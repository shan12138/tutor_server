package com.dataee.tutorserver.tutoradminserver.coursemanage.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.*;
import com.dataee.tutorserver.tutoradminserver.coursemanage.dao.CourseMngMapper;
import com.dataee.tutorserver.tutoradminserver.coursemanage.service.ICourseMngService;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ParentContractRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.ISaveContractManageService;
import com.dataee.tutorserver.tutoradminserver.messagemanage.dao.MessageManageMapper;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao.AddressChangeMapper;
import com.dataee.tutorserver.tutoradminserver.productmanage.dao.ProductManageMapper;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherListResponseBean;
import com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCenterMapper;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherCourseMapper;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherInviteMapper;
import com.dataee.tutorserver.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.http.annotation.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author 杨少聪
 * @Date 2019/5/17
 * @Description: com.dataee.tutorserver.tutoradminserver.coursemanage.service.impl
 */

@Service
public class CourseMngServiceImpl implements ICourseMngService {
    @Autowired
    private CourseMngMapper courseMngMapper;
    @Autowired
    private MessageManageMapper messageManageMapper;

    @Autowired
    private AddressChangeMapper  addressChangeMapper;

    @Autowired
    private TeacherCourseMapper  teacherCourseMapper;

    @Autowired
    private TeacherInviteMapper teacherInviteMapper;

    @Autowired
    private CommandUtil commandUtil;

    @Autowired
    private LowCaseToUpperCase lowCaseToUpperCase;

    @Autowired
    private IOSSService ossService;

    @Autowired
    private ParentCenterMapper parentCenterMapper;

    @Autowired
    private ProductManageMapper productManageMapper;

    @Autowired
    private ISaveContractManageService saveContractManageService;


    @Value("${template}")
    private  String template;

    @Value("${to}")
    private  String to;

    @Override
    public NewPageInfo getCourseList(Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<CourseListResponseBean> courseList = courseMngMapper.getCourseList();
        PageInfo pageInfo = new PageInfo(courseList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeAttendanceRecord(Lesson lesson, double hour, Integer courseHourRecordId) throws BaseServiceException {
        int number = courseMngMapper.changeAttendanceRecord(lesson);
        if (number != 1) {
            throw new SQLOperationException();
        }
//        courseMngMapper.changeCourseHourRecord(hour, courseHourRecordId);
        List<CourseHourRecord> courseHourRecordList = teacherCourseMapper.getCourseHourRecord(lesson.getCourse().getCourseId());
        double totalTimeToConsume = hour;
        for (CourseHourRecord courseHourRecord : courseHourRecordList) {
            double remainingTime = courseHourRecord.getTotalClassHour() - courseHourRecord.getConsumeClassHour();
            totalTimeToConsume -= remainingTime;
            if (totalTimeToConsume <= 0) {
                courseHourRecord.setConsumeClassHour(courseHourRecord.getTotalClassHour() + totalTimeToConsume);
                break;
            }
        }

        if (totalTimeToConsume > 0 && courseHourRecordList.size() > 0) {
            CourseHourRecord lastCourseHourRecord =  courseHourRecordList.get(courseHourRecordList.size() - 1);
            lastCourseHourRecord.setConsumeClassHour(lastCourseHourRecord.getConsumeClassHour() + totalTimeToConsume);
        }

        for (CourseHourRecord courseHourRecord :courseHourRecordList){
            courseMngMapper.changeCourseHourRecord(courseHourRecord);
        }

        if (number != 1) {
            throw new SQLOperationException();
        }
        Double restClassHour = courseMngMapper.getRestClassHour(courseHourRecordId);
        if (restClassHour < 0) {
            throw new BaseServiceException(ServiceExceptionsEnum.WRONG_OPERATION);
        }
    }

    @Override
    public NewPageInfo queryCourse(String studentName, String grade,String  subject, String teacher, String headTeacher,Integer productId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<CourseListResponseBean> courseList = courseMngMapper.queryCourse(studentName, grade,subject, teacher, headTeacher,productId);
        PageInfo pageInfo = new PageInfo(courseList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return courseMngMapper.getAllTeacher();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setTeacherForCourse(Integer courseId, Integer teacherId) {
        int id = courseMngMapper.getCourseId(courseId, 1);
        String courseName = courseMngMapper.getCourseName(courseId);
        Integer parentId = courseMngMapper.getCourseParentId(courseId);
        String teacherName = courseMngMapper.getTeacherName(teacherId);
        courseMngMapper.setTeacherForCourse(id, teacherId);
        MessageInformation messageInformation1 = new MessageInformation(teacherId, 20,
                "您已被任命为" + courseName + "教师");
        MessageInformation messageInformation2 = new MessageInformation(parentId, 40,
                teacherName+"已被任命为" + courseName + "的教师");
        messageManageMapper.addToMessage(messageInformation1);
        messageManageMapper.addToMessage(messageInformation2);
//        String telephone = teacherInviteMapper.getTelephoneById(teacherId);
//        teacherInviteMapper.updateTeacherInviteState(telephone,"已任教");

    }

    @Override
    public NewPageInfo getTempTeacher(Integer courseId, Integer flag, Page page,String queryCondition,String grade,String subject,String sex,String state1) throws BaseServiceException {
        int state = courseMngMapper.getCourseState(courseId);
        if (state == 2) {
            throw new BaseServiceException(ServiceExceptionsEnum.CANT_DISTRIBUTE_SPEAKING);
        } else {
            if (flag == 0) {
                //代表获得全部教师
                return getAllTeacherList(courseId, page,queryCondition,grade,subject,sex,state1);
            } else if (flag == 1) {
                //代表获得推荐教师
                return getCommandTeacherList(courseId, page);
            }
        }
        return null;
    }

    private NewPageInfo getAllTeacherList(Integer courseId, Page page,String queryCondition,String grade,String subject,String sex,String state) {
        List<TeacherListResponseBean> finalResultList = new ArrayList<>();
        List<TeacherListResponseBean> teacherNotSpeakingList = courseMngMapper.getTempNotSpeakingTeacher(courseId,queryCondition,grade,subject,sex,state);
        List<TeacherListResponseBean> teacherSpeakingList = courseMngMapper.getTempSpeakingTeacher(courseId,queryCondition,grade,subject,sex,state);
        if (!(teacherSpeakingList.size() == 0 && teacherNotSpeakingList.size() == 0)) {
            finalResultList.addAll(teacherNotSpeakingList);
            finalResultList.addAll(teacherSpeakingList);
        }
        List<TeacherListResponseBean> responseBeanList = new ArrayList<>();
        int start = page.getLimit() * (page.getPage() - 1);
        int end = start + page.getLimit();
        end = finalResultList.size() < end ? finalResultList.size() : end;
        for (int i = start; i < end; i++) {
            responseBeanList.add(finalResultList.get(i));
        }
        PageInfo pageInfo = new PageInfo(responseBeanList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        newPageInfo.setTotal(finalResultList.size());
        return newPageInfo;
    }

    private NewPageInfo getCommandTeacherList(Integer courseId, Page page) {
        List<TeacherListResponseBean> finalResultList = new ArrayList<>();
        List<TeacherListResponseBean> teacherNotSpeakingList = new ArrayList<>();
        List<TeacherListResponseBean> teacherSpeakingList = new ArrayList<>();
        //id为course_id，根据课程的年级科目查找对应所授范围的老师
        String grade = courseMngMapper.getGrade(courseId);
        String subject = courseMngMapper.getSubject(courseId);
        //得到授课范围内的试讲教师
        teacherSpeakingList = courseMngMapper.getTeacherSpeakingProper(grade, subject, courseId);
        //得到授课范围内的未试讲教师
        teacherNotSpeakingList = courseMngMapper.getTeacherNotSpeakingProper(grade, subject, courseId);
        if (!(teacherSpeakingList.size() == 0 && teacherNotSpeakingList.size() == 0)) {
            finalResultList.addAll(teacherNotSpeakingList);
            finalResultList.addAll(teacherSpeakingList);
        }
        List<TeacherListResponseBean> responseBeanList = new ArrayList<>();
        int start = page.getLimit() * (page.getPage() - 1);
        int end = start + page.getLimit();
        end = finalResultList.size() < end ? finalResultList.size() : end;
        for (int i = start; i < end; i++) {
            responseBeanList.add(finalResultList.get(i));
        }
        PageInfo pageInfo = new PageInfo(responseBeanList);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        newPageInfo.setTotal(finalResultList.size());
        return newPageInfo;
    }

    @Override
 //   @Async
    @Transactional(rollbackFor = Exception.class)
    public void createCourse(CreateCourseRequestBean course) throws BaseServiceException {
        //首先查看是否排过课
        Integer id = courseMngMapper.getCourse(course);
        if (id != null) {
            throw new BaseServiceException(ServiceExceptionsEnum.COURSE_EXISTS);
        }
        Student student = courseMngMapper.getStudentByStudentId(course.getStudentId());
        String studentName =  student.getStudentName();
        //获取目前最大的courseId从而设置新插入的课程的courseId
        // FIXME: 修改自增方式
        Integer courseId = courseMngMapper.getMaxCourseId();
        if (courseId == null) {
            courseId = 0;
        }
        course.setCourseId(courseId + 1);
        Address  address=addressChangeMapper.getAddressById(course.getParentId());
        CourseAddress courseAddress =new CourseAddress();
        courseAddress.setParentId(course.getParentId());
        courseAddress.setRegion(address.getRegion());
        courseAddress.setRegion(address.getAddressDetail());
       int number=courseMngMapper.addCourseAddress(courseAddress);
       if (number != 1) {
            throw new SQLOperationException();
       }

        course.setAddressId(courseAddress.getAddressId());
           number = courseMngMapper.createCourse(course);
            if (number != 1) {
                throw new SQLOperationException();
            }

        //新增完课程之后设置一个课程的名称方便以后查找
        number = courseMngMapper.setCourseName(course.getId(),
                course.getGrade() + course.getSubject() + "@" + studentName);
        if (number != 1) {
            throw new SQLOperationException();
        }
        //向课时记录表中插入数据
        List<CourseHourRecordSqlEntity> list = new ArrayList<>();
        CourseHourRecordSqlEntity courseHourRecord = new CourseHourRecordSqlEntity(course.getCourseId(), course.getTotalClassHour(),
                0.0, course.getPrice(), false,course.getDiscount(),new Date());
        CourseHourRecordSqlEntity freeCourseHourRecord = new CourseHourRecordSqlEntity(course.getCourseId(), course.getFreeClassHour(),
                0.0, 0, true,null,new Date());
        list.add(courseHourRecord);
        if(course.getFreeClassHour()!=0&&!course.getFreeClassHour().equals("")){
            list.add(freeCourseHourRecord);
        }
        for (CourseHourRecordSqlEntity courseHourRecordSqlEntity:list){
            number = courseMngMapper.insertNewCourseHourRecord(courseHourRecordSqlEntity);
            if (number != 1) {
               throw new SQLOperationException();
            }
        }
        Parent parent = courseMngMapper.getParent(course.getParentId());
        if(parent.getPartnerState().equals("未签约")) {
            courseMngMapper.updatePartnerState("已签约", parent.getParentId(), "邀请成功");
        }

             //获取家长信息（法定代理人的信息）
            Parent parentInfo = parentCenterMapper.getParentInfo(course.getParentId().toString());
            //获取地址信息
            List<Address> homeAddresses = parentCenterMapper.getAddressByParentId(course.getParentId());
            StringBuilder sb =new StringBuilder();
            for (Address address1:homeAddresses){
                sb.append(address1.getRegion()+address1.getAddressDetail());
            }
            Product product = productManageMapper.getProductById(course.getProductId());
            Calendar calendar = Calendar.getInstance();
            //生成json对象
            ParentContract contract =new ParentContract();
            Integer contractSn = courseMngMapper.getContractSn();
            Integer nextSn = 1000+contractSn+1;
            contract.setSn(String.valueOf(nextSn));
            contract.setBName(studentName);
            if(student.getSex()==0){
                contract.setBSex("男");
            }else if(student.getSex()==1){
                contract.setBSex("女");
            }
            contract.setIdCardNumber(student.getIdCard());
            contract.setSchool(student.getSchool());
            contract.setPartyAgentName(parentInfo.getParentName());
            contract.setTelephone(parentInfo.getTelephone());
            contract.setContactAddress(sb.toString());
            contract.setSubjectName(course.getSubject());
            contract.setGrade(course.getGrade());
            contract.setOtherSubject(course.getSubject());
            contract.setProductName(product.getProductName());
            contract.setTotalCourseHour(course.getTotalClassHour());
            contract.setPrice(course.getPrice());
            contract.setTotalMoney(course.getTotalClassHour()*course.getDiscount()*course.getPrice()/10000);
            contract.setUpperTotalMoney(lowCaseToUpperCase.change(contract.getTotalMoney()));
            contract.setPartyAgentIdCard(parentInfo.getIdCard());
            contract.setStartYear(calendar.get(Calendar.YEAR));
            contract.setStartMonth(calendar.get(Calendar.MONTH)+1);
            contract.setStartDay(calendar.get(Calendar.DATE));
            contract.setEndYear(calendar.get(Calendar.YEAR));
            contract.setEndMonth(calendar.get(Calendar.MONTH)+1);
            contract.setEndDay(calendar.get(Calendar.DATE));


            Gson gson = new GsonBuilder().create();
            String jsonString = gson.toJson(contract);

            String outputDir = to+contract.getSn();
            //每个用户根据合同编号创建模板目录
            String contractDir = outputDir+"/test.pug";
            //每个用户根据合同编号生成json目录
            String jsonDir =  outputDir+"/index.json";
            System.out.println(contractDir+"-------------创建的目录--------------");
            System.out.println(jsonDir+"-----------生成json的目录-----------------");
            File fromFile =new File(template);
            File toFile =new File(contractDir);
            //如果不存在就创建目录并且生成文件
            if(!toFile.exists()){
                toFile.mkdir();
            }
        try {
            //把服务器模板目录中的模板拷贝到每个模板当中
            FileUtils.copyFile(fromFile,toFile);
            //上传json文件到每个目录当中
            Writer write = new OutputStreamWriter(new FileOutputStream(jsonDir), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        courseMngMapper.saveContract(contract);
        System.out.println("1111111111111111111111111111111111111111111");
        this.generatePdfAndUploadPdf(
                student, contract, parentInfo, outputDir,course.getCourseId()
        );
    }

    @Async
    public void generatePdfAndUploadPdf(
            Student student,
            ParentContract contract,
            Parent parentInfo,
            String outputDir,
            Integer courseId
    ) {
        String command = String.format("docker run --rm -i -v %s:/tmp -w %s --name relaxed relaxed /tmp/test.pug -l index.json --build-once --no-sandbox", outputDir, outputDir);
        System.out.println(command);
        commandUtil.execCommand(command);
        String localFileName = outputDir+"/test.pdf";
        String  toFileName = "contracts/parent/"+contract.getSn()+".pdf";

        try {
            ossService.uploadFile(toFileName,localFileName);
        } catch (BaseServiceException e) {
            e.printStackTrace();
        }

        ParentContractRequestBean pcrb =new ParentContractRequestBean();
        pcrb.setStudentId(student.getStudentId());
        pcrb.setParentContractId(contract.getId());
        pcrb.setIdCard(student.getIdCard());
        pcrb.setCourseId(courseId);
        pcrb.setContractAddress(toFileName);
        pcrb.setContractName(
                parentInfo.getParentName() +
                        student.getStudentName() +contract.getSn()+
                        ".pdf"
        );
        pcrb.setPersonId(parentInfo.getParentId());
         saveContractManageService.saveParentContract(pcrb);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCourse(Integer id) throws BaseServiceException {
        Integer number = courseMngMapper.getState(id);
        if (number == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.CANT_DELETE_COURSE);
        }
        courseMngMapper.changeTableState("course", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setTempSpeakingTeacher(Integer courseId, Integer teacherId) throws BaseServiceException {
        Integer tempSpeakingId = courseMngMapper.getTempSpeakingId(courseId, teacherId);
        if (tempSpeakingId == null) {
            courseMngMapper.setTempSpeakingTeacher(courseId, teacherId);
            String courseName = courseMngMapper.getCourseName(courseId);
            MessageInformation messageInformation1 = new MessageInformation(teacherId, 20, "您已被安排为" + courseName
                    + "课程的试讲教师");
            messageManageMapper.addToMessage(messageInformation1);
            MessageInformation messageInformation = new MessageInformation(courseMngMapper.getParentId(courseId),
                    40, courseName + "课程已安排教师试讲");
            messageManageMapper.addToMessage(messageInformation);
        } else {
            throw new BaseServiceException(ServiceExceptionsEnum.CANT_DISTRIBUTE_TEACHER);
        }
    }

    @Override
    public void cancleTempSpeakingTeacher(Integer courseId, Integer teacherId) {
        int tempSpeakingId = courseMngMapper.getTempSpeakingId(courseId, teacherId);
        courseMngMapper.changeTableState("temp_speaking", tempSpeakingId);
        MessageInformation messageInformation1 = new MessageInformation(teacherId, 20, "您已被取消试讲");
        messageManageMapper.addToMessage(messageInformation1);
        String courseName = courseMngMapper.getCourseName(courseId);
        MessageInformation messageInformation = new MessageInformation(courseMngMapper.getParentId(courseId),
                40, courseName + "课程已取消教师试讲");
        messageManageMapper.addToMessage(messageInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeTeacherForCourse(Integer courseId, Integer teacherId) {
        int id = courseMngMapper.getCourseId(courseId, 2);
        courseMngMapper.changeTableState("course", id);
        CourseSqlEntity course = courseMngMapper.selectCourse(id);
        course.setTeacherId(teacherId);
        courseMngMapper.insertNewCourse(course);
        String courseName = courseMngMapper.getCourseName(courseId);
//        //FIXME   此时课时有多个
//        List<Integer> courseHourRecordIds = courseMngMapper.getCourseHourRecordId(course.getCourseId());
//
//        List<CourseHourRecordSqlEntity> courseHourRecords =new ArrayList<>();
//        for(Integer courseHourRecordId:courseHourRecordIds){
//            courseMngMapper.changeTableState("course_hour_record", courseHourRecordId);
//            CourseHourRecordSqlEntity courseHourRecord = courseMngMapper.selectCourseHourRecord(courseHourRecordId);
//            courseHourRecords.add(courseHourRecord);
//        }
//        for (CourseHourRecordSqlEntity courseHourRecord:courseHourRecords){
//            courseMngMapper.insertNewCourseHourRecord(courseHourRecord);
//        }
        MessageInformation messageInformation1 = new MessageInformation(teacherId, 20,
                "您已被更换成为" + courseName + "教师");
        messageManageMapper.addToMessage(messageInformation1);
        MessageInformation messageInformation = new MessageInformation(courseMngMapper.getParentId(courseId),
                40, courseName + "课程已更换教师");
        messageManageMapper.addToMessage(messageInformation);
    }

    @Override
    public List<Address> getParentAddressList(Integer parentId) {
        return courseMngMapper.getParentAddressList(parentId);
    }

    @Override
    public List<Student> getParentChildList(Integer parentId) {
        return courseMngMapper.getParentChildList(parentId);
    }

    @Override
    public Integer getTeacherIdByCourseId(Integer state, Integer courseId) {
        Integer teacherId = courseMngMapper.queryTeacherIdByCourseId(state, courseId);
        return teacherId;
    }

    @Override
    public Course getCourseDetailInfo(String courseId) {
        return courseMngMapper.getCourseDetailInfo(courseId);
    }

    @Override
    public List<Course> getTeacherCourse(Integer teacherId) {
        return courseMngMapper.getTeacherCourse(teacherId);
    }

    @Override
    public List<Teacher> getHasCourseTeacher() {
        return courseMngMapper.getHasCourseTeacher();
    }

    @Override
    public NewPageInfo getLessonDetailInfo(String courseId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Lesson> lessons = courseMngMapper.getLessonDetailInfo(courseId);
        for (Lesson lesson : lessons) {
            Integer uploadState = courseMngMapper.getRescourcePdfUploadState(lesson.getId());
            lesson.setUploadState(uploadState);
        }
        PageInfo pageInfo = new PageInfo(lessons);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public List<Administrator> getAdminList(String roleName) {
        return courseMngMapper.getAdminList(roleName);
    }

    @Override
    public NewPageInfo<CurrentCourseResponseBean> getCurrCourseList(Page page,String studentName,String teacher,String grade,String subject,String headTeacher,String startTime,String endTime) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<CurrentCourseResponseBean> currentCourseList = courseMngMapper.queryCurrentCourseByWeek(TimeWeekUtil.getWeekOfYear(),studentName,teacher,grade,subject,headTeacher,startTime,endTime);
        NewPageInfo<CurrentCourseResponseBean> newPageInfo = PageInfoUtil.read(new PageInfo(currentCourseList));
        return newPageInfo;
    }

    @Override
    public NewPageInfo<CourseHourRecord> getAllCourseHourRecordByCourseId(Integer courseId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<CourseHourRecord> allCourseHourRecord = courseMngMapper.getAllCourseHourRecordByCourseId(courseId);
        NewPageInfo<CourseHourRecord> newPageInfo = PageInfoUtil.read(new PageInfo(allCourseHourRecord));
        return newPageInfo;
    }

    @Override
    public CourseHourRecordDetail getTotalCourseHour(Integer courseId) {
        List<CourseHourRecord> courseHourRecordlist = courseMngMapper.getAllCourseHourRecordByCourseId(courseId);
        String courseName = courseMngMapper.getCourseName(courseId);
        double  allCourseHourRecord = 0.0;
        double  allBuyCourseHourRecord=0.0;
        double  allconsumeCourseHourRecord=0.0;
        for (CourseHourRecord courseHourRecord: courseHourRecordlist){
            allCourseHourRecord += courseHourRecord.getTotalClassHour();
            allconsumeCourseHourRecord +=  courseHourRecord.getConsumeClassHour();
            if(courseHourRecord.getIsFree()==0){
                allBuyCourseHourRecord+=courseHourRecord.getTotalClassHour();
            }
        }
        CourseHourRecordDetail recordDetail = new CourseHourRecordDetail();
        recordDetail.setCourseName(courseName);
        recordDetail.setTotalClassHour(allCourseHourRecord);
        recordDetail.setBuyClassHour(allBuyCourseHourRecord);
        recordDetail.setConsumeClassHour(allconsumeCourseHourRecord);
        return recordDetail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCourseHour(AddCourseHour  addCourseHour) {
        List<CourseHourRecord> records = courseMngMapper.getAllCourseHourRecordByCourseId(addCourseHour.getCourseId());
        CourseHourRecordSqlEntity recordSqlEntity = new CourseHourRecordSqlEntity(addCourseHour.getCourseId(),addCourseHour.getAddCourseHour(),0.0,records.get(0).getPrice(),false,addCourseHour.getDiscount(),new Date());
        courseMngMapper.insertNewCourseHourRecord(recordSqlEntity);
        List<CourseHourRecord> list = courseMngMapper.getAllCourseHourRecordByCourseId(addCourseHour.getCourseId());
        CourseHourRecord courseHourRecord = list.get(list.size()-1);
        CourseHourRecord hourRecord =new CourseHourRecord();
        hourRecord.setId(recordSqlEntity.getId());
        if(courseHourRecord.getTotalClassHour()<courseHourRecord.getConsumeClassHour()){
            hourRecord.setConsumeClassHour(courseHourRecord.getConsumeClassHour()-courseHourRecord.getTotalClassHour());
            courseMngMapper.changeCourseHourRecord(hourRecord);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void giveFreeCourseHour(GiveFreeCourseHour giveFreeCourseHour) throws BaseServiceException {
        CourseHourRecordSqlEntity recordSqlEntity = new CourseHourRecordSqlEntity(giveFreeCourseHour.getCourseId(),giveFreeCourseHour.getGiveFreeCourseHour(),0.0,0,true,null,new Date());
        courseMngMapper.insertNewCourseHourRecord(recordSqlEntity);
    }
}

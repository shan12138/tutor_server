package com.dataee.tutorserver.tutorteacherserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.coursemanage.dao.CourseMngMapper;
import com.dataee.tutorserver.tutoradminserver.messagemanage.dao.MessageManageMapper;
import com.dataee.tutorserver.tutorminiprogressserver.dao.InvitedTeacherMapper;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCourseMapper;
import com.dataee.tutorserver.tutorteacherserver.bean.Message;
import com.dataee.tutorserver.tutorteacherserver.bean.ScheduleBean;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherGiftBean;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInviteRegister;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherCourseMapper;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherInviteMapper;
import com.dataee.tutorserver.tutorteacherserver.service.ITeacherCourseService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/15 19:18
 */
@Service
public class TeacherCourseServiceImpl implements ITeacherCourseService {
    @Autowired
    private TeacherCourseMapper teacherCourseMapper;
    @Autowired
    private MessageManageMapper messageManageMapper;
    @Autowired
    private CourseMngMapper courseMngMapper;

    @Autowired
    private  ParentCourseMapper parentCourseMapper;

    @Autowired
    private TeacherInviteMapper teacherInviteMapper;
    @Autowired
    private InvitedTeacherMapper invitedTeacherMapper;

    @Override
    public Course getAttendanceRecord(Integer courseId, Integer teacherId) {
        return teacherCourseMapper.getAttendanceRecord(courseId, teacherId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkIn(Integer lessonId, Integer teacherId) throws BaseServiceException, ParseException {
        //首先获取最接近当前时间的签到记录的上课和下课时间
      //  Lesson lesson = teacherCourseMapper.getLesson(courseId, teacherId, "course_time");
        Lesson lesson = teacherCourseMapper.getOneLesson(lessonId);
        if (lesson == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.NONE_COURSE_ATTEND);
        } else {
            if (lesson.getCheckIn()==1) {
                throw new BaseServiceException(ServiceExceptionsEnum.COURSE_ATTEND_EXIST);
            }
            //删除上传课件才能签到的限制
//            if (lesson.getCheckInTime() == null || lesson.getCheckInTime().equals("")) {
//                //查看当堂课是否有课件
//                Integer resourceNum = teacherCourseMapper.getResourceNum(lesson.getId());
//                if (resourceNum != 0) {
//                    int isRead = teacherCourseMapper.getIsRead(lesson.getId());
//                    if (isRead == 0) {
//                        //查看当堂的课件是否存在并且阅读，只有存在且阅读后方可签到
//                        throw new BaseServiceException(ServiceExceptionsEnum.CANT_ATTEND_WITHOUT_READ);
//                    }
//                }
//                else {
//                    throw new BaseServiceException(ServiceExceptionsEnum.CANT_ATTEND_WITHOUT_RESOURCE);
//                }
//            }
            //判断是否处于签到时间
            if (isAttendTime(lesson)) {
                //是，进行签到
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                int number = teacherCourseMapper.editCheckInTimeInfo(lesson.getId(), time);
                String telephone = teacherInviteMapper.getTelephoneById(teacherId);
                String status = teacherInviteMapper.getTeacherInvitationStatus(telephone);
                if(lesson.getLessonNumber()==1 && !status.equals("已任教")) {
                    teacherInviteMapper.updateTeacherInviteState(telephone,"已任教");
                    //此时邀请老师成功
                    TeacherInviteRegister registerInfo = teacherInviteMapper.getInviteRegisterInfo(teacherId);
                    if(registerInfo.getInvitedTeacherId()!=null){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        TeacherGiftBean teacherGiftBean = new TeacherGiftBean();
                        teacherGiftBean.setInvitationSuccessDate(sdf.parse(time));
                        teacherGiftBean.setInvitationTeacherId(teacherId);
                        teacherGiftBean.setTeacherId(registerInfo.getInvitedTeacherId());
                        teacherInviteMapper.insertInvitedGift(teacherGiftBean);
                    }
                    //增加合伙人邀请老师账单
                    Integer teacherLevel = teacherCourseMapper.getTeacherLevel(teacherId);
                    Integer maxTeacherLevel = teacherCourseMapper.getMaxTeacherLevel();
                    if(teacherLevel!=null){
                        if(teacherLevel>maxTeacherLevel){
                            teacherLevel = maxTeacherLevel;
                        }
                        Integer money = invitedTeacherMapper.getTeacherLevelByLevel(teacherLevel);
                        Integer partnerIdByTeacherId = teacherCourseMapper.getPartnerIdByTeacherId(teacherId);
                        String teacherName = teacherCourseMapper.getTeacherName(teacherId);
                        Bill bill=new Bill();
                        bill.setTime(lesson.getRemarkCheckInTime());
                        bill.setFlowType("流出");
                        bill.setKind("邀请教员佣金");
                        bill.setNumber(money);
                        bill.setSource("平台");
                        bill.setTarget("合伙人");
                        bill.setTargetId(partnerIdByTeacherId);
                        Message message =new Message();
                        message.setTeacherName(teacherName);
                        Gson gson = new GsonBuilder().create();
                        String jsonString = gson.toJson(message);
                        bill.setMessage(jsonString);
                        teacherCourseMapper.insertParentBill(bill);
                    }
                }
                if (number != 1) {
                    throw new SQLOperationException();
                }
            } else {
                //不是，返回错误信息
                throw new BaseServiceException(ServiceExceptionsEnum.CANT_ATTEND);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkOut(Integer teacherId, Integer lessonId,Integer courseId) throws BaseServiceException, ParseException {
        //首先获取最接近当前时间的课程记录
        Lesson lesson = teacherCourseMapper.getLessonById(lessonId);
        if (lesson == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.NONE_COURSE_ATTEND);
        }
        if (lesson.getCheckInTime() == null|| lesson.getCheckInTime().equals("")) {
            throw new BaseServiceException(ServiceExceptionsEnum.CANT_CHECK_OUT);
        }
        if (!isAttendTime(lesson)) {
            throw new BaseServiceException(ServiceExceptionsEnum.CANT_ATTEND);
        }
        if (lesson.getCheckOutTime() != null) {
            throw new BaseServiceException(ServiceExceptionsEnum.COURSE_ATTEND_EXIST);
        }
        String checkInTime = teacherCourseMapper.getCheckInTime(lesson.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String checkOutTime = formatter.format(new Date());
        //修改签退时间
        int number = teacherCourseMapper.editCheckOutTimeInfo(lesson.getId(), checkOutTime);
        if (number != 1) {
            throw new SQLOperationException();
        }
        Date date1 = formatter.parse(checkInTime);
        Date date2 = formatter.parse(checkOutTime);
        double millisecond = date2.getTime() - date1.getTime();
        double classTime = millisecond / (60 * 60 * 1000);
        classTime = (int) Math.ceil(classTime);
        //计算上课时长,并修改上课时长
        number = teacherCourseMapper.editClassTime(lesson.getId(), classTime);
        if (number != 1) {
            throw new SQLOperationException();
        }
        int id = teacherCourseMapper.getCourseId(courseId, teacherId);
        //修改上课次数
        number = teacherCourseMapper.editCourseInfo(id);
        if (number != 1) {
            throw new SQLOperationException();
        }

        // consumeTime < totalTime
        // order by time
        int parentId = teacherCourseMapper.getParentId(courseId);
        //受先获取家长的等级
        Integer level = teacherCourseMapper.getParentLevelId(parentId);
        //获取课程所对应的的产品
        Integer productId = teacherCourseMapper.getProduct(courseId);
        Integer maxParentLevel = teacherCourseMapper.getMaxParentLevel(productId);
        if(level>maxParentLevel){
            level=maxParentLevel;
        }
        ParentLevel parentLevel = teacherCourseMapper.getParentLevel(productId,level);
        CourseHourRecord record = teacherCourseMapper.getOneCourseHourRecord(courseId);
        List<CourseHourRecord> courseHourRecordList = teacherCourseMapper.getCourseHourRecord(courseId);
        double totalTimeToConsume = classTime;
        double consumeMoney =0.0;
        double oldConsume =0.0;
        double oldConsume1 =0.0;
        double consumeMoney1 =0.0;
        double commissionRatioMoney =0.0;
        double commissionRatioMoney1 =0.0;
        for (CourseHourRecord courseHourRecord : courseHourRecordList) {
            double commissionRatio = (record.getId().equals(courseHourRecord.getId())) ?
                    parentLevel.getFirstCommissionRatio() : parentLevel.getNextCommissionRatio();
            double remainingTime = courseHourRecord.getTotalClassHour() - courseHourRecord.getConsumeClassHour();
                totalTimeToConsume -= remainingTime;
            if(totalTimeToConsume>0){
                oldConsume1 = courseHourRecord.getConsumeClassHour();
                courseHourRecord.setConsumeClassHour(courseHourRecord.getTotalClassHour());
                consumeMoney1+=(courseHourRecord.getConsumeClassHour()-oldConsume1)*courseHourRecord.getPrice()*courseHourRecord.getDiscount()*0.01;
                commissionRatioMoney1+=(courseHourRecord.getConsumeClassHour()-oldConsume1)*courseHourRecord.getPrice()*courseHourRecord.getDiscount()*commissionRatio/10000;
            }

            if (totalTimeToConsume <= 0) {
                oldConsume = courseHourRecord.getConsumeClassHour();
                courseHourRecord.setConsumeClassHour(courseHourRecord.getTotalClassHour() + totalTimeToConsume);
                consumeMoney+=(courseHourRecord.getConsumeClassHour()-oldConsume)*courseHourRecord.getPrice()*courseHourRecord.getDiscount()*0.01;
                commissionRatioMoney+=(courseHourRecord.getConsumeClassHour()-oldConsume)*courseHourRecord.getPrice()*courseHourRecord.getDiscount()*commissionRatio/10000;
                break;
            }
        }
        double  lastOldConsume =0.0;
        double  lastConsumeMoney =0.0;
        double specilCommissionRatioMoney =0.0;
        if (totalTimeToConsume > 0 && courseHourRecordList.size() > 0) {
            CourseHourRecord lastCourseHourRecord =  courseHourRecordList.get(courseHourRecordList.size() - 1);
            lastOldConsume = lastCourseHourRecord.getConsumeClassHour();
            lastCourseHourRecord.setConsumeClassHour(lastCourseHourRecord.getConsumeClassHour() + totalTimeToConsume);
            lastConsumeMoney = (lastCourseHourRecord.getConsumeClassHour()-lastOldConsume)*lastCourseHourRecord.getPrice()*lastCourseHourRecord.getDiscount()/100;
            double commissionRatio = (record.getId().equals(lastCourseHourRecord.getId())) ?
                    parentLevel.getFirstCommissionRatio() : parentLevel.getNextCommissionRatio();
            specilCommissionRatioMoney+=(lastCourseHourRecord.getConsumeClassHour()-oldConsume)*lastCourseHourRecord.getPrice()*lastCourseHourRecord.getDiscount()*commissionRatio/10000;

        }
        int totalConsume = (int) Math.ceil(lastConsumeMoney+consumeMoney+consumeMoney1);
        int totalCommissionRatioMoney =(int)Math.ceil(specilCommissionRatioMoney+commissionRatioMoney+commissionRatioMoney1);
        for (CourseHourRecord courseHourRecord :courseHourRecordList){
            courseMngMapper.changeCourseHourRecord(courseHourRecord);
        }

        int partnerId = teacherCourseMapper.getPartnerId(parentId);
        String parentName = teacherCourseMapper.getParentName(parentId);
        Bill parentConsumeClassHourBill =new Bill();
        Bill partnerGetBill =new Bill();
        parentConsumeClassHourBill.setTime(lesson.getRemarkCheckInTime());
        parentConsumeClassHourBill.setFlowType("流入");
        parentConsumeClassHourBill.setKind("家长消耗课时");
        parentConsumeClassHourBill.setNumber(totalConsume);
        parentConsumeClassHourBill.setSource("家长");
        parentConsumeClassHourBill.setSourceId(parentId);
        parentConsumeClassHourBill.setTarget("平台");
        parentConsumeClassHourBill.setCourseId(courseId);
        Message message =new  Message();
        message.setParentName(parentName);
        message.setConsumeClassHour(classTime);
        message.setStartTime(lesson.getRemarkCheckInTime());
        message.setEndTime(lesson.getRemarkCheckOutTime());
        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(message);
        parentConsumeClassHourBill.setMessage(jsonString);
        teacherCourseMapper.insertParentBill(parentConsumeClassHourBill);

        partnerGetBill.setTime(lesson.getRemarkCheckInTime());
        partnerGetBill.setFlowType("流出");
        partnerGetBill.setKind("邀请家长课时消耗佣金");
        partnerGetBill.setNumber(totalCommissionRatioMoney);
        partnerGetBill.setSource("家长");
        partnerGetBill.setSourceId(parentId);
        partnerGetBill.setTarget("合伙人");
        partnerGetBill.setTargetId(partnerId);
        partnerGetBill.setMessage(jsonString);
        teacherCourseMapper.insertParentBill(partnerGetBill);


        String courseName = courseMngMapper.getCourseName(courseId);
        MessageInformation messageInformation = new MessageInformation(teacherId, 20,
                courseName + "课程有课时损耗:" + classTime + "课时");
        messageManageMapper.addToMessage(messageInformation);

        MessageInformation messageInformation1 = new MessageInformation(parentId, 40,
                courseName + "课程有课时损耗:" + classTime + "课时");
        messageManageMapper.addToMessage(messageInformation1);
    }

    @Override
    public List<ScheduleBean> getSchedule(Integer personId, Integer week, String year) {
        return teacherCourseMapper.getSchedule(personId, week, year);
    }

    @Override
    public List<Remarks> getParentRecord(Integer lessonId) {
        return teacherCourseMapper.getParentRecord(lessonId);
    }

    @Override
    public NewPageInfo getAttendanceRecordPageInfo(Integer courseId, Integer personId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Lesson> lessons = teacherCourseMapper.getAttendanceRecordPageInfo(courseId, personId);
        for (Lesson lesson : lessons) {
            Integer resourceNum = teacherCourseMapper.getResourceNum(lesson.getId());
            if (resourceNum != 0) {
                resourceNum = 1;
            }
            lesson.setResourceNum(resourceNum);
        }
        PageInfo pageInfo = new PageInfo(lessons);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    //判断当前时间是否处于可签到时间
    private boolean isAttendTime(Lesson lesson) throws ParseException {
/*        String startTime = lesson.getCourseTime();
        String endTime = lesson.getEndCourseTime();
        Calendar beforeTime = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        beforeTime.setTime(formatter.parse(startTime));
        //得到上课前10分钟之前的时间
        beforeTime.add(beforeTime.MINUTE, -10);
        Calendar afterTime = Calendar.getInstance();
        afterTime.setTime(formatter.parse(endTime));
        //得到上课后10分钟之后的时间
        afterTime.add(afterTime.MINUTE, 10);
        //获取当前时间
        Calendar currentTime = Calendar.getInstance();//获取一个Calendar对象
        currentTime.setTime(new Date());
        //确定是否处于签到时间范围内
        if (currentTime.before(afterTime) && currentTime.after(beforeTime)) {
            return true;
        } else {
            return false;
        }*/
        Date startTime = lesson.getRemarkCheckInTime();
        Date endTime = lesson.getRemarkCheckOutTime();
        Calendar beforeTime = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        beforeTime.setTime(startTime);
        //得到上课前10分钟之前的时间
        beforeTime.add(beforeTime.MINUTE, -30);
        Calendar afterTime = Calendar.getInstance();
        afterTime.setTime(endTime);
        //得到上课后10分钟之后的时间
        afterTime.add(afterTime.MINUTE, 30);
        //获取当前时间
        Calendar currentTime = Calendar.getInstance();//获取一个Calendar对象
        currentTime.setTime(new Date());
        //确定是否处于签到时间范围内
        if (currentTime.before(afterTime) && currentTime.after(beforeTime)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Lesson getLessonById(Integer id) {
        Lesson lesson=  teacherCourseMapper.getLessonById(id);
        Integer resourceNum = teacherCourseMapper.getResourceNum(lesson.getId());
        if (resourceNum != 0) {
            resourceNum = 1;
        }
        lesson.setResourceNum(resourceNum);
        return lesson;
    }
}

package com.dataee.tutorserver.tutorpatriarchserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ParentContractRequestBean;
import com.dataee.tutorserver.tutorpatriarchserver.bean.*;
import com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCenterMapper;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentCenterService;
import com.dataee.tutorserver.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/4/29 10:56
 */
@Service
public class ParentCenterServiceImpl implements IParentCenterService {
    @Autowired
    private ParentCenterMapper parentCenterMapper;
    @Autowired
    private IOSSService ossService;
    @Autowired
    private  CommandUtil commandUtil;

    @Value("${template}")
    private  String template;

    @Value("${to}")
    private  String to;


    @Override
    public NewPageInfo<Student> getOwnChildren(Integer parentId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Student> students = parentCenterMapper.getOwnChildren(parentId);
        PageInfo pageInfo = new PageInfo(students);
        NewPageInfo<Student> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public Student getChildDetailInfo(String studentId) {
        return parentCenterMapper.getChildDetailInfo(studentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addChildInfo(EnrollChildInfoRequestBean enrollChildInfoRequestBean) throws BaseServiceException {
        int number = parentCenterMapper.addChildInfo(enrollChildInfoRequestBean);
        if (number != 1) {
            throw new BaseServiceException(ServiceExceptionsEnum.DATA_EXIST);
        }
        int studentId = enrollChildInfoRequestBean.getStudentId();
        for (String weakDiscipline : enrollChildInfoRequestBean.getWeakDiscipline()) {
            if (weakDiscipline != null && !weakDiscipline.equals(""))
                parentCenterMapper.addChildWeakDiscipline(studentId, weakDiscipline.split("-")[0],
                        weakDiscipline.split("-")[1]);
        }
    }

    @Override
    public Parent getParentInfo(String parentId) {
        return parentCenterMapper.getParentInfo(parentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeParentInfo(Integer parentId, String parentName, Integer sex) throws SQLOperationException {
        int number = parentCenterMapper.changeParentInfo(parentId, parentName, sex);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAddress(CreateAddressRequestBean createAddressRequestBean) throws SQLOperationException {
        int number = parentCenterMapper.addAddress(createAddressRequestBean);
        if (number != 1) {
            throw new SQLOperationException();
        }

    }

    @Override
    public List<Address> getAddress(String parentId) {
        return parentCenterMapper.getAddress(parentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeAddressInfo(ChangeAddressRequestBean changeAddressRequestBean) throws SQLOperationException, BaseControllerException {
     /*   String oldRegion = parentCenterMapper.getOldRegion(changeAddressRequestBean.getAddressId());
        String oldAddressDetail = parentCenterMapper.getOldAddressDetail(changeAddressRequestBean.getAddressId());
        changeAddressRequestBean.setOldRegion(oldRegion);
        changeAddressRequestBean.setOldAddressDetail(oldAddressDetail);*/
        int number = parentCenterMapper.updateHomeaddress(changeAddressRequestBean);
        if (number != 1) {
            throw new SQLOperationException();
        }


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enrollParentInfo(EnrollChildInfoRequestBean enrollChildInfo) throws BaseServiceException, BaseControllerException {
        Integer parentId = SecurityUtil.getPersonId();
        int number = parentCenterMapper.enrollParentInfo(parentId.toString());
        if (number != 1) {
            throw new SQLOperationException();
        }
        enrollChildInfo.setParentId(parentId);
        int number1 = parentCenterMapper.addChildInfo(enrollChildInfo);
        if (number1 != 1) {
            throw new BaseServiceException(ServiceExceptionsEnum.DATA_EXIST);
        }
    }

    @Override
    public NewPageInfo<MessageInformation> getMsgList(Integer parentId, int role, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<MessageInformation> messageInformations = parentCenterMapper.getParentMsgList(parentId, role);
        for (MessageInformation messageInformation : messageInformations) {
            parentCenterMapper.setRead(messageInformation.getId());
        }
        PageInfo pageInfo = new PageInfo(messageInformations);
        NewPageInfo<MessageInformation> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public Integer getState(Integer parentId) {
        return parentCenterMapper.getState(parentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeChildInfo(EnrollChildInfoRequestBean enrollChildInfo) {
        parentCenterMapper.changeChildInfo(enrollChildInfo);
        int studentId = enrollChildInfo.getStudentId();
        parentCenterMapper.changeWeakDisciplineState(studentId);
        for (String weakDiscipline : enrollChildInfo.getWeakDiscipline()) {
            if (weakDiscipline != null && !weakDiscipline.equals(""))
                parentCenterMapper.addChildWeakDiscipline(studentId, weakDiscipline.split("-")[0],
                        weakDiscipline.split("-")[1]);
        }
    }

    @Override
    public Student getOneChildDetail(Integer parentId) {
        Integer studentId = parentCenterMapper.getOneChildId(parentId);
        if (studentId == null) {
            return null;
        }
        Student student = parentCenterMapper.getChildDetailInfo(studentId.toString());
        student.setStudentId(studentId);
        return student;
    }

    @Override
    public Address getOneAddress(Integer addressId) {
        return parentCenterMapper.getOneAddress(addressId);
    }

    @Override
    public Integer getAddressId(Integer personId) {
        return parentCenterMapper.getAddressId(personId);
    }

    @Override
    public void changeOneAddressInfo(ChangeAddressRequestBean changeAddressRequestBean) {
        parentCenterMapper.changeOneAddressInfo(changeAddressRequestBean);
    }

    @Override
    public void changeParentState(Integer parentId) {
        parentCenterMapper.changeParentState(parentId);
    }

    @Override
    public List<String> getWeakDiscipline(Integer studentId) {
        return parentCenterMapper.getWeakDiscipline(studentId);
    }

    @Override
    public void createParent(Integer parentId, String parentName, Integer sex, String idCard , String inviteCode) throws BaseServiceException {
        Parent parent = parentCenterMapper.getParentByInviteCode(inviteCode);
        String ownInviteCode = InviteCodeUtil.getlinkNo();
        if(parent == null) {
            Partner partner  = parentCenterMapper.getPartnerByInviteCode(inviteCode);
            if(partner == null) {
                throw new BaseServiceException(ServiceExceptionsEnum.INVITE_CODE_NOT_FOUND);
            }
            else {
                // 创建合伙人邀请的家长
                parentCenterMapper.createParentOfPartner(parentId, parentName, sex,idCard, inviteCode,
                        ownInviteCode, partner.getPartnerId());
            }
        }
        else {
            // 创建家长邀请的家长
            parentCenterMapper.createParent(parentId, parentName, sex, inviteCode,
                    parent.getParentId(), ownInviteCode, parent.getParentLevel() + 1, parent.getPartnerId());
        }
    }

    @Override
    public List<Address> getOfficialAddress(String paretnId) {
        return parentCenterMapper.getOfficialAddress(paretnId);
    }

    @Override
    public Integer getMsgNum(Integer personId, int roleId) {
        return parentCenterMapper.getMsgNum(personId, roleId);
    }

    @Override
    public List<Address> getAddressByParentId(Integer parentId) {
        return parentCenterMapper.getAddressByParentId(parentId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeCourseAddressInfo(ChangeAddressRequestBean changeAddressRequestBean)throws SQLOperationException {
        System.out.println("之前"+changeAddressRequestBean.getAddressId());
        Course course=parentCenterMapper.getCourseById(changeAddressRequestBean.getCourseId());
        if(course.getAddress()!=null){
            changeAddressRequestBean.setAddressId(course.getAddress().getAddressId());
            System.out.println("之后"+changeAddressRequestBean.getAddressId());
            String oldCourseRegion = parentCenterMapper.getOldCourseRegion(changeAddressRequestBean.getAddressId());
            String oldCourseAddressDetail = parentCenterMapper.getOldCourseAddressDetail(changeAddressRequestBean.getAddressId());
            changeAddressRequestBean.setOldRegion(oldCourseRegion);
            changeAddressRequestBean.setOldAddressDetail(oldCourseAddressDetail);

        }
        int number = parentCenterMapper.addCourseAddressChangeInfo(changeAddressRequestBean);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    public List<String> getContractAddress(Integer personId, Integer personRole) {
        return parentCenterMapper.getContractAddress(personId,personRole);
    }

    @Override
    public NewPageInfo<ParentContractBean> getParentContractList(Integer personId, Integer personRole,Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ParentContractBean> pcb = parentCenterMapper.getParentContractList(personId,personRole);
        PageInfo pageInfo = new PageInfo(pcb);
        NewPageInfo<ParentContractBean> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public ParentContractBean getParentContractByid(Integer contractId) throws BaseServiceException {
        ParentContractBean contract = parentCenterMapper.getParentContractByid(contractId);
      //  contract.setPdfAddress(ossService.getURL(contract.getPdfAddress()).toString());
        List<String> urlList = new ArrayList<>();
        for (String url:contract.getContractImages()){
            URL url1 = ossService.getURL(url);
            urlList.add(url1.toString());
        }
        contract.setContractImages(urlList);
        if(contract!=null&&contract.getSignImage()!=null){
            contract.setSignImage(ossService.getURL(contract.getSignImage()).toString());
        }

        if(contract.getSignedPdfAddress()==null||contract.getSignedPdfAddress().equals("")){
            contract.setSignedPdfAddress(null);
        }else {
            contract.setSignedPdfAddress(ossService.getURL(contract.getSignedPdfAddress()).toString());
        }

        return contract;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getUploadParentContract(Integer contractId,String path) throws BaseServiceException {
        ParentContractBean contractPdf = parentCenterMapper.getParentContractByid(contractId);
        Integer id = contractPdf.getId();
        URL url = ossService.getURL(path);
        parentCenterMapper.editParentContract(path,id);
        ParentContract contract = parentCenterMapper.getParentContractById(id);
        contract.setSignImage(url.toString());
        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(contract);
        try {
            String outputDir = to+contract.getSn();
            File file =new File(outputDir);
            String jsonDir =  outputDir+"/index.json";
            System.out.println(jsonDir+"-----------生成json的目录-----------------");
            //上传json文件到每个目录当中
            Writer write = new OutputStreamWriter(new FileOutputStream(jsonDir), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
            String command = String.format("docker run --rm -i -v %s:/tmp -w %s --name relaxed relaxed /tmp/test.pug -l index.json --build-once --no-sandbox", outputDir, outputDir);
            System.out.println(command);
            commandUtil.execCommand(command);

            String localFileName = outputDir+"/test.pdf";
            String  toFileName = "contracts/parent/"+contract.getSn()+"signed"+".pdf";

           // MultipartFile mulFileByPath = commandUtil.getMulFileByPath(localFileName);
            ossService.uploadFile(toFileName,localFileName);
            parentCenterMapper.editParentContractPdf(toFileName,contractId);
//            List<String> paths = pdfConvertToImageService.pdfRendering(toFileName);
//            if (contractId != null && paths != null) {
//                saveFileImage(contractId, paths);
//            } else {
//                throw new BaseServiceException(ServiceExceptionsEnum.CLIENT_OPERATE_EXCEPTION);
//            }



            //            //先保存contract数据
//            courseMngMapper.saveContract(contract);
//            //再保存contractpdf数据
//            ParentContractRequestBean pcrb =new ParentContractRequestBean();
//            pcrb.setStudentId(student.getStudentId());
//            pcrb.setSn(contract.getSn());
//            pcrb.setNumber(student.getNumber());
//            pcrb.setContractAddress(toFileName);
//            pcrb.setContractName(parentInfo.getParentName()+studentName+".pdf");
////            Integer personId = courseMngMapper.getPersonId(parentInfo.getTelephone());
//            pcrb.setPersonId(parentInfo.getParentId());
//            saveContractManageService.saveParentContract(pcrb);
            //把数据保存起来

            //  uploadPdf(localFileName,toFileName);
            //上传文件
            System.out.println("1111111111111111111111111111111111111111111");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

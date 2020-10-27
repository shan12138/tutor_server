package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.commonservice.IOSSService;
import com.dataee.tutorserver.commons.commonservice.ISavePDFService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ResourceRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.impl.SaveResourceManageServiceImpl;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.CancelAuditionClassBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.CreateFlowTypeBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.NodeBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.TeacherConsumeRequestBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFlowMapper;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFormMapper;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service.ICreateFlowService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.dataee.tutorserver.utils.RandomStringUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CreateFlowServiceImpl implements ICreateFlowService {

    private Logger logger = LoggerFactory.getLogger(CreateFlowServiceImpl.class);

    @Autowired
    private IOSSService ossService;

    @Autowired
    private CreateFlowMapper createFlowMapper;

    @Autowired
    private CreateFormMapper createFormMapper;

//    @Autowired
//    private ISavePDFService<TeacherConsumeRequestBean> savePDFService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createFlow(CreateFlowTypeBean createFlowTypeBean) throws BaseControllerException {
        Integer personId = SecurityUtil.getPersonId();
        Administrator administrator = createFlowMapper.getAdminById(personId);
        String workflowType = createFlowTypeBean.getType();
        if (workflowType.equals("陪伴式辅导试听课")) {
            //陪伴式流程
            WorkFlow workFlow =new WorkFlow();
            workFlow.setJoinTime(new Date());
            String sn = generateNum(workFlow.getJoinTime(),"PBS");
            workFlow.setWorkFlowSn(sn);
            workFlow.setWorkFlowName(createFlowTypeBean.getType());
            workFlow.setAdministrator(administrator);
            createFlowMapper.insertWorkFlow(workFlow);
            //创建节点
            Node node = new Node();
            node.setWorkFlow(workFlow);
            node.setOperator(administrator);
            node.setCreateTime(new Date());
            node.setSequenceNum(1);
            node.setStepNum(1);
            node.setType(1);
            createFlowMapper.insertNode(node);
            //创建任务
            Task task =new Task();
            task.setTaskName(workFlow.getWorkFlowName());
            task.setState("待办");
            task.setTaskHandler(administrator);
            task.setNode(node);
            task.setWorkFlow(workFlow);
            createFlowMapper.insertTask(task);

            // 个性化
            // 创建节点
            // 创建任务
            // 如果下一个人在这个流程中已经有了这个任务
            // 那么直接更新任务（任务关联新的节点，状态）
            // 否则创建新任务（任务关联新节点，待办）

        }
        else if (workflowType.equals("个性化辅导试听课")) {
            //陪伴式流程
            WorkFlow workFlow =new WorkFlow();
            workFlow.setJoinTime(new Date());
            String sn = generateNum(workFlow.getJoinTime(),"GXH");
            workFlow.setWorkFlowSn(sn);
            workFlow.setWorkFlowName(createFlowTypeBean.getType());
            workFlow.setAdministrator(administrator);
            createFlowMapper.insertWorkFlow(workFlow);
            //创建节点
            Node node = new Node();
            node.setWorkFlow(workFlow);
            node.setOperator(administrator);
            node.setCreateTime(new Date());
            node.setSequenceNum(1);
            node.setStepNum(1);//不断增加的step
            node.setType(1);
            createFlowMapper.insertNode(node);
            //创建任务
            Task task =new Task();
            task.setTaskName(workFlow.getWorkFlowName());
            task.setState("待办");
            task.setNode(node);
            task.setWorkFlow(workFlow);
            task.setTaskHandler(administrator);
            createFlowMapper.insertTask(task);
        }
        else if (workflowType.equals("预约记录")) {
            // 预约
            WorkFlow workFlow =new WorkFlow();
            workFlow.setJoinTime(new Date());
            String sn = generateNum(workFlow.getJoinTime(),"YY");
            workFlow.setWorkFlowSn(sn);
            workFlow.setWorkFlowName(createFlowTypeBean.getType());
            workFlow.setAdministrator(administrator);
            createFlowMapper.insertWorkFlow(workFlow);
            //创建节点
            Node node = new Node();
            node.setWorkFlow(workFlow);
            node.setOperator(administrator);
            node.setCreateTime(new Date());
            node.setSequenceNum(1);
            node.setStepNum(1);
            node.setType(8);
            createFlowMapper.insertNode(node);
            //创建任务
            Task task =new Task();
            task.setTaskName(workFlow.getWorkFlowName());
            task.setState("待办");
            task.setNode(node);
            task.setWorkFlow(workFlow);
            task.setTaskHandler(administrator);
            createFlowMapper.insertTask(task);
        }

    }

    public  String  generateNum(Date date,String type){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDate = dateFormat.format(date);
        int a = (int)(Math.random()*(9999-1000+1))+1000;
        String sn = type+formatDate+a;
        return sn;
    }

    @Override
    public NewPageInfo<WorkFlow> getAllFlow(String state,Integer adminId, Page page) {
        PageHelper.startPage(page.getPage(), 9);
        List<WorkFlow> workFlows = createFlowMapper.getAllFlow(state,adminId);
        PageInfo pageInfo = new PageInfo(workFlows);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public Administrator getAdminById(Integer id) {
        return createFlowMapper.getAdminById(id);
    }

    @Override
    public List<Administrator> getAdminByRole(Integer roleId) {
        return createFlowMapper.getAdminByRole(roleId);
    }

    @Override
    public WorkFlow getWorkFlowByWorkFlowId(Integer workFlowId) {
        return createFlowMapper.getWorkFlowByWorkFlowId(workFlowId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAudition(CancelAuditionClassBean cancelAuditionClassBean) throws BaseControllerException {
        Integer personId = SecurityUtil.getPersonId();
        WorkFlow workFlow = createFlowMapper.getWorkFlowById(cancelAuditionClassBean.getWorkFlowId());
        List<NodeBean> nodes = createFlowMapper.getNodesById(cancelAuditionClassBean.getWorkFlowId());
        if(nodes.size()>0 && nodes!=null){
            Administrator admin = createFlowMapper.getAdminById(personId);
            Node node =new Node();
            node.setOperator(admin);
            node.setType(6);
            node.setCreateTime(new Date());
            node.setSequenceNum(-2);
            node.setStepNum(nodes.get(nodes.size()-1).getStepNum()+1);
            node.setWorkFlow(workFlow);
            node.setOperationTime(new Date());
            node.setCreateTime(new Date());
            createFlowMapper.insertNode(node);
            Task newTask = new Task();
            newTask.setNode(node);
            newTask.setWorkFlow(workFlow);
            newTask.setTaskName(workFlow.getWorkFlowName());
            newTask.setState("已办");
            newTask.setTaskHandler(admin);
            createFlowMapper.insertTask(newTask);
            Operation operation =new Operation();
            operation.setNode(node);
            operation.setComment(cancelAuditionClassBean.getOperation().getComment());
            operation.setType(cancelAuditionClassBean.getOperation().getType());
            createFlowMapper.insertOperation(operation);
            createFlowMapper.editTaskState(null,workFlow.getWorkFlowId(),nodes.get(nodes.size()-1).getNodeId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeTeacher(CancelAuditionClassBean cancelAuditionClassBean) throws BaseControllerException, BaseServiceException {
        Integer personId = SecurityUtil.getPersonId();
        Integer nodeId = cancelAuditionClassBean.getNodeId();
        MatchTeacherForm matchTeacherForm =cancelAuditionClassBean.getMatchTeacherForm();
        MatchTeacherForm matchTeacherForms = createFormMapper.getMatchTeacherFormById(cancelAuditionClassBean.getNodeId());
        if(matchTeacherForm == null){
          throw  new BaseServiceException(ServiceExceptionsEnum.NOT_CHANGE_TEACHER);
        }else {
            WorkFlow workFlow = createFlowMapper.getWorkFlowById(cancelAuditionClassBean.getWorkFlowId());
            List<NodeBean> nodes = createFlowMapper.getNodesById(cancelAuditionClassBean.getWorkFlowId());
            MatchTeacherForm  teacherForm = matchTeacherForms;
            matchTeacherForm.setSn(teacherForm.getSn());
            matchTeacherForm.setVersion(teacherForm.getVersion()+1);
            matchTeacherForm.setNodeId(nodeId);
            matchTeacherForm.setIsChange(1);
            createFormMapper.saveMatchTeacherForm(matchTeacherForm);
            Administrator operator = createFlowMapper.getAdminById(personId);
            Administrator submiter = createFlowMapper.getAdminById(personId);
            Node newNode  = new Node();
            newNode.setWorkFlow(workFlow);
            newNode.setType(3);
            newNode.setOperator(operator);
            newNode.setCreateTime(new Date());
            newNode.setStepNum(nodes.get(nodes.size()-1).getStepNum()+1);
            newNode.setSequenceNum(3);
            createFlowMapper.insertNode(newNode);
            Task task = createFormMapper.getTaskByAdminAndWorkFlow(cancelAuditionClassBean.getWorkFlowId(),cancelAuditionClassBean.getAdminId());
            if(task == null){
                Task newTask = new Task();
                newTask.setNode(newNode);
                newTask.setWorkFlow(workFlow);
                newTask.setTaskName(workFlow.getWorkFlowName());
                newTask.setState("待办");
                newTask.setTaskHandler(submiter);
                createFlowMapper.insertTask(newTask);

            }else{
                Task newTask = new Task();
                newTask.setTaskId(task.getTaskId());
                newTask.setNode(newNode);
                newTask.setWorkFlow(workFlow);
                newTask.setTaskName(workFlow.getWorkFlowName());
                newTask.setState("待办");
                newTask.setTaskHandler(submiter);
                createFlowMapper.editTask(newTask);
            }
            //判断是否有操作
            Node node = createFlowMapper.getNodeById(nodeId);
            Operation isExistOperation = createFlowMapper.getOperationByNodeId(nodeId);
            if(isExistOperation==null){
                Operation operation =new Operation();
                operation.setNode(node);
                operation.setComment(cancelAuditionClassBean.getOperation().getComment());
                operation.setType(cancelAuditionClassBean.getOperation().getType());
                createFlowMapper.insertOperation(operation);
            }else {
                Operation operation =new Operation();
                operation.setNodeId(nodeId);
                operation.setComment(cancelAuditionClassBean.getOperation().getComment());
                operation.setType(cancelAuditionClassBean.getOperation().getType());
                createFlowMapper.editOperation(nodeId);
            }
        }
    }

    @Override
    public String getTeacherConsumeUrl(Integer nodeId) throws BaseServiceException {
      String  fileName =  createFlowMapper.getTeacherConsumeUrl(nodeId);
        if (fileName == null || fileName == "") {
            throw new BaseServiceException(ServiceExceptionsEnum.FILE_NOT_EXIT);
        }
        //生成连接地址
        URL url = ossService.getURL(fileName);
        return url.toString();
    }
}

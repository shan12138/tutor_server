package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.SaveReviewForm;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.SubmitFlowBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFlowMapper;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFormMapper;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service.ICreateFormService;
import com.dataee.tutorserver.utils.RandomStringUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CreateFormServiceImpl implements ICreateFormService {
    @Autowired
    private CreateFormMapper createFormMapper;
    @Autowired
    private CreateFlowMapper createFlowMapper;

    @Override
    public void saveRegisterForm(RegisterForm registerForm) throws BaseServiceException, BaseControllerException {
        RegisterForm form = createFormMapper.getRegisterFormById(registerForm.getNodeId());
        if(form == null){
            createFormMapper.savePersonalRegisterForm(registerForm);
        }else {
            createFormMapper.editRegisterForm(registerForm);
        }
    }

    @Override
    public void saveAccompanyRegisterForm(AccompanyRegisterForm accompanyRegisterForm) throws BaseServiceException, BaseControllerException {
        AccompanyRegisterForm form = createFormMapper.getAccompanyRegisterFormById(accompanyRegisterForm.getNodeId());
        if(form == null){
            createFormMapper.saveAccompanyRegisterForm(accompanyRegisterForm);
        }else {
            createFormMapper.editAccompanyRegisterForm(accompanyRegisterForm);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveReviewForm(SaveReviewForm saveReviewForm) throws BaseServiceException, BaseControllerException {
        HeadMasterExamine examine = createFormMapper.getHeadMasterExamineById(saveReviewForm.getHeadMasterExamine().getNodeId());
        if(examine == null){
            createFormMapper.saveHeadMasterExamine(saveReviewForm.getHeadMasterExamine());
        }else {
            createFormMapper.editHeadMasterExamine(saveReviewForm.getHeadMasterExamine());
        }
        if(saveReviewForm.getRegisterForm()!=null&&saveReviewForm.getRegisterForm().getNodeId()!=null){
            createFormMapper.editRegisterForm(saveReviewForm.getRegisterForm());
        }else if(saveReviewForm.getAccompanyRegisterForm()!=null&&saveReviewForm.getAccompanyRegisterForm().getNodeId()!=null){
            createFormMapper.editAccompanyRegisterForm(saveReviewForm.getAccompanyRegisterForm());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMatchTeacherForm(MatchTeacherForm matchTeacherForm) throws BaseServiceException, BaseControllerException {
        MatchTeacherForm form = createFormMapper.getMatchTeacherFormById(matchTeacherForm.getNodeId());
        if(form == null){
            matchTeacherForm.setSn(RandomStringUtil.getRandomString());
            matchTeacherForm.setVersion(1);
            createFormMapper.saveMatchTeacherForm(matchTeacherForm);
        }else {
            matchTeacherForm.setSn(form.getSn());
            matchTeacherForm.setVersion(form.getVersion()+1);
            createFormMapper.saveMatchTeacherForm(matchTeacherForm);
        }
    }

    @Override
    public void saveTrainForm(TrainForm trainForm) throws BaseServiceException, BaseControllerException {
        TrainForm form = createFormMapper.getTrainFormById(trainForm.getNodeId());
        if(form ==null){
            createFormMapper.saveTrainForm(trainForm);
        }else {
            createFormMapper.editTrainForm(trainForm);
        }



    }

    @Override
    public void saveAuditionFeedbackForm(AuditionFeedbackForm auditionFeedbackForm) throws BaseServiceException, BaseControllerException {
        AuditionFeedbackForm form = createFormMapper.getAuditionFeedbackFormById(auditionFeedbackForm.getNodeId());
        if(form == null){
            createFormMapper.saveAuditionFeedbackForm(auditionFeedbackForm);
        }else {
            createFormMapper.editAuditionFeedbackForm(auditionFeedbackForm);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitRegisterForm(SubmitFlowBean submitFlowBean) throws BaseServiceException, BaseControllerException {
       // Integer personId = SecurityUtil.getPersonId();
        Integer stepNum = createFlowMapper.getNodeStepNum(submitFlowBean.getNodeId());
        //编辑节点的操作时间
        createFlowMapper.editNode(new Date(),submitFlowBean.getAdminId(),submitFlowBean.getNodeId());
        //改变这个任务的状态
        createFlowMapper.editTaskState("已办",submitFlowBean.getWorkFlowId(),submitFlowBean.getNodeId());
        WorkFlow workFlow = createFlowMapper.getWorkFlowById(submitFlowBean.getWorkFlowId());
        Administrator admin = createFlowMapper.getAdminById(submitFlowBean.getAdminId());
        Node node  = new Node();
        node.setWorkFlow(workFlow);
        node.setType(2);
        node.setOperator(admin);
        node.setStepNum(stepNum+1);
        node.setCreateTime(new Date());
        node.setSequenceNum(2);
        createFlowMapper.insertNode(node);
        // TODO: 检测该流程是否有下个节点处理者的已存在任务
        // task(workflowId, adminId)
        // 如果有的话直接使用已存在的任务，否则创建新任务
        // 处理Task
        // Task task = getTask(workflowId, adminId);
        // if ( taks == null ) { task = new Task(); }
//        Task task = new Task();
        //  task.setNode(node);
        //  task.setWorkFlow(workFlow);
        //  task.setTaskName(workFlow.getWorkFlowName());
        // task.setState("待办");

        Task task = createFormMapper.getTaskByAdminAndWorkFlow(submitFlowBean.getWorkFlowId(),node.getNodeId());
        if(task == null){
            Task newTask = new Task();
            newTask.setNode(node);
            newTask.setWorkFlow(workFlow);
            newTask.setTaskName(workFlow.getWorkFlowName());
            newTask.setState("待办");
            newTask.setTaskHandler(admin);
            createFlowMapper.insertTask(newTask);

        }else{
            Task newTask = new Task();
            newTask.setTaskId(task.getTaskId());
            newTask.setNode(node);
            newTask.setWorkFlow(workFlow);
            newTask.setTaskName(workFlow.getWorkFlowName());
            newTask.setState("待办");
            newTask.setTaskHandler(admin);
            createFlowMapper.editTask(newTask);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitReviewForm(SubmitFlowBean submitFlowBean) throws BaseServiceException, BaseControllerException {
        Integer stepNum = createFlowMapper.getNodeStepNum(submitFlowBean.getNodeId());
        createFlowMapper.editNode(new Date(),submitFlowBean.getAdminId(),submitFlowBean.getNodeId());
        //改变这个任务的状态
        createFlowMapper.editTaskState("已办",submitFlowBean.getWorkFlowId(),submitFlowBean.getNodeId());
        WorkFlow workFlow = createFlowMapper.getWorkFlowById(submitFlowBean.getWorkFlowId());
        Administrator admin = createFlowMapper.getAdminById(submitFlowBean.getAdminId());
        Node node  = new Node();
        node.setWorkFlow(workFlow);
        node.setType(3);
        node.setOperator(admin);
        node.setCreateTime(new Date());
        node.setStepNum(stepNum+1);
        node.setSequenceNum(3);
        createFlowMapper.insertNode(node);
        Task task = createFormMapper.getTaskByAdminAndWorkFlow(submitFlowBean.getWorkFlowId(),submitFlowBean.getAdminId());
        if(task == null){
            Task newTask = new Task();
            newTask.setNode(node);
            newTask.setWorkFlow(workFlow);
            newTask.setTaskName(workFlow.getWorkFlowName());
            newTask.setState("待办");
            newTask.setTaskHandler(admin);
            createFlowMapper.insertTask(newTask);

        }else{
            Task newTask = new Task();
            newTask.setTaskId(task.getTaskId());
            newTask.setNode(node);
            newTask.setWorkFlow(workFlow);
            newTask.setTaskName(workFlow.getWorkFlowName());
            newTask.setState("待办");
            newTask.setTaskHandler(admin);
            createFlowMapper.editTask(newTask);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitMatchTeacherForm(SubmitFlowBean submitFlowBean) throws BaseServiceException, BaseControllerException {
        Integer stepNum = createFlowMapper.getNodeStepNum(submitFlowBean.getNodeId());
        createFlowMapper.editNode(new Date(),submitFlowBean.getAdminId(),submitFlowBean.getNodeId());
        //改变这个任务的状态
        createFlowMapper.editTaskState("已办",submitFlowBean.getWorkFlowId(),submitFlowBean.getNodeId());
        WorkFlow workFlow = createFlowMapper.getWorkFlowById(submitFlowBean.getWorkFlowId());
        Administrator admin = createFlowMapper.getAdminById(submitFlowBean.getAdminId());
        Node node  = new Node();
        node.setWorkFlow(workFlow);
        node.setType(4);
        node.setOperator(admin);
        node.setStepNum(stepNum+1);
        node.setCreateTime(new Date());
        node.setSequenceNum(4);
        createFlowMapper.insertNode(node);
        Task task = createFormMapper.getTaskByAdminAndWorkFlow(submitFlowBean.getWorkFlowId(),submitFlowBean.getNodeId());
        if(task == null){
            Task newTask = new Task();
            newTask.setNode(node);
            newTask.setWorkFlow(workFlow);
            newTask.setTaskName(workFlow.getWorkFlowName());
            newTask.setState("待办");
            newTask.setTaskHandler(admin);
            createFlowMapper.insertTask(newTask);

        }else{
            Task newTask = new Task();
            newTask.setTaskId(task.getTaskId());
            newTask.setNode(node);
            newTask.setWorkFlow(workFlow);
            newTask.setTaskName(workFlow.getWorkFlowName());
            newTask.setState("待办");
            newTask.setTaskHandler(admin);
            createFlowMapper.editTask(newTask);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitTrainForm(SubmitFlowBean submitFlowBean) throws BaseServiceException, BaseControllerException {
        //Integer personId = SecurityUtil.getPersonId();
        Integer stepNum = createFlowMapper.getNodeStepNum(submitFlowBean.getNodeId());
        createFlowMapper.editNode(new Date(),submitFlowBean.getAdminId(),submitFlowBean.getNodeId());
        //改变这个任务的状态
        createFlowMapper.editTaskState("已办",submitFlowBean.getWorkFlowId(),submitFlowBean.getNodeId());
        WorkFlow workFlow = createFlowMapper.getWorkFlowById(submitFlowBean.getWorkFlowId());
        Administrator admin = createFlowMapper.getAdminById(submitFlowBean.getAdminId());
        Node node  = new Node();
        node.setWorkFlow(workFlow);
        node.setType(5);
        node.setOperator(admin);
        node.setStepNum(stepNum+1);
        node.setCreateTime(new Date());
        node.setSequenceNum(5);
        createFlowMapper.insertNode(node);
        Task task = createFormMapper.getTaskByAdminAndWorkFlow(submitFlowBean.getWorkFlowId(),submitFlowBean.getAdminId());
        if(task == null){
            Task newTask = new Task();
            newTask.setNode(node);
            newTask.setWorkFlow(workFlow);
            newTask.setTaskName(workFlow.getWorkFlowName());
            newTask.setState("待办");
            newTask.setTaskHandler(admin);
            createFlowMapper.insertTask(newTask);

        }else{
            Task newTask = new Task();
            newTask.setTaskId(task.getTaskId());
            newTask.setNode(node);
            newTask.setWorkFlow(workFlow);
            newTask.setTaskName(workFlow.getWorkFlowName());
            newTask.setState("待办");
            newTask.setTaskHandler(admin);
            createFlowMapper.editTask(newTask);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitAuditionFeedbackForm(SubmitFlowBean submitFlowBean) throws BaseServiceException, BaseControllerException {
        //编辑节点的操作时间
        Integer personId = SecurityUtil.getPersonId();
        Integer stepNum = createFlowMapper.getNodeStepNum(submitFlowBean.getNodeId());
        createFlowMapper.editNode(new Date(),personId,submitFlowBean.getNodeId());
        //改变这个任务的状态
        createFlowMapper.editTaskState("已办",submitFlowBean.getWorkFlowId(),submitFlowBean.getNodeId());
        WorkFlow workFlow = createFlowMapper.getWorkFlowById(submitFlowBean.getWorkFlowId());
        Administrator admin = createFlowMapper.getAdminById(submitFlowBean.getAdminId());
        Node node  = new Node();
        node.setWorkFlow(workFlow);
        node.setType(11);
        node.setOperator(admin);
        node.setStepNum(stepNum+1);
        node.setCreateTime(new Date());
        node.setSequenceNum(6);
        createFlowMapper.insertNode(node);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
        public void submitRefuseReviewForm(SubmitFlowBean submitFlowBean) throws BaseControllerException {
            // Integer personId = SecurityUtil.getPersonId();
            Integer stepNum = createFlowMapper.getNodeStepNum(submitFlowBean.getNodeId());
            createFlowMapper.editNode(new Date(),submitFlowBean.getAdminId(),submitFlowBean.getNodeId());
            //改变这个任务的状态
            createFlowMapper.editTaskState("已办",submitFlowBean.getWorkFlowId(),submitFlowBean.getNodeId());

            WorkFlow workFlow = createFlowMapper.getWorkFlowById(submitFlowBean.getWorkFlowId());
            Node preNode = createFlowMapper.getNodeById(submitFlowBean.getNodeId());
            Administrator admin = createFlowMapper.getAdminById(preNode.getOperator().getId());
            Node node  = new Node();
            node.setWorkFlow(workFlow);
            node.setType(1);
            node.setOperator(admin);
            node.setCreateTime(new Date());
            node.setStepNum(stepNum+1);
            node.setSequenceNum(1);
            createFlowMapper.insertNode(node);
            Task task = createFormMapper.getTaskByAdminAndWorkFlow(submitFlowBean.getWorkFlowId(),submitFlowBean.getAdminId());
            if(task == null){
                Task newTask = new Task();
                newTask.setNode(node);
                newTask.setWorkFlow(workFlow);
                newTask.setTaskName(workFlow.getWorkFlowName());
                newTask.setState("待办");
                newTask.setTaskHandler(admin);
                createFlowMapper.insertTask(newTask);

            }else{
                Task newTask = new Task();
                newTask.setTaskId(task.getTaskId());
                newTask.setNode(node);
                newTask.setWorkFlow(workFlow);
                newTask.setTaskName(workFlow.getWorkFlowName());
                newTask.setState("待办");
                newTask.setTaskHandler(admin);
                createFlowMapper.editTask(newTask);
            }
    }
}
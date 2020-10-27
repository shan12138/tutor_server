package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.BindAuditionClassBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.NodeBean;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateAppointmentMapper;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFlowMapper;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFormMapper;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.service.ICreateAppointmentService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.dataee.tutorserver.utils.RandomStringUtil;
import com.dataee.tutorserver.utils.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CreateAppointServiceImpl implements ICreateAppointmentService {
    @Autowired
    private CreateAppointmentMapper createAppointmentMapper;

    @Autowired
    private CreateFlowMapper createFlowMapper;

    @Autowired
    private CreateFormMapper createFormMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void appointAuditionClass(BindAuditionClassBean bindAuditionClassBean) throws BaseControllerException {
        createFlowMapper.editNode(new Date(),bindAuditionClassBean.getAdminId(),bindAuditionClassBean.getAppointment().getNodeId());
        createFlowMapper.editTaskState("已办",bindAuditionClassBean.getWorkFlowId(),bindAuditionClassBean.getAppointment().getNodeId());
        Appointment appointment = createAppointmentMapper.getAppointment(bindAuditionClassBean.getAppointment().getNodeId());
        if(appointment == null){
            bindAuditionClassBean.getAppointment().setSn(RandomStringUtil.getRandomString());
            bindAuditionClassBean.getAppointment().setVersion(1);
            createAppointmentMapper.saveAppointment(bindAuditionClassBean.getAppointment());
        }else {
            bindAuditionClassBean.getAppointment().setSn(appointment.getSn());
            bindAuditionClassBean.getAppointment().setVersion(appointment.getVersion()+1);
            createAppointmentMapper.saveAppointment( bindAuditionClassBean.getAppointment());
        }
        // TODO: 2019/11/26   增加限制

        //判断目前课程记录绑定了没有，
        // 如果绑定了，看有没有审核，
        //如果没有绑定就进行绑定，

        WorkFlow workFlow = createFlowMapper.getWorkFlowById(bindAuditionClassBean.getWorkFlowId());
        List<NodeBean> nodes = createFlowMapper.getNodesById(workFlow.getWorkFlowId());
        Administrator submitter = createFlowMapper.getAdminById(bindAuditionClassBean.getAdminId());
        Integer personId = SecurityUtil.getPersonId();
        Administrator operator = createFlowMapper.getAdminById(personId);
        if(nodes.size()>0){
            Node node  =new Node();
            node.setWorkFlow(workFlow);
            node.setSubmitter(submitter);
            node.setOperator(operator);
            node.setStepNum(nodes.get(nodes.size()-1).getStepNum()+1);
            node.setSequenceNum(2);
           // node.setOperationTime(bindAuditionClassBean.getAppointment().getTime());
            node.setType(9);
            node.setCreateTime(new Date());
            createFlowMapper.insertNode(node);

            Operation operation =new Operation();
            operation.setNode(node);
            operation.setType("提交");
            operation.setComment("提交");
            createFlowMapper.insertOperation(operation);

            Task task = createFormMapper.getTaskByAdminAndWorkFlow(workFlow.getWorkFlowId(),bindAuditionClassBean.getAdminId());
            if(task == null){
                Task newTask = new Task();
                newTask.setNode(node);
                newTask.setWorkFlow(workFlow);
                newTask.setTaskName(workFlow.getWorkFlowName());
                newTask.setState("待办");
                newTask.setTaskHandler(submitter);
                createFlowMapper.insertTask(newTask);

            }else{
                Task newTask = new Task();
                newTask.setTaskId(task.getTaskId());
                newTask.setNode(node);
                newTask.setWorkFlow(workFlow);
                newTask.setTaskName(workFlow.getWorkFlowName());
                newTask.setState("待办");
                newTask.setTaskHandler(submitter);
                createFlowMapper.editTask(newTask);
            }
        }
    }

    @Override
    public NewPageInfo<NodeBean> appointList(Integer workFlowId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<NodeBean> nodes = createAppointmentMapper.getAppointList(workFlowId);
        PageInfo pageInfo = new PageInfo(nodes);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void trainDepartmentAccept(Integer workFlowId,Integer nodeId) throws BaseControllerException {
        Integer personId = SecurityUtil.getPersonId();
        createFlowMapper.editNode(new Date(),personId,nodeId);
        WorkFlow workFlow = createFlowMapper.getWorkFlowById(workFlowId);
        createFlowMapper.editTaskState("已办",workFlow.getWorkFlowId(),nodeId);
        Node preNode = createAppointmentMapper.getNodeById(nodeId);
        Node node = new Node();
        node.setWorkFlow(workFlow);
        node.setSubmitter(preNode.getOperator());
        node.setOperator(preNode.getSubmitter());
        node.setType(10);
        node.setSequenceNum(3);
        node.setStepNum(preNode.getStepNum() + 1);
        node.setCreateTime(new Date());
        createFlowMapper.insertNode(node);
        Operation operation =new Operation();
        operation.setNode(node);
        operation.setType("已完成");
        operation.setComment("已完成");
        createFlowMapper.insertOperation(operation);
        createFlowMapper.editTaskState("已办",workFlowId,node.getNodeId());

    }

    @Override
    public void trainDepartmentRefuse(Integer workFlowId, Integer nodeId) throws BaseControllerException {
        Integer personId = SecurityUtil.getPersonId();
        createFlowMapper.editNode(new Date(),personId,nodeId);
        WorkFlow workFlow = createFlowMapper.getWorkFlowById(workFlowId);
        createFlowMapper.editTaskState("已办",workFlow.getWorkFlowId(),nodeId);
        Node preNode = createAppointmentMapper.getNodeById(nodeId);
        Node node = new Node();
        node.setWorkFlow(workFlow);
        node.setSubmitter(preNode.getOperator());
        node.setOperator(preNode.getSubmitter());
        node.setType(8);
        node.setSequenceNum(1);
        node.setStepNum(preNode.getStepNum() + 1);
        node.setCreateTime(new Date());
        createFlowMapper.insertNode(node);
        Task newTask = new Task();
        newTask.setNode(node);
        newTask.setWorkFlow(workFlow);
        newTask.setTaskName(workFlow.getWorkFlowName());
        newTask.setState("待办");
        newTask.setTaskHandler(preNode.getOperator());
        createFlowMapper.insertTask(newTask);
        Operation operation =new Operation();
        operation.setNode(node);
        operation.setType("驳回");
        operation.setComment("驳回");
        createFlowMapper.insertOperation(operation);
    }
}

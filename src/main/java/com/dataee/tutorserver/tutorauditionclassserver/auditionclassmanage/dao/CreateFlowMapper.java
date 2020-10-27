package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao;

import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.NodeBean;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Mapper
public interface CreateFlowMapper {

    @Select("select * from admin  where id  = #{id}")
    Administrator getAdminById(Integer id);

    @Select("select * from work_flow where work_flow_id = #{workFlowId}")
    @Results(
            id = "getWorkFlow",
            value = {
                    @Result(column = "work_flow_id",property = "workFlowId"),
                    @Result(column = "initiation_time",property = "joinTime"),
                    @Result(column = "work_flow_id", property = "nodes",
                            many = @Many(select = "getNodesById")),
            } )
    WorkFlow getWorkFlowByWorkFlowId(Integer workFlowId);

    @Select("select * from node  where  work_flow_id = #{workFlowId}")
    @Results(
            value = {
                    @Result(column = "node_id", property = "nodeId"),
                    @Result(column = "operator", property = "operator",
                            one = @One(select = "getAdminById")),
                    @Result(column = "submitter", property = "submitter",
                            one = @One(select = "getAdminById")),
                    @Result(column = "node_id", property = "registerForm",
                            one = @One(select = "com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFormMapper.getRegisterFormById")),
                    @Result(column = "node_id", property = "accompanyRegisterForm",
                            one = @One(select = "com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFormMapper.getAccompanyRegisterFormById")),
                    @Result(column = "node_id", property = "headMasterExamine",
                            one = @One(select = "com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFormMapper.getHeadMasterExamineById")),
                    @Result(column = "node_id", property = "matchTeacherForm",
                            one = @One(select = "com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFormMapper.getMatchTeacherFormById")),
                    @Result(column = "node_id", property = "trainForm",
                            one = @One(select = "com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFormMapper.getTrainFormById")),
                    @Result(column = "node_id", property = "auditionFeedbackForm",
                            one = @One(select = "com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFormMapper.getAuditionFeedbackFormById")),
                    @Result(column = "node_id", property = "operation",
                            one = @One(select = "getOperationByNodeId")),
            })
    List<NodeBean> getNodesById(Integer workFlowId);

    @Select("select * from operation where node_id  = #{nodeId}")
    Operation getOperationByNodeId(Integer nodeId);


    @Select("select * from work_flow where work_flow_id = #{workFlowId}")
    WorkFlow getWorkFlowById(Integer workFlowId);

    @Insert("insert into work_flow (work_flow_sn,work_flow_name,sponsor,initiation_time)values (#{workFlowSn},#{workFlowName},#{administrator.id},#{joinTime})")
    @Options(useGeneratedKeys = true, keyProperty = "workFlowId", keyColumn = "work_flow_id")
    void   insertWorkFlow(WorkFlow workFlow);

    @Insert("insert into task (work_flow_id,node_id,task_name,task_handler,state)values (#{workFlow.workFlowId},#{node.nodeId},#{taskName},#{taskHandler.id},#{state})")
    void   insertTask(Task task);

    @Update("update task set work_flow_id = #{workFlow.workFlowId},node_id =#{node.nodeId},task_name = #{taskName},task_handler = #{taskHandler.id},state = #{state} where task_id = #{taskId} ")
    void  editTask(Task task);

    @Insert("insert into node (work_flow_id,operator,submitter,operation_time,create_time,sequence_num,step_num,type)values(#{workFlow.workFlowId},#{operator.id},#{submitter.id},#{operationTime},#{createTime},#{sequenceNum},#{stepNum},#{type})")
    @Options(useGeneratedKeys = true, keyProperty = "nodeId", keyColumn = "node_id")
    void   insertNode(Node node);

    @Select("SELECT work_flow.work_flow_id,work_flow.work_flow_name,work_flow.initiation_time  FROM task \n" +
            "INNER JOIN  work_flow ON work_flow.work_flow_id = task.work_flow_id\n" +
            "INNER JOIN node ON node.node_id = task.node_id\n" +
            "WHERE state = #{state} and task_handler = #{adminId} ")
    @Results(
            id = "getTaskList",
            value = {
                    @Result(column = "work_flow_id",property = "workFlowId"),
                    @Result(column = "initiation_time",property = "joinTime"),
                    @Result(column = "work_flow_id", property = "nodes",
                            many = @Many(select = "getNodesByWorkFlowId")),
            }
    )
    List<WorkFlow>  getAllFlow(@Param("state") String state,@Param("adminId") Integer adminId);

    @Select("select * from node  where  work_flow_id = #{workFlowId} ")
    @Results(
            value = {
            @Result(column = "operator", property = "operator",
                    one = @One(select = "getAdminById")),
                    @Result(column = "submitter", property = "submitter",
                            one = @One(select = "getAdminById")),
    })
    List<Node> getNodesByWorkFlowId(Integer workFlowId);

    @Update("update task set state =#{state} where work_flow_id =#{workFlowId} and  node_id = #{nodeId} ")
    void editTaskState(@Param("state") String state,@Param("workFlowId") Integer workFlowId,@Param("nodeId") Integer nodeId);

    @Select("select step_num from node where node_id = #{nodeId}")
    Integer getNodeStepNum(Integer nodeId);

    @Update("update  node set operation_time = #{operationTime},submitter=#{submitterId} where node_id = #{nodeId}")
    void  editNode(@Param("operationTime")Date operationTime,@Param("submitterId")Integer submitterId,  @Param("nodeId") Integer nodeId);

    @Select("SELECT a.id,a.admin_name FROM admin a INNER JOIN admin_auth aa ON  a.admin_id = aa.admin_id WHERE aa.role_id=#{roleId}")
     List<Administrator> getAdminByRole(Integer roleId);

    @Insert("insert into operation (node_id,type,comment) values (#{node.nodeId},#{type},#{comment})")
    void  insertOperation(Operation operation);

    @Update("update operation set node_id = #{nodeId}, type = #{type},comment = #{comment} where node_id =#{nodeId} ")
    void  editOperation(Integer nodeId);

    @Select("select * from node where node_id = #{nodeId}")
    @Results(
            value = {
                    @Result(column = "operator", property = "operator",
                            one = @One(select = "getAdminById")),
            })
    Node getNodeById(Integer nodeId);

    @Select("SELECT pdf_address from  match_teacher_form  where node_id = #{nodeId}  \n" +
            "AND VERSION = (SELECT MAX(version) from  match_teacher_form  where node_id = #{nodeId} ) ")
    String  getTeacherConsumeUrl(Integer nodeId);

    @Update("update task set state = null where work_flow_id = #{workFlowId}")
    void updateTaskState(@Param("state")String state,Integer workFlowId);



























}

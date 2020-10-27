package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao;
import com.dataee.tutorserver.entity.Appointment;
import com.dataee.tutorserver.entity.Node;
import com.dataee.tutorserver.entity.WorkFlow;
import com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean.NodeBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CreateAppointmentMapper {
   @Insert("insert into  appointment (work_flow_sn,sn,version,time,way,node_id) values(#{workFlowSn},#{sn},#{version},#{time},#{way},#{nodeId})  ")
   void  saveAppointment(Appointment appointment);

   @Select("select * from  appointment where work_flow_sn =#{workFlowSn}")
    List<Appointment> getAppointments(String workFlowSn);

   @Update("update appointment set work_flow_sn =#{workFlowSn},time=#{time},way =#{way}")
    void editAppointment(Appointment appointment);

   @Select("select * from work_flow where work_flow_sn =#{workFlowSn}")
    WorkFlow getWorkFlow(String workFlowSn);

   @Select("select * from node where node_id = #{nodeId}")
   @Results({
           @Result(column = "node_id", property = "nodeId"),
           @Result(column = "operator", property = "operator",
                   one = @One(select = "com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFlowMapper.getAdminById")),
           @Result(column = "submitter", property = "submitter",
                   one = @One(select = "com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFlowMapper.getAdminById"))
   })
    Node getNodeById(Integer nodeId);



   @Select("select * from appointment where node_id =#{nodeId} and version =  (select max(version) from appointment where node_id =#{nodeId} )")
   Appointment getAppointment(Integer nodeId);

   @Select("select * from node where work_flow_id =#{workFlowId}")
   @Results({
           @Result(column = "node_id", property = "nodeId"),
           @Result(column = "operator", property = "operator",
                   one = @One(select = "com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFlowMapper.getAdminById")),
           @Result(column = "submitter", property = "submitter",
                   one = @One(select = "com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFlowMapper.getAdminById")),
           @Result(column = "node_id",property = "appointment",
                   one = @One(select = "getAppointment")),
           @Result(column = "node_id", property = "operation",
                   one = @One(select = "com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao.CreateFlowMapper.getOperationByNodeId")),
   })
    List<NodeBean> getAppointList(Integer workFlowId);
}

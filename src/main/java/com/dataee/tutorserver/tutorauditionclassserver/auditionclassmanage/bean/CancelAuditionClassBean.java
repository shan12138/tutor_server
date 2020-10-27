package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean;
import com.dataee.tutorserver.entity.MatchTeacherForm;
import com.dataee.tutorserver.entity.Operation;
import com.dataee.tutorserver.entity.WorkFlow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelAuditionClassBean {
    private Integer workFlowId;
    private Integer nodeId;
    private MatchTeacherForm matchTeacherForm;
    private Operation operation;
    private Integer adminId;
}

package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean;

import com.dataee.tutorserver.entity.Appointment;
import com.dataee.tutorserver.entity.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BindAuditionClassBean {
    private Appointment appointment;
    @NotNull(message = "提交人不能为空")
    private Integer adminId;
    @NotNull(message = "流程id不能为空")
    private Integer  workFlowId;
}

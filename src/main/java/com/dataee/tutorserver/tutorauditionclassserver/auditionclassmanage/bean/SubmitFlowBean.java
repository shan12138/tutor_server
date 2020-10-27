package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean;

import com.dataee.tutorserver.entity.WorkFlow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitFlowBean {
   @NotNull(message = "流程id不能为空")
   private Integer workFlowId;
   @NotNull(message = "节点id不能为空")
   private Integer nodeId;
   @NotNull(message = "提交人不能为空")
   private Integer adminId;

}

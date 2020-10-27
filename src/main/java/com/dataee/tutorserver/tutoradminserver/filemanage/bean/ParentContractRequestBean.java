package com.dataee.tutorserver.tutoradminserver.filemanage.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 10:46
 */
@Data
public class ParentContractRequestBean extends ContractRequestBean {
    @NotBlank(message = "学生的身份证号不能为空")
    private String idCard;
    @NotNull(message = "没有指定课程")
    private Integer courseId;
    @NotNull(message = "没有指定学生")
    private Integer studentId;

    public ParentContractRequestBean() {
        super();
    }

    public ParentContractRequestBean(Integer contractId,@NotNull(message = "上传文件编号不能为空") Integer parentContractId,
                                     @NotNull(message = "没有指定课程")Integer courseId,
                                     @NotBlank(message = "上传文件不能为空") String contractName,
                                     @NotBlank(message = "上传的文件地址不能为空") String contractAddress,
                                     @NotNull(message = "未指定合同人") Integer personId, String role,
                                     @NotBlank(message = "合同身份证号不能为空") String idCard,
                                     @NotNull(message = "没有指定学生") Integer studentId) {
        super(contractId,parentContractId,courseId, contractName, contractAddress, personId, role);
        this.idCard = idCard;
        this.studentId = studentId;
    }
}

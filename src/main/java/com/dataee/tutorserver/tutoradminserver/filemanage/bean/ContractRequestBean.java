package com.dataee.tutorserver.tutoradminserver.filemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 11:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequestBean {
    private Integer contractId;
   // @NotBlank(message = "上传文件id不能为空")
    private Integer courseId;
    private Integer parentContractId;
    @NotBlank(message = "上传文件不能为空")
    private String contractName;
    @NotBlank(message = "上传的文件地址不能为空")
    private String contractAddress;
    @NotNull(message = "未指定合同人")
    private Integer personId;
    private String role;
}

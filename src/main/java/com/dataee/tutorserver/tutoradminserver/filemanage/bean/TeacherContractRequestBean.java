package com.dataee.tutorserver.tutoradminserver.filemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 上传文件请求信息
 *
 * @author JinYue
 * @CreateDate 2019/5/3 14:37
 */
@Data
public class TeacherContractRequestBean extends ContractRequestBean {

    public TeacherContractRequestBean() {
        super();
    }


    public TeacherContractRequestBean(Integer contractId,
                                      @NotBlank(message = "上传文件不能为空") String contractName,
                                      Integer parentContractId,
                                      Integer courseId,
                                      @NotBlank(message = "上传的文件地址不能为空") String contractAddress,
                                      @NotNull(message = "未指定合同人") Integer personId, String role) {
        super(contractId,parentContractId,courseId, contractName, contractAddress, personId, role);
    }
}

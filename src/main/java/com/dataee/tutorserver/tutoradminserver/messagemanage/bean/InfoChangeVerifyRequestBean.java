package com.dataee.tutorserver.tutoradminserver.messagemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 信息变更审核请求bean
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 21:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoChangeVerifyRequestBean {
    @NotNull(message = "审核ID不能为空")
    private Integer id;
    private String remark;
    /**
     * 2代表不通过
     * 1代表通过
     */
    private Integer accepted;
}

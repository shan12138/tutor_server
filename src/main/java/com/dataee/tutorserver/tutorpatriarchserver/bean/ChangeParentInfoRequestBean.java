package com.dataee.tutorserver.tutorpatriarchserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 16:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeParentInfoRequestBean {
    private Integer parentId;
    @NotBlank(message = "家长姓名不能为空")
    private String parentName;
    @NotNull(message = "家长性别不能为空")
    private Integer sex;
}

package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 公司员工
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/13 23:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    private Integer id;
    @NotBlank(message = "员工名称不能为空")
    private String staffName;
    @NotNull(message = "员工部门ID不能为空")
    private Integer departmentId;
    @NotBlank(message = "员工职位不能为空")
    private String position;
    @NotBlank(message = "员工电话不能为空")
    private String telephone;
    private String name;
    private String remark;
    private Integer state;
}

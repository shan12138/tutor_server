package com.dataee.tutorserver.tutoradminserver.adminmanage.bean;

import com.dataee.tutorserver.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/14 23:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminManage {
    /**
     * 基本信息表的ID编号
     */
    private Integer id;

    /**
     * 管理员角色表ID编号
     */
    private Integer adminAuthId;

    /**
     * 管理员名称
     */
    @NotBlank(message = "管理员名称不能为空")
    private String adminName;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * role_id编号
     */
    private Integer roleId;

    /**
     * roleName编号
     */
    private String roleName;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private Integer state;

    private String position;
    private String remark;
    private Department department;
    private Integer departmentId;
}

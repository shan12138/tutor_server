package com.dataee.tutorserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author ChenShanShan
 * 管理员的角色列表
 * @CreateDate 2019/11/4 11:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRole {
    private Integer roleId;
    private String roleName;
    private String remark;
    @JsonIgnore
    private Integer state;
    private Date createdAt;
    private String createAdminName;
    private Integer createdId;
    private Boolean isSystemCreated;

    public AdminRole(String remark, Integer state, Integer createdId) {
        this.remark = remark;
        this.state = state;
        this.createdAt = new Date();
        this.createdId = createdId;
    }
}

package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.*;

/**
 * 管理员类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administrator {
    /**
     * ID编号
     */
    private Integer id;

    /**
     * 管理员的ID
     */
    private String adminId;

    /**
     * 管理员名称
     */
    private String adminName;

    /**
     * 管理员的账号
     */
    private String account;

    /**
     * 管理员的密码
     */
    private String password;

    /**
     * 管理员的创建时间
     */
    private Date crt_date;

    /**
     * 管理员的创建者Id
     */
    private String crtId;

    /**
     * 管理员的修改信息时间
     */
    private Date modDate;

    /**
     * 角色名称
     */
    private AdminRole role;

    /**
     * 状态
     */
    private Integer state;
    private String position;
    private String remark;
    private Department department;
}
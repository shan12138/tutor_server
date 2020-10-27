package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 每个用户拥有的角色
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {
    private Integer id;
    /**
     * 该用户
     * 对应数据字段user_id
     */
    private User userId;
    private Role role;
    /**
     * 该字段表示用户对应的具体（存在问题）
     */
    private Integer personId;
    /**
     * 用户权限的创建人
     * 对应数据字段crt_id
     */
    private User crtUser;
    private Timestamp crtDate;
    private Timestamp modDate;
    private Integer state;
}

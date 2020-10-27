package com.dataee.tutorserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户的角色
 *
 * @Author JinYue
 * @CreateDate 2019/4/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Integer roleId;
    private String roleName;
    @JsonIgnore
    private Integer state;
}

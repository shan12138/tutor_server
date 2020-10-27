package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教员和家长信息的公共属性构成的实体
 *
 * @author JinYue
 * @CreateDate 2019/5/8 0:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Integer id;
    private String roleName;
    private String name;
    private String sex;
    private String telephone;
}

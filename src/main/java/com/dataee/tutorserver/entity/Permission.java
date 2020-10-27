package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/1 17:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private Integer id;
    private String name;
    private String description;
    private Action action;
    private Resource resource;
    private Boolean granted;
    private Permission parent;
    private String type;
}

package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    /**
     * ID编号
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父节点
     */
    private Integer parentId;

    /**
     * 状态
     */
    private Integer state;

    public Department(String name, Integer parentId, Integer state) {
        this.name = name;
        this.parentId = parentId;
        this.state = state;
    }
}
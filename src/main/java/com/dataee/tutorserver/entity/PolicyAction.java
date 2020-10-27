package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * policy行为限制
 *
 * @author JinYue
 * @CreateDate 2019/5/11 17:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyAction {
    private Integer id;
    private String serviceName;
    private String actionName;

    public String toJson() {
        return this.serviceName + ":" + this.actionName;
    }
}

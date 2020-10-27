package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * policy资源限制
 *
 * @author JinYue
 * @CreateDate 2019/5/11 17:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyResource {
    private Integer id;
    private String serviceName;
    private String region;
    private String accountId;
    private String relativeId;

    public String toJson() {
        return "acs:" + serviceName + ":" + region + ":" + accountId + ":" + relativeId;
    }
}


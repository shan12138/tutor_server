package com.dataee.tutorserver.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * policy描述
 *
 * @author JinYue
 * @CreateDate 2019/5/11 17:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyStatement {
    @Expose(serialize = false)
    private Integer id;
    @Expose
    private String effect;
    @Expose
    @SerializedName("Action")
    private List<PolicyAction> actions;
    @Expose
    @SerializedName("Resource")
    private List<PolicyResource> resources;

}

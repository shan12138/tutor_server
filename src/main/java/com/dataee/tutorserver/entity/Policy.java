package com.dataee.tutorserver.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


/**
 * STS权限policy
 *
 * @author JinYue
 * @CreateDate 2019/5/11 2:54
 */
@Data
@AllArgsConstructor
public class Policy {
    @Expose(serialize = false)
    private Integer id;
    @Expose
    private String version;
    @Expose
    @SerializedName("Statement")
        private List<PolicyStatement> statements;

    public Policy() {
        this.version = "1";
    }

}

package com.dataee.tutorserver.tutorpatriarchserver.bean;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 11:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentInvitationRequestBean {
    private String parentName;
    private Integer parentSex;
    private String telephone;
    private Integer productId;
    private String studentName;
    private Integer studentSex;
    private String productAttributeValue;
}

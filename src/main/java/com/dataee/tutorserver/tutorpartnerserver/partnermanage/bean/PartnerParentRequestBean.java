package com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 16:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerParentRequestBean {
    private String parentName;
    private String telephone;
    private String productAttributeValue;
    private Integer productId;
}

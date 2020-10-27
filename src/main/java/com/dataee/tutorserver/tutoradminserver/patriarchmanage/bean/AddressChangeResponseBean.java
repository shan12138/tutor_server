package com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员获得所有的地址审核信息
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 20:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressChangeResponseBean {
    /**
     * 合同ID
     */
    private Integer id;
    private Integer  contractId;
    private String   sn;
    private String parentName;
    /**
     *
     */
    private  String studentName;
    private  String parentTel;
    private  String teacherTel;
    private  String teacherName;
    /**
     * ///
     */
    private String oldAddress;
    private String newAddress;
    private String remark;
    /**
     * 0表示未审核
     * 1表示审核通过
     * 2表示驳回
     */
    private int confirmed;
}

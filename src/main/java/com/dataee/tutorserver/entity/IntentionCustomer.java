package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 意向客户表
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/29 20:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntentionCustomer {
    /**
     * 自增ID
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 身份（职业）
     */
    private String position;
    /**
     * 常住地址
     */
    private String permanentAddress;
    /**
     * 孩子基本信息
     */
    private String childBasicInfo;
    /**
     * 薄弱学科
     */
    private String weakDiscipline;
    /**
     * 理想家教性别
     */
    private Integer tutorSex;
    /**
     * 家教要求
     */
    private String tutorDemand;
    /**
     * 登记时间
     */
    private String enrollTime;
    /**
     * 状态
     */
    private Integer state;
}

package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 家长类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parent extends User {

    /**
     * ID编号
     */
    private Integer parentId;

    /**
     * 姓名
     */
    private String parentName;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 0女1男
     * 性别
     */
    private Integer sex;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 家庭住址
     */
    private List<Address> address;

    /**
     * 子女列表
     */
    private List<Student> student;

    /**
     * 合同列表
     */
    private List<ContractPdf> contractPdf;

    /**
     * 头像
     */
    private String headPicture;

    /**
     * 家长等级
     */
    private Integer parentLevel;

    /**
     * 自己的邀请码
     */
    private String inviteCode;

    /**
     * 邀请自己的邀请人的邀请码
     */
    private String invitedCode;

    /**
     * 所属的合伙人
     */
    private Partner partner;

    /**
     * 邀请它的家长
     */
    private Parent parent;

    /**
     * 邀请它的合伙人
     */
    private Partner invitePartner;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 分配的咨询师
     */
    private Administrator consultant;
    private ParentLevel parentLevelClass;
    private Integer parentInvitationId;
    private String adminState;
    private Integer partnerId;
    private String partnerState;
    private String parentState;
    private Date inviteSuccessTime;
    private Integer invitedParentId;
}
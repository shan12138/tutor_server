package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 邀请家长
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentInvitation {
   private Integer id;
   private String parentName;
   private Integer parentSex;
   private String telephone;
   private String studentName;
   private Integer studentSex;
   /**
    * 家长邀请人的邀请码
    */
   private String parentInvitationCode;
   /**
    * 合伙邀请人的邀请码
    */
   private String partnerCode;
   /**
    * 自己
    */
   private Parent parent;
   /**
    * 邀请自己的家长
    */
   private Parent invitedParent;
   private Integer invitedParentId;
   /**
    * 邀请别人的合伙人
    */
   private Partner invitedPartner;
   /**
    * 所属合伙人
    */
   private Partner partner;
   /**
    * 分配的咨询师
    */
   private Administrator administrator;
   private Product product;
   private String parentState;
   private String partnerState;
   private String adminState;
   /**
    * 填写的产品表格数据
    */
   private String productAttributeValue;
   /**
    * 邀请成功时间定为家长第一次购买课程时间（管理端第一次登记该家长的课程信息）
    */
   private Date inviteSuccessTime;
   private Integer partnerId;
   private Integer parentId;
}

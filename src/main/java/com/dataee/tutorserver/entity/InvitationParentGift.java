package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 邀请家长赠礼
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationParentGift {
  private  Integer id;
  private  Date invitationSuccessDate;
  private  Parent invitationParent;
  private  Parent parent;
  private  String status;

}

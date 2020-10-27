package com.dataee.tutorserver.tutoradminserver.teachermanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteTeacherGift {
    private  Integer id;
    private  String  telephone;
    private  String  invitePerson;
    private  String  invitedPerson;
    private  String  partnerName;
    private  String  invitationSuccessDate;
    private  String  status;

}

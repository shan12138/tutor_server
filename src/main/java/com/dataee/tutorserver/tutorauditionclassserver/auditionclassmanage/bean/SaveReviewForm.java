package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.bean;

import com.dataee.tutorserver.entity.AccompanyRegisterForm;
import com.dataee.tutorserver.entity.HeadMasterExamine;
import com.dataee.tutorserver.entity.RegisterForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveReviewForm {
    private HeadMasterExamine headMasterExamine;
    private RegisterForm registerForm;
    private AccompanyRegisterForm accompanyRegisterForm;
}

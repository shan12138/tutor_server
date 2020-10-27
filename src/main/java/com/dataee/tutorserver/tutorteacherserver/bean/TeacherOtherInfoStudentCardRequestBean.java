package com.dataee.tutorserver.tutorteacherserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 上传学生证信息
 *
 * @author JinYue
 * @CreateDate 2019/5/10 19:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherOtherInfoStudentCardRequestBean {
    private String studentNumber;
    @NotBlank(message = "学生证照片")
    private String studentCardPicture;
    @NotBlank(message = "银行卡号")
    private String aliPayAccount;
    @NotBlank(message = "银行卡照片")
    private String aliPayPicture;
    @NotBlank(message = "开户行名称")
    private String openBankName;
}

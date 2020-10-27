package com.dataee.tutorserver.tutorteacherserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JinYue
 * @CreateDate 2019/7/9 16:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountResponseBean {
    private String openBankName;
    private String bankAccount;
    private String banckCardPicture;
}

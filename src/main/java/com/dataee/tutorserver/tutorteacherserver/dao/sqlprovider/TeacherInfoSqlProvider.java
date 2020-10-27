package com.dataee.tutorserver.tutorteacherserver.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author JinYue
 * @CreateDate 2019/5/12 0:13
 */
public class TeacherInfoSqlProvider {

    public String updateStudentCardByPersonId(@Param("studentNumber") final String studentNumber,
                                              @Param("studentCardPicture") final String studentCardPicture) {
        return new SQL() {{
            UPDATE("teacher");
            if (studentNumber != null) {
                SET("job_number=#{studentNumber}");
            }
            if (studentCardPicture != null) {
                SET("student_id_card_picture=#{studentCardPicture}");
            }
            WHERE("teacher_id = #{personId}");
            WHERE("state != 0");
        }}.toString();
    }

    /**
     * 添加银行卡的信息
     *
     * @param aliPayAccount
     * @param aliPayPicture
     * @param openBankName
     * @return
     */
    public String updateAliPayByPersonId(@Param("aliPayAccount") final String aliPayAccount,
                                         @Param("aliPayPicture") final String aliPayPicture,
                                         @Param("openBankName") final String openBankName) {
        return new SQL() {{
            UPDATE("teacher");
            if (aliPayAccount != null) {
                SET("alipay_account=#{aliPayAccount}");
            }
            if (aliPayPicture != null) {
                SET("alipay_picture=#{aliPayPicture}");
            }
            if (openBankName != null) {
                SET("open_bank_name = #{openBankName}");
            }
            WHERE("teacher_id = #{personId}");
            WHERE("state != 0");
        }}.toString();
    }
}

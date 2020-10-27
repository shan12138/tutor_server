package com.dataee.tutorserver.tutorteacherserver.dao;

import com.dataee.tutorserver.entity.Question;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.entity.TeacherLabel;
import com.dataee.tutorserver.tutorteacherserver.bean.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherCenterMapper {
    @Select("select content, answer, optionOne, optionTwo, optionThree, optionFour " +
            "from question where grade = #{grade} and ctype = #{subject} and state = 1 order by rand() limit 10")
    List<Question> getPaper(@Param("grade") String grade, @Param("subject") String subject);

    @Update("update teacher set teacher_name = #{teacherName}, sex = #{sex}, birthday = #{birthday}, native_place = #{nativePlace}, " +
            "qq = #{qq}, emergency_contact = #{emergencyContact}, emergency_telephone = #{emergencyTelephone}, " +
            "address = #{address}, aliPay_account = #{aliPayAccount}, college = #{college}, major = #{major}, " +
            "job_number = #{jobNumber}, tutor_experience = #{tutorExperience}, " +
            "evaluation = #{evaluation}, honour = #{honour}, current_status = #{currentStatus} where teacher_id = #{teacherId}")
    int changeTeaInfo(TeacherDetailInfoRequestBean teacherDetailInfoRequestBean);

    @Select("select *from teacher where teacher_id = #{teacherId} and state != 0")
    Teacher getTeacherInfo(int teacherId);

    @Select("select one_sum + two_sum + three_sum + four_sum + five_sum + six_sum + seven_sum + eight_sum + nine_sum + ten_sum " +
            "as sum_number, teacher.teacher_id, teacher_name, sex, current_status, college, major, tutor_experience, honour, evaluation, remark " +
            " from teacher_label, teacher where teacher.teacher_id = teacher_label.teacher_id order by sum_number desc")
    @Results({
            @Result(column = "teacher_id", property = "teacherLabel", one = @One(select = "getTeacherLabel"))
    })
    List<Teacher> queryTeacher();

    @Select("select head_picture from teacher where teacher_id = #{person_id} and state != 0")
    String getHeadPicture(Integer personId);

    @Update("update teacher set state = 2 where teacher_id = #{teacherId}")
    int updateTeacherStateById(Integer teacherId);

    @Insert("insert into score(teacher_id, grade, subject, score) values(#{teacherId}, #{grade}, #{subject}, #{score})")
    void saveCourseScore(ScoreBean score);

    @Insert("insert into teaching_area(teacher_id, grade, subject) values(#{teacherId}, #{grade}, #{subject})")
    void saveTeachingArea(ScoreBean score);

    @Select("select id from teaching_area where subject = #{subject} and grade = #{grade} and teacher_id = #{teacherId}")
    Integer getTeachingAreaId(ScoreBean score);

    @Select("select *from score where teacher_id = #{teacherId} and state = 1")
    List<ScoreBean> getScoreHistory(Integer teacherId);

    @Update("update teacher set teacher_name = #{teacherName}, sex = #{sex}, birthday = #{birthday}, " +
            "native_place = #{nativePlace}, qq = #{qq}, emergency_contact = #{emergencyContact}, " +
            "emergency_telephone = #{emergencyTelephone},invite_code= #{ownInviteCode},address = #{address} where teacher_id = #{teacherId} and state != 0")
    void writeFirstInformation(TeacherBasicInfoRequestBean teacherBasicInfoRequestBean);

    @Update("update teacher set college = #{college}, major = #{major}, job_number = #{jobNumber}, current_status = #{currentStatus} " +
            "where teacher_id = #{teacherId} and state != 0")
    void writeSecondInformation(TeacherBasicInfoRequestBean teacherBasicInfoRequestBean);

    @Select("select teacher_id, teacher_name, sex, birthday, native_place, qq, emergency_contact, emergency_telephone" +
            ", address from teacher where teacher_id = #{teacherId} and state != 0")
    Teacher getFirstInformation(Integer teacherId);

    @Select("select teacher_id, college, major, job_number, current_status from teacher where teacher_id = #{teacherId} and state != 0")
    Teacher getSecondInformation(Integer teacherId);

    @Select("select teacher_id, tutor_experience, honour, evaluation from teacher where teacher_id = #{teacherId} and state != 0")
    Teacher getThirdInformation(Integer teacherId);

    @Select("select one_sum/total_time as one, two_sum/total_time as two, three_sum/total_time as three, " +
            "four_sum/total_time as four, five_sum/total_time as five, six_sum/total_time as six, " +
            "seven_sum/total_time as seven, eight_sum/total_time as eight, nine_sum/total_time as nine, " +
            "ten_sum/total_time as ten from teacher_label where teacher_id = #{teacherId} ")
    TeacherLabel getTeacherLabel(Integer teacherId);

    /**
     * 获取教师的银行账户信息
     *
     * @param teacherId
     * @return
     */
    @Select("select alipay_account, open_bank_name,aliPay_picture from teacher where state != 0 and teacher_id = #{teacherId}")
    @Results(id = "queryAccount", value = {
            @Result(column = "alipay_account", property = "bankAccount"),
            @Result(column = "open_bank_name", property = "openBankName"),
            @Result(column = "aliPay_picture", property = "banckCardPicture")
    })
    BankAccountResponseBean queryBankAccount(@Param("teacherId") Integer teacherId);


    /**
     * 获取教师在校的学生卡图片
     *
     * @param studentId
     * @return
     */
    @Select("select student_id_card_picture from teacher where state != 0 and teacher_id = #{teacherId}")
    String queryStudentCard(@Param("teacherId") Integer studentId);
}

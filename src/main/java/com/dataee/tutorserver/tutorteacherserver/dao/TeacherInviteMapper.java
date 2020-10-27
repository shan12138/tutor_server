package com.dataee.tutorserver.tutorteacherserver.dao;

import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherGiftBean;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInvite;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInviteRegister;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherIsRegister;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherInviteMapper {
    @Select("select * from teacher where teacher_id = #{teacherId}")
    @Results({
            @Result(column = "partner_id", property = "partner",one = @One(select = "com.dataee.tutorserver.tutorminiprogressserver.dao.InvitedTeacherMapper.getPartnerByPartnerId"))
    })
    Teacher getTeacherByTeacherId(Integer teacherId);

    @Select("select teacher_id from teacher where telephone = #{telephone} ")
    Integer getTeacherId(String telephone);

    @Insert("insert into  teacher_invitation (invited_teacher_id,teacher_name,telephone,teacher_invitation_code,partner_code,teacher_id,partner_id,status) values(#{invitedTeacher.teacherId},#{teacherName},#{telephone}, #{teacherInvitationCode},#{partnerCode},#{teacher.teacherId},#{partner.partnerId},#{status})  ")
    void  insertTeacherInvite(TeacherInvitation teacherInvitation);

    @Update("update  teacher_invitation SET  `status`=#{status}  WHERE  telephone = #{telephone}")
    void  updateTeacherInviteState(@Param("telephone") String telephone,@Param("status") String status);

    @Update("update  teacher_invitation set  invited_teacher_id=#{teacherId}  WHERE  telephone =#{telephone}")
    void  updateTeacherInvitedPerson(@Param("teacherId") Integer teacherId,@Param("telephone") String telephone);

    @Select("select telephone from teacher where teacher_id =#{teacherId} ")
    String getTelephoneById(Integer teacherId);

    @Select("select * from teacher where telephone =#{telephone}")
    Teacher  getFormTeacherById(String telephone);

    @Select("select count(1) from teacher_invitation where telephone =#{telephone}")
    int  getTeacherById(String telephone);

    @Select("select invited_teacher_id,teacher_id,partner_id from teacher_invitation where telephone =#{telephone}")
    TeacherIsRegister getInvitedTeacherId(String telephone);

    @Select("select partner_id from teacher_invitation where telephone =#{telephone}")
    Integer getPartnerId(String telephone);

    @Select("select teacher_id,invited_teacher_id,teacher_level,partner_id from teacher  where teacher_id =#{teacherId} ")
    TeacherInviteRegister getInviteRegisterInfo(Integer teacherId);

    @Select("select teacher_id , invited_teacher_id,teacher_level,partner_id from teacher  where teacher_id =#{teacherId} ")
    Teacher getTeacher(Integer teacherId);

    @Select("select teacher_name,telephone,STATUS from teacher_invitation where  teacher_id = #{teacherId}")
    List<TeacherInvite> getTeachersById(Integer teacherId);

    @Select("select count(1) from teacher_invitation where  teacher_id = #{teacherId}")
    Integer getInviteTeacherCountById(Integer teacherId);

    @Select("select count(1) from teacher_invitation where  teacher_id = #{teacherId}  and status='已任教'")
    Integer getInviteSuccessTeacherCountById(Integer teacherId);

    @Insert("insert into  invitation_teacher_gift (invitation_success_date,invitation_teacher_id,teacher_id,status) values (#{invitationSuccessDate},#{invitationTeacherId},#{teacherId},'待发放')")
    void insertInvitedGift(TeacherGiftBean teacherGiftBean);

    @Update("update teacher set invited_teacher_id = #{invitedTeacherId},teacher_level = #{teacherLevel},partner_id = #{partnerId} where telephone = #{telephone} and state != 0")
    void editTeacherInviteInfo(@Param("invitedTeacherId")Integer invitedTeacherId,@Param("teacherLevel")Integer teacherLevel,@Param("partnerId")Integer partnerId,@Param("telephone")String telephone);

    @Select("select * from course WHERE teacher_id = #{teacherId}")
    List<Course> getCourseList(Integer teacherId);

    @Select("select status from teacher_invitation where telephone =#{telephone}")
    String getTeacherInvitationStatus(String telephone);
}

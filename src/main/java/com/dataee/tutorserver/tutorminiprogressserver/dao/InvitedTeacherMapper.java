package com.dataee.tutorserver.tutorminiprogressserver.dao;

import com.dataee.tutorserver.entity.Lesson;
import com.dataee.tutorserver.entity.Partner;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherInvite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InvitedTeacherMapper {
    @Select("select teacher_name,telephone,STATUS from teacher_invitation where  partner_id = #{partnerId}")
    List<TeacherInvite> getTeachersById(Integer partnerId);

    @Select("select * from  partner where partner_id = #{partnerId}")
    Partner getPartnerByPartnerId(Integer partnerId);

    @Select("select count(1) from teacher_invitation where  partner_id=#{partnerId}")
    Integer getCountByPartnerId(Integer partnerId);

    @Select("select * from  teacher where partner_id=#{partnerId} ")
    List<Teacher> getTeachersByPartnerId(Integer partnerId);

    @Select("select * from lesson  where teacher_id =#{teacherId}")
    List<Lesson> getLessonsByTeacherId(Integer teacherId);

    @Select("select amount from teacher_level where level =#{level}")
    Integer getTeacherLevelByLevel(Integer level);

    @Select("SELECT * FROM  partner  WHERE we_chat_user_id=#{id} AND state=\"启用\"")
    Integer getPartnerId(Integer id);

    @Select("select max(level) from teacher_level ")
    Integer getMaxTeacherLevel();

}

package com.dataee.tutorserver.tutorpatriarchserver.dao;

import com.dataee.tutorserver.entity.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/6 22:13
 */
@Mapper
@Repository
public interface ParentTeacherMapper {
    @Select("select teacher_id, teacher_name, sex, college, major, tutor_experience, honour, evaluation, remark, " +
            "current_status from teacher where teacher_id = #{teacherId} and state != 0")
    @Results({
            @Result(property = "teachingArea", column = "teacher_id",
                    many = @Many(select = "getTeachingArea")),
            @Result(property = "teacherLabel", column = "teacher_id",
                    many = @Many(select = "com.dataee.tutorserver.tutorteacherserver.dao.TeacherCenterMapper." +
                            "getTeacherLabel"))
    })
    Teacher getTeacherDetailInfo(String teacherId);

    @Select("select concat(grade,  '-', subject) as teacherArea from teaching_area where teacher_id = #{teacherId} and state = 1")
    List<String> getTeachingArea(@Param("teacherId") String teacherId);
}

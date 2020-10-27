package com.dataee.tutorserver.scheduletask.dao;

import com.dataee.tutorserver.entity.MessageInformation;
import com.dataee.tutorserver.scheduletask.bean.LessonBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/29 16:16
 */
@Mapper
@Repository
public interface ScheduleTaskMapper {
    @Select("select course_time, lesson_id, teacher_id, lesson.course_id from lesson, resource_pdf where resource_pdf.lesson_id = " +
            " lesson.id and is_read = 0")
    List<LessonBean> getAllNotReadResourceLessons();

    @Insert("insert into message_information(lesson_id, person_id, person_role, content) " +
            "values(#{lessonId}, #{personId}, #{personRole}, #{content})")
    void addToLessonMessageTable(MessageInformation messageInformation);

    @Select("select is_read from message_information where lesson_id = #{lessonId} and state = 1")
    Integer getIsRead(Integer lessonId);
}

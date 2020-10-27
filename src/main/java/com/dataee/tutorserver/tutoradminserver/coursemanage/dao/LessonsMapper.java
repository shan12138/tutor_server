package com.dataee.tutorserver.tutoradminserver.coursemanage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/27 22:33
 */
@Repository
@Mapper
public interface LessonsMapper {
    @Select("select id from week_lessons where year = #{year} and teacher_id = #{teacherId} " +
            "and course_id = #{courseId} and week > #{week} and state = 1")
    List<Integer> queryWeekLessonIdListAfterWeek(@Param("week") Integer week, @Param("courseId") Integer courseId,
                                                 @Param("teacherId") Integer teacherId, @Param("year") Integer year);

    @Select("select id from week_lessons where year = #{year} and teacher_id = #{teacherId} " +
            "and course_id = #{courseId} and week = #{week} and state = 1")
    Integer queryWeekLessonId(@Param("week") Integer week, @Param("courseId") Integer courseId,
                              @Param("teacherId") Integer teacherId, @Param("year") Integer year);

    @Update("update week_lessons set state = 0 where id = #{weekLessonId}")
    Integer deleteWeekLesson(Integer weekLessonId);

    @Update("update lesson set state = 0 where state = 1 and week_lessons_id = #{weekLessonId} and day > #{day}")
    Integer deleteLessonsAfterDay(@Param("weekLessonId") Integer weekLessonId, @Param("day") Integer day);

    @Update("update lesson set state = 0 where state = 1 and week_lessons_id = #{weekLessonId}")
    Integer deleteLessonsByWeekLessonId(Integer weekLessonsId);
}

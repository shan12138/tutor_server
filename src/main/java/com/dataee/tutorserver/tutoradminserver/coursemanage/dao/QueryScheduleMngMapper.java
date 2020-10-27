package com.dataee.tutorserver.tutoradminserver.coursemanage.dao;

import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.CourseLesson;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.Schedule;
import com.dataee.tutorserver.tutoradminserver.coursemanage.dao.sqlprovider.QueryCourseSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/24 22:17
 */
@Mapper
@Repository
public interface QueryScheduleMngMapper {
    @Select("select day, time, 0 as state from lesson where week_lessons_id in " +
            "(select id from week_lessons where teacher_id = #{teacherId} and course_id != #{courseId} and week = #{week})" +
            " and teacher_id = #{teacherId} and state = 1")
    List<Schedule> getOtherLessons(@Param("teacherId") Integer teacherId, @Param("week") Integer week,
                                   @Param("courseId") Integer courseId);

    @Select("select day, time, 1 as state from lesson where week_lessons_id in " +
            "(select id from week_lessons where teacher_id = #{teacherId} and course_id = #{courseId} and week = #{week})" +
            " and course_id = #{courseId} and teacher_id = #{teacherId} and state = 1")
    List<Schedule> getThisLessons(@Param("teacherId") Integer teacherId, @Param("week") Integer week,
                                  @Param("courseId") Integer courseId);

    @Select("select remarks from week_lessons where week = #{week} and course_id = #{courseId} and teacher_id = #{teacherId}" +
            " and state = 1")
    String getRemarks(@Param("week") Integer week,
                      @Param("courseId") Integer courseId,
                      @Param("teacherId") Integer teacherId);


    @SelectProvider(type = QueryCourseSqlProvider.class, method = "getLessonsByCourseId")
    List<CourseLesson> getLessonsByCourseId(@Param("courseId") Integer courseId,@Param("teacherName") String teacherName,@Param("status")String status,
                                            @Param("startTime")String startTime,@Param("endTime")String endTime );
}

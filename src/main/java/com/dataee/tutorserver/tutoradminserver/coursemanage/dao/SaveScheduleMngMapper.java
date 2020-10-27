package com.dataee.tutorserver.tutoradminserver.coursemanage.dao;

import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.LessonBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.SaveLessonRequestBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.SaveLessonsRequestBean;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.SaveRemarksResponseBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;


/**
 * @author JinYue
 * @CreateDate 2019/5/24 17:48
 */
@Repository
@Mapper
public interface SaveScheduleMngMapper {

    /**
     * 删除当前的课程
     *
     * @param id
     * @return
     */
    @Update("update lesson set state=0 where id = #{id} and state = 1")
    Integer deleteLessonsById(Integer id);


    /**
     * 获取课程的id
     *
     * @param weekLessonId
     * @param day
     * @param time
     * @return
     */
    @Select("select id from lesson where week_lessons_id = #{weekLessonId} and day = #{day} and lesson.time = #{times} and state = 1")
    Integer queryLessonId(@Param("weekLessonId") Integer weekLessonId, @Param("day") Integer day, @Param("times") Integer time);


    /**
     * 新增教学周
     *
     * @param weekLesson
     * @return
     */
    @Insert("insert into week_lessons(year,week,teacher_id,course_id) values(#{weekLesson.year}, #{week}, " +
            "#{weekLesson.teacherId}, #{weekLesson.courseId})")
    @Options(useGeneratedKeys = true, keyProperty = "weekLesson.id", keyColumn = "id")
    void addWeekLesson(@Param("weekLesson") SaveLessonsRequestBean weekLesson, @Param("week") Integer week);


    /**
     * 新增教学周
     *
     * @param weekLesson
     * @param week
     */
    @Insert("insert into week_lessons(year,week,teacher_id,course_id) values(#{weekLesson.year}, #{week}, " +
            "#{weekLesson.teacherId}, #{weekLesson.courseId})")
    @Options(useGeneratedKeys = true, keyProperty = "weekLesson.id", keyColumn = "id")
    void addTheWeekLesson(@Param("weekLesson") SaveLessonRequestBean weekLesson, @Param("week") Integer week);


    /**
     * 更新教学备注
     *
     * @param remarks
     * @param id
     * @return
     */
    @Update("update week_lessons set remarks = #{remarks} where id = #{id} and state = 1")
    Integer updateWeekLessonById(@Param("remarks") String remarks, @Param("id") Integer id);

    /**
     * 新增课程信息
     *
     * @param lessons
     * @param weekLessonId
     * @param courseId
     * @param teacherId
     * @return
     */
    @Insert({"<script>",
            "insert into lesson(week_lessons_id, course_id,teacher_id, day, time, course_time, end_course_time,remark_check_in_time,remark_check_out_time) values",
            "<foreach collection='list' item='row' index='index' separator=','>",
            "<if test='row.courseTime != null'>",
            "(#{weekLessonId}, #{courseId},#{teacherId},#{row.day},#{row.time},#{row.courseTime.startTime},#{row.courseTime.endTime},#{row.remarkCheckInTime},#{row.remarkCheckOutTime})",
            "</if>",
            "</foreach>",
            "</script>"})
    Integer addLessons(@Param("list") List<LessonBean> lessons, @Param("weekLessonId") Integer weekLessonId,
                       @Param("courseId") Integer courseId, @Param("teacherId") Integer teacherId
                       );


    /**
     * 查询当前周是否存在
     *
     * @param week
     * @param courseId
     * @param teacherId
     * @param year
     * @return
     */
    @Select("select id from week_lessons where state = 1 and week_lessons.year = #{year} and week_lessons.week = #{week} and teacher_id = #{teacherId} and course_id = #{courseId}")
    Integer queryWeekBYWeek(@Param("week") Integer week, @Param("courseId") Integer courseId,
                            @Param("teacherId") Integer teacherId, @Param("year") Integer year);


    /**
     * 重写新增weekLesson
     *
     * @param remark
     * @param week
     * @return
     */
    @Insert("insert into week_lessons(year, week, teacher_id, course_id, remarks) values (#{remark.year},#{week},#{remark.teacherId},#{remark.courseId},#{remark.remarks})")
    Integer addWeekLessonByRemark(@Param("remark") SaveRemarksResponseBean remark, @Param("week") Integer week);


    /**
     * 获取指定的课的课次
     *
     * @param lessonId
     * @return
     */
    @Select("select lesson_number from lesson where state = 1 and id = #{lessonId}")
    Integer queryLessonNumberByLessonId(@Param("lessonId") Integer lessonId);

    /**
     * 将课次减一
     *
     * @param courseId
     * @param lessonNumber
     * @return
     */
    @Update("update lesson set lesson_number = lesson_number - 1 where state = 1 and course_id = #{courseId} and lesson_number > #{lessonNumber}")
    Integer minusLessonNumber(@Param("courseId") Integer courseId, @Param("lessonNumber") Integer lessonNumber);


    /**
     * 获取最大的课次
     *
     * @param courseId
     * @return
     */
    @Select("select max(lesson_number) as maxNumber from lesson where state = 1 and course_id = #{courseId} and course_time < #{endCourseTime} and course_time != #{courseTime}")
    Integer queryMaxLessonNumber(@Param("courseId") Integer courseId, @Param("courseTime") String courseTime, @Param("endCourseTime") String endCourseTime);


    /**
     * 增加一堂新的课
     *
     * @param lesson
     * @param weekLessonId
     * @param courseId
     * @param teacherId
     * @param lessonNumber
     * @return
     */
    @Insert({"insert into lesson(week_lessons_id, course_id,teacher_id, day, time, course_time, end_course_time, lesson_number,remark_check_in_time,remark_check_out_time) values",
            "(#{weekLessonId}, #{courseId},#{teacherId},#{lesson.day},#{lesson.time},#{lesson.courseTime.startTime},#{lesson.courseTime.endTime}, #{lessonNumber},#{lesson.remarkCheckInTime},#{lesson.remarkCheckOutTime})"})
    @Options(useGeneratedKeys = true, keyProperty = "lesson.id", keyColumn = "id")
    Integer insertLesson(@Param("lesson") LessonBean lesson, @Param("weekLessonId") Integer weekLessonId,
                         @Param("courseId") Integer courseId, @Param("teacherId") Integer teacherId, @Param("lessonNumber") Integer lessonNumber);


    /**
     * 当前课程之后的课程加1
     *
     * @param courseId
     * @param lessonNumber
     * @return
     */
    @Update("update lesson set lesson_number = lesson_number + 1 where state = 1 and lesson_number >= #{lessonNumber} and course_id = #{courseId} and id != #{lessonId}")
    int plusLessonNumber(@Param("courseId") Integer courseId, @Param("lessonNumber") Integer lessonNumber, @Param("lessonId") Integer lessonId);

}

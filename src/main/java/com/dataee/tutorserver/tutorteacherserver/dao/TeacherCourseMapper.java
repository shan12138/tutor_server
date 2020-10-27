package com.dataee.tutorserver.tutorteacherserver.dao;

import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import com.dataee.tutorserver.tutorteacherserver.bean.ScheduleBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/15 19:18
 */
@Mapper
@Repository
public interface TeacherCourseMapper {
    /**
     * 获取课程的签到列表信息
     *
     * @param courseId
     * @param teacherId
     * @return
     */
    @Select("select address_id, course_id, teacher_id from course where state = 2 and course_id = #{courseId}" +
            " and teacher_id = #{teacherId}")
    @Results(value = {
            @Result(property = "address", column = "address_id",
                    one = @One(select = "com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCourseMapper." +
                            "getAddressOfCourse"))
    })
    Course getAttendanceRecord(@Param("courseId") Integer courseId,
                               @Param("teacherId") Integer teacherId);

    @Select("select id, course_time, end_course_time, time, lesson_number, " +
            "check_in_time, check_out_time, is_check_in as checkIn,remark_check_in_time,remark_check_out_time, " +
            "teacher_name, teacher_record_confirmed, parent_record_confirmed, " +
            "class_time from lesson, teacher where teacher.teacher_id = " +
            "lesson.teacher_id and course_id = #{courseId} and lesson.state = 1 and teacher.state = 4" +
            " and lesson.teacher_id = #{teacherId} order by lesson_number desc")
    List<Lesson> getAttendanceRecordPageInfo(@Param("courseId") Integer courseId,
                                             @Param("teacherId") Integer teacherId);

    @Select("select * from (select * from lesson where date(${column}) = curdate() and state = 1 and " +
            "course_id = #{courseId} and teacher_id = #{teacherId}" +
            ") as temp order by abs(TIMESTAMPDIFF(MINUTE,course_time,now())) asc limit 0,1")
    @Results(value = {
            @Result(property = "checkIn", column = "is_check_in")
    })
    Lesson getLesson(@Param("courseId") Integer courseId, @Param("teacherId") Integer teacherId, @Param("column") String column);

    /**
     * 修改签到时间
     *
     * @param id
     * @param checkInTime
     * @return
     */
    @Update("update lesson set is_check_in = 1, check_in_time = #{checkInTime} " +
            "where id = #{id}")
    int editCheckInTimeInfo(@Param("id") Integer id, @Param("checkInTime") String checkInTime);

    /**
     * 修改课程的上课次数
     *
     * @param id
     * @return
     */
    @Update("update course set class_times = class_times + 1 where id = #{id}")
    int editCourseInfo(Integer id);

    /**
     * 修改课程的课时信息
     *
     * @param id
     * @param classTime
     * @return
     */
    @Update("update course_hour_record set consume_class_hour = consume_class_hour + #{classTime}, " +
            "rest_class_hour = rest_class_hour - #{classTime} where id = #{id} and state = 1")
    int editCourseHourRecord(@Param("id") Integer id, @Param("classTime") Double classTime);

    /**
     * 修改课程的签退信息
     *
     * @param lessonId
     * @return
     */
    @Update("update lesson set check_out_time = #{checkOutTime} , is_check_in=2 where id = #{lessonId}")
    int editCheckOutTimeInfo(@Param("lessonId") Integer lessonId, @Param("checkOutTime") String checkOutTime);

    /**
     * 改变上课时长
     *
     * @param lessonId
     * @param classTime
     * @return
     */
    @Update("update lesson set class_time = #{classTime} where id = #{lessonId}")
    int editClassTime(@Param("lessonId") Integer lessonId, @Param("classTime") double classTime);

    /**
     * 得到一节课的签到信息（计算上课时长）
     *
     * @param lessonId
     * @return
     */
    @Select("select check_in_time from lesson where id = #{lessonId}")
    String getCheckInTime(Integer lessonId);

    /**
     * 获得所要修改课程上课次数的记录的ID
     *
     * @param courseId
     * @param teacherId
     * @return
     */
    @Select("select id from course where course_id = #{courseId} and teacher_id = #{teacherId} and state = 2")
    int getCourseId(@Param("courseId") Integer courseId, @Param("teacherId") Integer teacherId);

    /**
     * 选取最近的一堂尚未签退的课的id进行签退
     *
     * @param courseId
     * @param teacherId
     * @return
     */
    @Select("select max(id) from lesson where course_id = #{courseId} and teacher_id = #{teacherId} and state = 1 and " +
            "check_out_time is null and check_in_time is not null")
    int getLessonId(@Param("courseId") Integer courseId, @Param("teacherId") Integer teacherId);

    /**
     * 原先一个课程就有一个课时记录，现在一个课程有多个课时记录
     * @param courseId
     * @return
     */
    @Select("select * from course_hour_record where course_id = #{courseId} and state = 1 AND consume_class_hour < total_class_hour ORDER  BY  createdAt   asc")
    List<CourseHourRecord> getCourseHourRecord(Integer courseId);

    @Select("select * from course_hour_record where course_id = #{courseId} AND state =1 ORDER  BY  createdAt  LIMIT 0,1")
    CourseHourRecord getOneCourseHourRecord(Integer courseId);

    @Select("select course.course_id, course_name, address_id, temp2.day,temp2.remark_check_in_time,temp2.lessonId,temp2.remark_check_out_time, temp2.time from course inner join " +
            "(select day,lesson.id AS lessonId, time,remark_check_in_time,remark_check_out_time, temp1.* from lesson inner join (select id, course_id from week_lessons " +
            "where week = #{week} and teacher_id = #{person_id} and state = 1 and year = #{year}) " +
            "as temp1 on lesson.week_lessons_id = temp1.id and lesson.course_id = " +
            "temp1.course_id and lesson.state = 1) as temp2 " +
            "on course.course_id = temp2.course_id and course.state = 2")
    @Results(value = {
            @Result(property = "address", column = "address_id",
                    one = @One(select = "getAddress"))
    })
    List<ScheduleBean> getSchedule(@Param("person_id") Integer personId, @Param("week") Integer week,
                                   @Param("year") String year);

    @Select("select concat(region, address_detail) as address from address_course where address_id = #{address_id} ")
    String getAddress(@Param("address_id") Integer address_id);

    @Select("select question_id, question, answer from parent_record, parent_record_question where parent_record.question_id = " +
            "parent_record_question.id and lesson_id = #{lessonId}")
    List<Remarks> getParentRecord(Integer lessonId);

    @Select("select parent_id from course where course_id = #{courseId} and state = 2")
    int getParentId(Integer courseId);

    @Select("select partner_id from parent where parent_id = #{parentId}  AND  state=3")
    int getPartnerId(Integer parentId);

    @Select("select ifnull(sum(is_read),0) from resource_pdf, lesson where resource_pdf.lesson_id = lesson.id and lesson.id = #{id} and resource_pdf.state=1")
    int getIsRead(Integer id);

    @Select("select count(*) from resource_pdf where lesson_id = #{id}")
    Integer getResourceNum(Integer id);

    @Select("select  * from   lesson WHERE id=#{id}")
    @Results(value = @Result(column = "is_check_in",property = "checkIn"))
    Lesson getOneLesson(Integer id);


    @Select("SELECT t.teacher_name,l.*,resource_pdf.resource_id, resource_pdf.upload_state, resource_pdf.state pdf_state FROM   lesson l\n" +
            "INNER JOIN teacher t ON t.teacher_id=l.teacher_id\n" +
            "left JOIN  resource_pdf ON  resource_pdf.lesson_id=l.id\n" +
            "WHERE l.id=#{id} AND l.state = 1 AND (resource_pdf.state = 1 OR resource_pdf.state IS NULL )")
    @Results(value = @Result(column = "is_check_in",property = "checkIn"))
    Lesson getLessonById(Integer id);

    @Insert("insert into bill (time,flow_type,kind,number,source,source_id,target,target_id,course_id,message) values(#{time},#{flowType},#{kind},#{number},#{source},#{sourceId},#{target},#{targetId},#{courseId},#{message})")
    void  insertParentBill(Bill bill);

    @Select("select  * from bill where course_id =#{courseId}")
    List<Bill> getBills(Integer courseId);

    @Select("select parent_level from parent where parent_id =#{parentId}")
    Integer getParentLevelId(Integer parentId);

    @Select("select product_id from course where course_id =#{courseId}")
    Integer getProduct(Integer courseId);

    @Select("select * from parent_level where product_id=#{productId} and level =#{level}")
    ParentLevel getParentLevel(@Param("productId") Integer productId,@Param("level") Integer level);

    @Select("select teacher_level FROM teacher WHERE teacher_id =#{teacherId}")
    Integer getTeacherLevel(Integer teacherId);

    @Select("select teacher_name from teacher where teacher_id =#{teacherId} ")
    String  getTeacherName(Integer teacherId);

    @Select("select partner_id from teacher where teacher_id =#{teacherId}")
    Integer getPartnerIdByTeacherId(Integer teacherId);

    @Select("SELECT  MAX(level) FROM   parent_level  WHERE   product_id=#{productId}")
    Integer getMaxParentLevel(Integer productId);

    @Select("select MAX(level) from teacher_level ")
    Integer getMaxTeacherLevel();

    @Select("select parent_name from parent where parent_id =#{parentId}")
    String getParentName(Integer parentId);






}

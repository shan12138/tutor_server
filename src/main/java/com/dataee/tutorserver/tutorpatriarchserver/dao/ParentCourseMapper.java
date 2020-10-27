package com.dataee.tutorserver.tutorpatriarchserver.dao;

import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorteacherserver.bean.ScheduleBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/6 15:42
 */
@Mapper
@Repository
public interface ParentCourseMapper {
    /**
     * 获得课程信息的详情，进行course_hour_record的一对一查询
     * lesson的一对多查询
     * address的一对一查询
     *
     * @return
     */
    @Select("select course_name, address_id, course_id, parent_id, student_id, teacher_id" +
            " from course where state != 0 and course_id = #{courseId}")
    @Results(id = "courseMap", value = {
            @Result(property = "courseHourRecord", column = "course_id",
                    many = @Many(select = "getCourseHourRecordOfCourse")),
            @Result(property = "address", column = "address_id",
                    one = @One(select = "getAddressOfCourse")),
            @Result(property = "student", column = "student_id",
                    one = @One(select = "com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCenterMapper." +
                            "getChildDetailInfo")),
            @Result(property = "teacher", column = "teacher_id",
                    one = @One(select = "getTeacherName")),
            @Result(property = "parent", column = "parent_id",
                    one = @One(select = "com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCenterMapper." +
                            "getParentInfo")),
    })
    Course getCourseDetailInfo(@Param("courseId") String courseId);
    @Select("select * from course_hour_record where course_id = #{courseId} and state = 1")
    List<CourseHourRecord> getCourseHourRecordOfCourse(@Param("courseId") String courseId);

    @Select("select id, is_check_in AS checkIn,remark_check_in_time,remark_check_out_time,check_in_time, check_out_time, course_time, lesson_number, time, " +
            "teacher_record_confirmed, parent_record_confirmed," +
            " teacher_name, class_time,remark_check_in_time,remark_check_out_time from lesson, teacher where teacher.teacher_id = " +
            "lesson.teacher_id and course_id = #{courseId} and lesson.state = 1 and teacher.state = 4 and check_in_time" +
            " is not null and check_out_time is not null order by lesson_number desc")
    List<Lesson> getLessonDetailInfo(@Param("courseId") String courseId);

    /*@Select("select address_id, region, address_detail from address where address_id = #{addressId} and state = 1")*/
   @Select("select * from address_course where address_id = #{addressId} ")
    CourseAddress getAddressOfCourse(@Param("addressId") String addressId);

    @Select("select teacher_name from teacher where teacher_id = #{teacherId}")
    Teacher getTeacherName(@Param("teacherId") String teacherId);

    @Select("select course.course_id,course_name, address_id, temp2.day,temp2.remark_check_in_time,temp2.remark_check_out_time,temp2.lessonId,temp2.time from course inner join " +
            "(select day, time,remark_check_in_time,remark_check_out_time,lesson.id AS lessonId, temp1.* from lesson inner join (select week_lessons.id, week_lessons.course_id " +
            "from week_lessons, course where week_lessons.teacher_id =" +
            " course.teacher_id and week_lessons.course_id = course.course_id and year = #{year} and " +
            "course.state = 2 and week_lessons.state = 1 and parent_id = #{person_id} and week = #{week}) " +
            "as temp1 on lesson.week_lessons_id = temp1.id and lesson.course_id = temp1.course_id and lesson.state = 1) as temp2 " +
            "on course.course_id = temp2.course_id and course.state = 2")
    @Results(value = {
            @Result(property = "address", column = "address_id",
                    one = @One(select = "getAddress"))
    })
    List<ScheduleBean> getSchedule(@Param("person_id") Integer personId, @Param("week") Integer week,
                                   @Param("year") String year);

    @Select("select concat(region, address_detail) as address_course from address where address_id = #{id} ")
    String getAddress(@Param("id") Integer id);

    @Update("update course set address_id = #{addressId} where course_id = #{courseId} and state != 0")
    void changeCourseAddress(@Param("addressId") Integer addressId, @Param("courseId") Integer courseId);

    @Select("SELECT t.teacher_name,l.*,resource_pdf.resource_id, resource_pdf.upload_state, resource_pdf.state pdf_state FROM   lesson l\n" +
            "INNER JOIN teacher t ON t.teacher_id=l.teacher_id\n" +
            "left JOIN  resource_pdf ON  resource_pdf.lesson_id=l.id\n" +
            "WHERE l.id=#{id} AND l.state = 1 AND (resource_pdf.state = 1 OR resource_pdf.state IS NULL )")
    @Results(value = @Result(column = "is_check_in",property = "checkIn"))
    Lesson getLessonById(Integer id);

    @Select("select count(*) from resource_pdf where lesson_id = #{id}")
    Integer getResourceNum(Integer id);

    @Select("select * from lesson where state =1 and  course_id =#{courseId}")
    List<Lesson>  getLessonsByCourseId(Integer courseId);

}

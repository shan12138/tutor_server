package com.dataee.tutorserver.tutorteacherserver.dao;

import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.tutorteacherserver.bean.CourseInfo;
import com.dataee.tutorserver.tutorteacherserver.bean.MyStudentInfoResponseBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/16 12:04
 */
@Repository
@Mapper
public interface MyStudentsMapper {

    /**
     * 获取去教师的学生列表
     *
     * @param teacherId
     * @return
     */
    @Select("select distinct s.student_id, student_name from student as s, course as c where s.state = 1 and " +
            "s.student_id = c.student_id and c.teacher_id = #{teacherId} and c.state = 2")
    List<Student> queryStudentsByTeacherId(Integer teacherId);

    /**
     * 获取教师的指定学生的详细信息
     *
     * @param studentId
     * @return
     */
    @Select("select s.student_id, student_name, sex, s.grade from student as s where s.student_id = #{studentId} and s.state = 1")
    MyStudentInfoResponseBean queryStudentByStudentId(@Param("studentId") Integer studentId);

    /**
     * 获取学生的课程信息
     *
     * @param teacherId
     * @param studentId
     * @return
     */
/*    @Select("select c.course_name, concat(region, address_detail) address_info from address " +
            "inner join " +
            "(select course.address_id, course_name from course where course.state = 2 and teacher_id = #{teacherId} and student_id = #{studentId}) as c " +
            "on address.address_id = c.address_id where address.state = 1")
    @Results({
            @Result(column = "address_info", property = "address")
    })*/
    @Select("select c.course_name, concat(region, address_detail) address_info from address_course " +
            "inner join " +
            "(select course.address_id, course_name from course where course.state = 2 and teacher_id = #{teacherId} and student_id = #{studentId}) as c " +
            "on address_course.address_id = c.address_id ")
    @Results({
            @Result(column = "address_info", property = "address")
    })
    List<CourseInfo> queryCourseInfo(@Param("teacherId") Integer teacherId, @Param("studentId") Integer studentId);

}

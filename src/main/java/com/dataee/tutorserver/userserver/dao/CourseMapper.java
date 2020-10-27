package com.dataee.tutorserver.userserver.dao;

import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.userserver.bean.CourseResponseBean;
import com.dataee.tutorserver.userserver.bean.GetTodayCourseResponseBean;
import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;
import com.dataee.tutorserver.userserver.bean.LessonNumberResponseBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/4/27 11:29
 */
@Mapper
@Repository
public interface CourseMapper {
    @Select("select id, name from grade where state = 1")
    List<Grade> getGrade();

    @Select("select id, name from subject where state = 1")
    List<Subject> getSubject();

    @Select("select course.id,course_id, student_name, course_name, teacher_name, teacher.teacher_id  from course, teacher," +
            "student where course.student_id = student.student_id and student.state = 1 and " +
            "course.teacher_id = teacher.teacher_id and course.state = 2 and teacher.state = 4 and ${tableId} = #{id}")
    @Results(value = {
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "courseHourRecords", column = "course_id",
                    many = @Many(select = "com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCourseMapper." +
                            "getCourseHourRecordOfCourse")),
            @Result(property = "contractPdf",column = "course_id" ,one = @One(select ="com.dataee.tutorserver.userserver.dao.CourseMapper.getContractPdfByCourseId" ))
    })
    List<GetCourseTeacherListResponseBean> getCourseList(@Param("tableId") String tableId, @Param("id") Integer id);

    @Select("select * from contract_pdf  where course_id = #{courseId} and state = 1")
    @Results(value = {
            @Result(property = "parentContractId", column = "parent_contract_id"),
            @Result(property = "parentContract",column = "parent_contract_id" ,one = @One(select ="com.dataee.tutorserver.userserver.dao.CourseMapper.getParentContract" ))
    })
    ContractPdf getContractPdfByCourseId(Integer courseId);

    @Select("select * from parent_contract where id = #{parentContractId}")
    ParentContract getParentContract(Integer parentContractId);

    @Select("select course.course_id,course_name,course.address_id,course_time,remark_check_in_time ,remark_check_out_time " +
            " from course,teacher,lesson " +
            "  where  teacher.teacher_id = #{userId} and teacher.teacher_id = course.teacher_id " +
            "and lesson.state = 1 and course.state = 2 and teacher.state=4 " +
            "and lesson.course_id = course.course_id and lesson.course_time >= curdate() " +
            "and lesson.course_time <= DATE_SUB(curdate(),INTERVAL -1 DAY) ")
    @Results(id = "TeacherCourseMapper", value = {
            @Result(column = "courseId", property = "course_id"),
            @Result(column = "courseName", property = "course_name"),
            @Result(property = "address", column = "address_id",
                    one = @One(select = "getAddress"))
    })
    List<GetTodayCourseResponseBean> getTeacherTodayCourse(@Param("userId") String userId);

    @Select("select course.course_id,course_name,course.address_id,course_time, course.teacher_id ,remark_check_in_time ,remark_check_out_time" +
            " from course ,parent, lesson  " +
            "where parent.parent_id = #{userId} and parent.parent_id = course.parent_id " +
            "and lesson.state = 1 and course.state = 2 and parent.state=3 " +
            "and lesson.course_id = course.course_id and lesson.course_time >= curdate() " +
            "and lesson.course_time <= DATE_SUB(curdate(),INTERVAL -1 DAY)")
    @Results(id = "ParentCourseMapper", value = {
            @Result(column = "courseId", property = "course_id"),
            @Result(column = "courseName", property = "course_name"),
            @Result(column = "courseTime", property = "course_time"),
            @Result(property = "address", column = "address_id",
                    one = @One(select = "getAddress")),
            @Result(property = "teacher", column = "teacher_id",
                    one = @One(select = "getTeacher"))
    })
    List<GetTodayCourseResponseBean> getParentTodayCourse(@Param("userId") String userId);

    /*@Select("select address_id , region, address_detail,state from address where address_id = #{addressId} and state = 1")*/
    @Select("select * from address_course where address_id = #{addressId}")
    Address getAddress(@Param("addressId") int address_id);

    @Select("select teacher_name from teacher where teacher_id = #{teacherId} and state =4")
    Teacher getTeacher(@Param("teacherId") int teacher_id);

    /**
     * 获取该用户的课程名
     *
     * @param personId
     * @param personRole
     * @return
     */
    @Select("select course_id, course_name from course where state = 2 and ${personRole}_id = #{personId}")
    List<CourseResponseBean> queryCouseNameByPersonIdAndPersonRole(@Param("personId") Integer personId, @Param("personRole") String personRole);

    /**
     * 获取指定课程的已经上课的课次的列表
     *
     * @param courseId
     * @return
     */
   // @Select("select id, lesson_number from lesson where state = 1 and course_id = #{courseId} and check_out_time is not null")
    @Select("select id, lesson_number from lesson where state = 1 and course_id = #{courseId} ")
    @Results(id = "lessonNumberMap", value = {
            @Result(column = "id", property = "lessonId")
    })
    List<LessonNumberResponseBean> queryLessonNumberByCourseId(@Param("courseId") Integer courseId);
}

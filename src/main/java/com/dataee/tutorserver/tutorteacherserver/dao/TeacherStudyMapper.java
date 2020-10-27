package com.dataee.tutorserver.tutorteacherserver.dao;

import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.tutorteacherserver.bean.StudyCenterResponseBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/23 2:51
 */
@Repository
@Mapper
public interface TeacherStudyMapper {
    /**
     * 待解决
     * 获取资源列表
     *
     * @param personId
     * @param classification
     * @return
     */
    @Select("select resource_id, resource_name, pdf_address,  course_name from resource_pdf " +
            "inner join " +
            "(select course_id, course_name from course where state = 2 and course.teacher_id = #{teacherId}) as course_info " +
            " where resource_pdf.state = 1 and resource_pdf.resource_type = #{type} and resource_pdf.course_id = course_info.course_id")
    List<StudyCenterResponseBean> queryStudyResourcesByPersonId(@Param("teacherId") Integer personId, @Param("type") String classification);

    /**
     * 获取指定资源
     *
     * @param studyResourceId
     * @return
     */
    @Select("select image_address from resource_image where state = 1 and resource_id = #{id}")
    List<String> queryStudyResourceById(@Param("id") Integer studyResourceId);

    /**
     * 获取学生信息
     *
     * @param studentId
     * @return
     */
    @Select("select student_id,student_name,sex, grade from student where student_id = #{student_id} and state = 1")
    Student findStudentById(@Param("student_id") Integer studentId);


}

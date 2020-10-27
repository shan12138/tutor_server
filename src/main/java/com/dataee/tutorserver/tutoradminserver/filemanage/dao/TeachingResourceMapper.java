package com.dataee.tutorserver.tutoradminserver.filemanage.dao;

import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ResourceListRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.dao.sqlprovider.ErrorQuestionsSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/9 23:44
 */
@Repository
@Mapper
public interface TeachingResourceMapper {

    /**
     * 获取课件列表
     *
     * @return
     */
   /* @Select("select resource_id, resource_name, pdf_address, teacher_name, course_name, resource_type, upload_state from resource_pdf\n" +
            "inner join\n" +
            "(select teacher_name, course_name, course_id, course.teacher_id from teacher, course where course.state = 2 and teacher.teacher_id = course.teacher_id and teacher.state != 0) as course_info\n" +
            "on resource_pdf.state = 1 and resource_pdf.person_id = course_info.teacher_id and resource_pdf.course_id = course_info.course_id")*/
    @SelectProvider(type = ErrorQuestionsSqlProvider.class,method = "queryTeacherResources")
    List<ResourceListRequestBean> queryTeachingResources(@Param("studentName")String studentName,@Param("teacher")String teacher,@Param("courseName")String courseName,@Param("type")String type,@Param("headTeacher")String headTeacher,@Param("courseAdmin")String courseAdmin);


    /**
     * 查询教学资料中的文件地址
     *
     * @param id
     * @return
     */
    @Select("select pdf_address from resource_pdf where state = 1 and resource_id = #{id}")
    String selectResourceById(@Param("id") Integer id);

    /**
     * 删除课件根据id
     *
     * @param id
     * @return
     */
    @Update("update resource_pdf set state = 0 where resource_id = #{id}")
    int deleteResourceByResourceId(@Param("id") Integer id);

}

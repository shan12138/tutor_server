package com.dataee.tutorserver.tutorpatriarchserver.dao;

import com.dataee.tutorserver.tutorteacherserver.bean.StudyCenterResponseBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/15 19:45
 */
@Repository
@Mapper
public interface StudyCenterMapper {

    /**
     * 获取到教学资源的列表
     *
     * @param parentId
     * @param keyword
     * @return
     */
    @Select("select resource_id, resource_name,pdf_address, course_name from resource_pdf " +
            "inner join " +
            "(select course_id,course_name, teacher_id from course where state = 2 and parent_id =#{parentId}) as course_info " +
            " where resource_pdf.state = 1 and resource_pdf.resource_type = #{keyword} " +
            " and resource_pdf.person_id = course_info.teacher_id " +
            " and resource_pdf.course_id = course_info.course_id")
    List<StudyCenterResponseBean> getTeachingResourceListByIdAndKeyword(@Param("parentId") Integer parentId, @Param("keyword") String keyword);

    /**
     * 获取去指定的教学资源的图片的列表
     *
     * @param id
     * @return
     */
    @Select("select image_address from resource_image where state = 1 and resource_id = #{id}")
    List<String> queryTeachingResourceImageById(@Param("id") Integer id);

    /**
     * 获取课件的地址
     * @param coursewareId
     * @return
     */
    @Select("SELECT  pdf_address  FROM  resource_pdf WHERE   lesson_id = 0   AND   state = 1  AND   resource_id = #{coursewareId}")
     String getCoursewareAddressName(@Param("coursewareId") int coursewareId);
}

package com.dataee.tutorserver.tutorteacherserver.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 15:28
 */
@Repository
@Mapper
public interface TeacherCoursewareMapper {

    /**
     * 获取课件名
     *
     * @param coursewareId
     * @return
     */
    @Select("select resource_name from resource_pdf where state = 1 and resource_id = #{coursewareId}")
    String getCoursewareName(@Param("coursewareId") int coursewareId);

    /**
     * 获取课件的地址
     * @param coursewareId
     * @return
     */
    @Select("SELECT  pdf_address  FROM  resource_pdf WHERE   lesson_id = 0   AND   state = 1  AND   resource_id = #{coursewareId}")
    String getCoursewareAddressName(@Param("coursewareId") int coursewareId);

    /**
     * 获取课件的Id
     *
     * @param lessonId
     * @return
     */
    @Select("select resource_id from resource_pdf where state = 1 and lesson_id = #{lessonId}")
    Integer getCoursewareId(@Param("lessonId") Integer lessonId);

    /**
     * 获取课件的图片
     *
     * @param coursewareId
     * @return
     */
    @Select("select image_address from resource_image where state = 1 and resource_id = #{coursewareId}")
    List<String> getCoursewareImage(@Param("coursewareId") int coursewareId);


    /**
     * 标记已读
     *
     * @param coursewareId
     * @return
     */
    @Update("update resource_pdf set is_read = 1 where state = 1 and resource_id = #{coursewareId}")
    int updateReadByCoursewareId(@Param("coursewareId") int coursewareId);
}

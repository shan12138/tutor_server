package com.dataee.tutorserver.tutoradminserver.filemanage.dao;

import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ResourceRequestBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 13:42
 */
@Repository
@Mapper
public interface ResourceMapper {
    /**
     * 保存资源的PDF地址
     * 获取主键
     *
     * @param resource
     * @return
     */
    @Insert("insert into resource_pdf(resource_name, pdf_address, person_id,course_id, resource_type) " +
            "value(#{resource.resourceName},#{resource.pdfAddress},#{resource.personId}, " +
            "#{resource.courseId}, #{resource.resourceType})")
    @Options(useGeneratedKeys = true, keyProperty = "resource.resourceId", keyColumn = "resource_id")
    int insertResource(@Param("resource") ResourceRequestBean resource);

    /**
     * 保存课资源的图片信息
     *
     * @param resourceId
     * @param paths
     * @return
     */
    @Insert({"<script>",
            "insert into resource_image(resource_id, image_address) ",
            "values ",
            "<foreach collection='list' item='path' index='index' separator=','>",
            "(#{resourceId}, #{path})",
            "</foreach>",
            "</script>"})
    int insertResourceImage(@Param("resourceId") Integer resourceId, @Param("list") List<String> paths);


    /**
     * 将课件上传的状态修改为上传完成
     *
     * @param resourceId
     * @return
     */
    @Update("update resource_pdf set upload_state = 1 where state = 1 and resource_id = #{resourceId}")
    int updateResourceUploadState(@Param("resourceId") int resourceId);
}

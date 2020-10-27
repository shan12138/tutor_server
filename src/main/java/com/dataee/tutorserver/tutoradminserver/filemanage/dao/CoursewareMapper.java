package com.dataee.tutorserver.tutoradminserver.filemanage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 13:41
 */
@Repository
@Mapper
public interface CoursewareMapper {
    /**
     *
     *更新课件的lessonId
     * @param coursewareId
     * @param lessonId
     * @return
     */
    @Update("update resource_pdf set lesson_id = #{lessonId} where state = 1 and resource_id = #{coursewareId}")
    int updateLessonIdByCoursewareId(@Param("coursewareId") Integer coursewareId, @Param("lessonId") Integer lessonId);

    /**
     * 删除重复的课件
     *
     * @param lessonId
     * @return
     */
    @Update("update resource_pdf set state = 0 where lesson_id = #{lessonId}")
    int deleteCoursewareByLessonId(@Param("lessonId") Integer lessonId);
}

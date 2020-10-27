package com.dataee.tutorserver.tutorteacherserver.dao;

import com.dataee.tutorserver.entity.Leisure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/24 9:09
 */
@Repository
@Mapper
public interface LeisureMapper {
    /**
     * 获取教师的课余时间的列表
     *
     * @param teacherId
     * @return
     */
    @Select("select id, day, morning, noon, evening from leisure where state = 1 and teacher_id = #{teacherId}")
    List<Leisure> queryLeisureByteacherId(Integer teacherId);

    @Select("select teacher_id from course where course_id = #{courseId} and state != 0")
    Integer getTeacherIdOfCourse(Integer courseId);
}

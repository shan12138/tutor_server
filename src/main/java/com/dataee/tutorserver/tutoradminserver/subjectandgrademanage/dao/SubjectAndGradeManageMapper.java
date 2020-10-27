package com.dataee.tutorserver.tutoradminserver.subjectandgrademanage.dao;

import com.dataee.tutorserver.entity.Grade;
import com.dataee.tutorserver.entity.Subject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/7 23:08
 */
@Mapper
@Repository
public interface SubjectAndGradeManageMapper {
    @Select("select * from grade order by priority")
    List<Grade> getGrade();

    @Select("select * from subject order by priority")
    List<Subject> getSubject();

    @Update("update ${table} set priority = #{priority} where id = #{id}")
    int changeData(@Param("table") String table, @Param("id") Integer id, @Param("priority") Integer priority);

    @Insert("insert into ${table} (name, priority) values(#{name}, #{priority})")
    int addData(@Param("table") String table, @Param("name") String name, @Param("priority") Integer priority);

    @Select("select name from ${table} where id = #{id}")
    String getOldNameById(@Param("table") String table, @Param("id") Integer id);

    @Select("select count(*) from course where (subject = #{oldName} or grade = #{oldName}) and state != 0")
    Integer getCourseByObject(String oldName);
}

package com.dataee.tutorserver.tutoradminserver.levelmanage.dao;

import com.dataee.tutorserver.entity.ParentLevel;
import com.dataee.tutorserver.entity.TeacherLevel;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/5 11:22
 */
@Mapper
public interface LevelManageMapper {
    @Select("select * from parent_level where product_id = #{id}")
    List<ParentLevel> getParentLevelList(int id);

    @Select("select max(level) from parent_level where product_id = #{id}")
    Integer getMaxLevel(int id);

    @Insert("insert into parent_level(level_name, level, first_commission_ratio, next_commission_ratio, product_id) " +
            "values(#{levelName}, #{level}, #{firstCommissionRatio}, #{nextCommissionRatio}, #{productId})")
    void createParentLevel(ParentLevel parentLevel);

    @Select("select * from parent_level where id = #{id}")
    ParentLevel getParentLevelById(Integer id);

    @Update("update parent_level set level_name = #{levelName}, first_commission_ratio = #{firstCommissionRatio}, " +
            "next_commission_ratio = #{nextCommissionRatio} where id = #{id}")
    void updateParentLevel(ParentLevel parentLevel);

    @Delete("delete from parent_level where id = #{id}")
    void deleteParentLevel(int id);

    @Select("select * from teacher_level")
    List<TeacherLevel> getTeacherLevelList();

    @Select("select max(level) from teacher_level")
    Integer getTeacherMaxLevel();

    @Insert("insert into teacher_level(level_name, level, amount) values(#{levelName}, #{level}, #{amount})")
    void createTeacherLevel(TeacherLevel teacherLevel);

    @Select("select * from teacher_level where id = #{id}")
    TeacherLevel getTeacherLevelById(Integer id);

    @Update("update teacher_level set level_name = #{levelName}, amount = #{amount} where id = #{id}")
    void updateTeacherLevel(TeacherLevel teacherLevel);

    @Delete("delete from teacher_level where id = #{id}")
    void deleteTeacherLevel(int id);
}

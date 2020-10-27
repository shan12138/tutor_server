package com.dataee.tutorserver.tutoradminserver.departmentmanage.dao;

import com.dataee.tutorserver.entity.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/25 21:00
 */
@Mapper
@Repository
public interface DepartmentManageMapper {
    @Select("select id from department where name = #{name} and parent_id = #{parentId} and state = 1")
    Integer getDepartment(@Param("name") String name, @Param("parentId") Integer parentId);

    @Insert("insert into department(name, parent_id) values(#{name}, #{parentId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createDepartment(Department department);

    @Select("select *from department where state = 1")
    List<Department> getAllDepartment();

    @Select("select count(*) from department where parent_id = #{id} and state = 1")
    Integer isDepartmentLeaf(Integer id);

    @Update("update department set state = 0 where id = #{id}")
    void deleteDepartment(Integer id);

    @Update("update department set name = #{name} where id = #{id}")
    void changeDepartment(@Param("name") String name, @Param("id") Integer id);

    @Select("select parent_id from department where id = #{id}")
    Integer getParentId(Integer id);

    @Select("select *from department where id = #{id} and state = 1")
    Department getDepartmentById(Integer id);
}

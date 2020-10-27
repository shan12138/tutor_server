package com.dataee.tutorserver.tutoradminserver.adminmanage.dao;

import com.dataee.tutorserver.entity.AdminRole;
import com.dataee.tutorserver.entity.Department;
import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminManage;
import com.dataee.tutorserver.tutoradminserver.adminmanage.dao.sqlprovider.QueryAdminSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 21:27
 */
@Mapper
@Repository
public interface AdminManageMapper {
    @SelectProvider(type = QueryAdminSqlProvider.class, method = "getQueryConditionSql")
    @Results(
            value = {
                    @Result(column = "department_id", property = "department",
                            one = @One(select = "getAdminDepartment")),
                    @Result(column = "role_id", property = "roleName",
                            one = @One(select = "getAdminRoleById")),
            }
    )
    List<AdminManage> getAdminList(@Param("keyword") String keyword, @Param("roleId") Integer roleId);

    @Select("select name, id from department where id = #{id}")
    Department getAdminDepartment(int id);

    @Select("select remark as roleName from admin_role where role_id = #{id}")
    String getAdminRoleById(int id);

    @Update("update admin set admin_name = #{adminName}, position = #{position}, remark = #{remark}, department_id" +
            " = #{departmentId} where id = #{id}")
    int changeAdminBasicInfo(AdminManage adminManage);

    @Update("update admin_auth set role_id = #{roleId} where id = #{adminAuthId}")
    int changeAdminRole(@Param("roleId") Integer roleId, @Param("adminAuthId") Integer adminAuthId);

    @Update("update admin_auth set state = 0 where id = #{id}")
    int deleteAdminAuth(Integer id);

    @Update("update admin set state = 0 where id = #{id}")
    int deleteAdmin(Integer id);

    /**
     * 查询管理员身份id
     *
     * @param id
     * @return
     */
    @Select("select admin_id from admin where state = 1 and id = #{id}")
    String queryAdminIdById(Integer id);

    /**
     * 更新指定管理员的密码
     *
     * @param id
     * @param password
     */
    @Update("update admin set password = #{pwd} where state = 1 and id = #{id}")
    void updateAdminPasswordById(@Param("id") Integer id, @Param("pwd") String password);

    @Select("select admin_auth.id from admin, admin_auth where admin.admin_id = admin_auth.admin_id and admin.id = #{id}")
    int getAdminAuthId(int id);

    @Select("select role_id, remark as role_name from admin_role where state = 1")
    List<AdminRole> getAdminRole();
}

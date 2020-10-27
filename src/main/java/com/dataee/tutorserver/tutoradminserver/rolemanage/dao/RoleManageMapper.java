package com.dataee.tutorserver.tutoradminserver.rolemanage.dao;

import com.dataee.tutorserver.entity.AdminRole;
import com.dataee.tutorserver.entity.Administrator;
import com.dataee.tutorserver.entity.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/4 11:22
 */
@Mapper
public interface RoleManageMapper {
    @Select("select * from admin_role where state = 1 and remark like concat('%', #{keyword}, '%')" +
            " order by createdAt desc")
    @Results(
            id = "getCreateRoleAdmin",
            value = {
                    @Result(column = "createdId", property = "createAdminName",
                            one = @One(select = "getCreateRoleAdmin")),
            }
    )
    List<AdminRole> getRoleList(String keyword);

    @Select("select admin_name from admin where id = #{id}")
    String getCreateRoleAdmin(int id);

    @Select("select permission.*, role_permission.granted" +
            " from permission, role_permission where permission.id = role_permission.permissionId  " +
            "and role_permission.roleId = #{roleId}")
    @Results(
            id = "queryRolePermission",
            value = {
                    @Result(column = "parentId", property = "parent",
                            one = @One(select = "getPermissionById")),
            }
    )
    List<Permission> getPermissionsOfRole(@Param("roleId") Integer roleId);

    @Select("select permission.*, role_permission.granted" +
            " from permission, role_permission where permission.id = role_permission.permissionId  " +
            "and role_permission.roleId = #{roleId}")
    @Results(
            value = {
                    @Result(column = "parentId", property = "parent",
                            one = @One(select = "getPermissionById")),
            }
    )
    List<Permission> getPermissionsOfCurrentUser(Integer roleId);

    @Select("select *from permission where id = #{id}")
    Permission getPermissionById(@Param("id") int id);

    @Insert("insert into admin_role(remark, createdAt, state, createdId) values(#{remark}, #{createdAt}, #{state}, #{createdId})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId", keyColumn = "id")
    int createRole(AdminRole role);

    @Insert("insert into role_permission(roleId, permissionId, granted) values(#{roleId}, #{permissionId}, #{granted})")
    void insertPermissionOfRole(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId,
                                 @Param("granted") Boolean granted);

    @Select("select * from admin_role where role_id = #{roleId}")
    AdminRole getRoleById(int roleId);

    @Update("update admin_role set remark = #{remark} where role_id = #{roleId}")
    void updateRoleRemark(@Param("roleId") int roleId, @Param("remark") String remark);

    @Delete("delete from role_permission where roleId = #{roleId} and permissionId = #{permissionId}")
    void deletePermissionOfRole(@Param("roleId") int roleId, @Param("permissionId") Integer permissionId);

    @Delete("delete from admin_role where role_id = #{roleId}")
    void deleteRole(int roleId);

    @Delete("delete from role_permission where roleId = #{roleId}")
    void deleteAllPermissionOfRole(int roleId);

    @Update("update role_permission set granted = #{granted} where roleId = #{roleId} and permissionId = #{permissionId}")
    void updatePermissionOfRole(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId,
                                @Param("granted") Boolean granted);

    @Select("select * from permission")
    @Results(
            id = "queryAllPermission",
            value = {
                    @Result(column = "parentId", property = "parent",
                            one = @One(select = "getPermissionById")),
            }
    )
    List<Permission> getAllPermissions();
}

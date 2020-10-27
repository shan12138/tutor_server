package com.dataee.tutorserver.tutoradminserver.adminmanage.dao;

import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.entity.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/14 2:57
 */
@Repository
@Mapper
public interface AdminLoginMapper {
    /**
     * 获取管理员的凭证信息
     *
     * @param account
     * @return
     */
    @Select("select id, admin_id, admin_name, account from admin where  account = #{account} and state = 1")
    @Results(id = "adminPrincipalsMap", value = {
            @Result(column = "admin_id", property = "userId"),
            @Result(column = "admin_name", property = "personName"),
            @Result(column = "account", property = "username"),
            @Result(property = "roles", column = "admin_id",
                    many = @Many(select = "queryRolesByAdminId"))
    })
    UserPrincipals queryPrincipalsByAdminNameAndROle(@Param("account") String account);

    /**
     * 通过制定的adminId获取Role
     *
     * @param adminId
     * @return
     */
    @Select("select admin_role.role_id, role_name, state from admin_role where admin_role.role_id in " +
            "(select role_id from admin_auth where admin_id=#{adminId} and state =1) and admin_role.state = 1")
    List<Role> queryRolesByAdminId(String adminId);

    /**
     * 获取管理员的口令
     *
     * @param adminId
     * @return
     */
    @Select("select password from admin where admin_id = #{adminId} and state = 1")
    String querySecretByAdminId(@Param("adminId") String adminId);
}

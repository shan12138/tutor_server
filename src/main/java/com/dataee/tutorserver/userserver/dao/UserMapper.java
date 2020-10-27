package com.dataee.tutorserver.userserver.dao;

import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.entity.Role;
import com.dataee.tutorserver.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface  UserMapper {
    /**
     * 通过用户名获取用户信息
     *
     * @param username
     * @return
     */
    @Select("select * from user where role_id=(select role_id from role where state=1 and role_name=#{role}) and username = #{username} and state=1")
    @Results(id = "findUser", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "role", column = "role_id",
                    one = @One(select = "com.dataee.tutorserver.userserver.dao.UserMapper.selectRoleByRoleId"))
    })
    User selectUserByUserName(@Param("username") String username, @Param("role") String role);

    /**
     * 通过roleId查找所属角色
     *
     * @param roleId
     * @return
     */
    @Select("select * from role where state = 1 and role_id=#{roleId}")
    Role selectRoleByRoleId(Integer roleId);

    /**
     * 插入新用户
     *
     * @param userId
     * @param username
     * @param password
     * @return
     */
    @Insert("insert into user(user_id, username, password, role_id) values (#{userId}, #{username}, #{password}, #{roleId})")
    int insertNewUser(@Param("userId") String userId, @Param("username") String username, @Param("password") String password, @Param("roleId") Integer roleId);


    /**
     * 插入用户权限
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Insert("insert into user_auth(user_id, role_id) values (#{userId},#{roleId})")
    int insertUserAuth(@Param("userId") String userId, @Param("roleId") int roleId);

    /**
     * 新增人物
     *
     * @param userId
     * @param table
     * @param phone
     * @return
     */
    @Insert("insert into ${tableName} (user_id, telephone) values (#{userId}, #{phone})")
    int insertPersonAccount(@Param("userId") String userId, @Param("tableName") String table, @Param("phone") String phone);

    /**
     * 更新用户密码
     *
     * @param id
     * @param encodePassword
     */
    @Update("update user set password = #{encodePassword} where id = #{id} and state = 1")
    int updatePasswordByUserId(@Param("id") int id, @Param("encodePassword") String encodePassword);

    /**
     * 获取指定人的personId
     *
     * @param userId
     * @param table
     * @return
     */
    @Select("select ${tableName}_id from ${tableName} where user_id=#{userId} and state !=0")
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 1000)
    Integer queryPersonId(@Param("userId") String userId, @Param("tableName") String table);

    /**
     * 根据角色名称获取角色相应的ID
     *
     * @param role
     * @return
     */
    @Select("select role_id from role where role_name=#{role}")
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 1000)
    Integer queryRoleIdByRole(String role);


    /**
     * 获取管理员的roleId
     *
     * @param roleName
     * @return
     */
    @Select("select role_id from admin_role where role_name=#{roleName}")
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE, timeout = 1000)
    Integer queryRoleIdByAdminRole(String roleName);

    /**
     * 查询用户口令
     *
     * @param userId
     * @return
     */
    @Select("select password from user where user_id = #{userId} and state=1")
    String querySecretByUserId(@Param("userId") String userId);

    /**
     * 获取用户凭证
     *
     * @param username
     * @param role
     * @return
     */
    @Select("select id, user_id, username, user.role_id from user, role where username = #{username} and user.state =1 and " +
            "user.role_id = role.role_id and role.role_name = #{role} and role.state = 1 ")
    @Results(id = "principalsMap", value = {
            @Result(column = "role_id", property = "roles", many = @Many(select = "queryRolesByRoleId"))
    })
    UserPrincipals queryPrincipals(@Param("username") String username, @Param("role") String role);

    /**
     * user_id和role_id是一对一的关系，bean的需要所以有更改
     *
     * @param roleId
     * @return
     */
    @Select("select * from role where role_id = #{roleId} and state=1")
    List<Role> queryRolesByRoleId(Integer roleId);

    @Select("select home_picture from home_picture where state = 1")
    List<String> getHomePictures();


    /**
     * 通过userId获取role_id
     *
     * @param userId
     * @return
     */
    @Select("select role_id from user where state = 1 and user_id = #{userId}")
    Integer queryRoleIdByUserId(@Param("userId") String userId);

    /**
     * 获取用户的密码
     *
     * @param userId
     * @return
     */
    @Select("select password from user where state = 1 and user_id = #{userId}")
    String queryUserPasswordById(@Param("userId") String userId);


    /**
     * 查找数据库符合要求的用户，包括被禁用的
     *
     * @param username
     * @param role
     * @return
     */
    @Select("select count(id) from user where role_id=(select role_id from role where state=1 and role_name=#{roleName}) and username = #{username}")
    Integer queryUserInRegister(@Param("username") String username, @Param("roleName") String role);

    @Update("update user set username = #{phoneNumber} where user_id = #{userId}")
    void updateTelephone(@Param("userId") String userId, @Param("phoneNumber") String phoneNumber);
}
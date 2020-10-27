package com.dataee.tutorserver.userserver.dao;

import com.dataee.tutorserver.entity.Person;
import com.dataee.tutorserver.userserver.bean.UserBean;
import com.dataee.tutorserver.userserver.dao.sqlprovider.PersonSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 与任务和角色相关的表
 *
 * @author JinYue
 * @CreateDate 2019/5/14 1:43
 */
@Mapper
@Repository
public interface PersonMapper {
    /**
     * 查询当前人物的state
     *
     * @param personId
     * @param role
     * @return
     */
    @Select("select state from ${tableName} where ${tableName}_id=#{personId} and state != 0")
    Integer queryPersonStateByPersonIdAndRole(@Param("personId") Integer personId, @Param("tableName") String role);

    /**
     * 根据personId和role来查找该person
     *
     * @param personId
     * @param role
     * @return
     */
    @Select("select ${role}_id from ${role} where ${role}_id=#{personId} and state !=0")
    Integer queryPersonByPersonIdAndRole(@Param("personId") Integer personId, @Param("role") String role);


    /**
     * 获取人物抽象实体
     *
     * @param personId
     * @param personRole
     * @return
     */
    @SelectProvider(type = PersonSqlProvider.class, method = "selectPersonById")
    Person queryPersonById(@Param("personId") Integer personId, @Param("personRole") Integer personRole);


    @Select("select state from ${tableName} where user_id = #{user_id}")
    Integer queryPersonState(@Param("user_id") String userId, @Param("tableName") String tableName);

    /**
     * 获取登录者的姓名
     *
     * @param personId
     * @param tableName
     * @return
     */
    @Select("select ${tableName}_name from ${tableName} where state != 0 and ${tableName}_id = #{personId}")
    String queryPersonNameById(@Param("personId") Integer personId, @Param("tableName") String tableName);

    @Select("select user_id, telephone, ${tableName} as username, state from ${table} where ${tableId} = #{personId}")
    UserBean getUser(@Param("personId") Integer personId, @Param("table") String table, @Param("tableId") String tableId,
                     @Param("tableName") String tableName);

}

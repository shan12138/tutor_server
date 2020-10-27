package com.dataee.tutorserver.tutoradminserver.statemanage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 管理员端的状态统一管理
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 18:43
 */
@Mapper
@Repository
public interface StateManageMapper {
    @Update("update ${table} set state = #{state} where ${tableId} = #{id}")
    int changeDataState(@Param("table") String table, @Param("state") Integer state, @Param("tableId") String tableId,
                        @Param("id") Integer id);

    @Select("select state from ${table} where ${tableId} = #{id}")
    int getDataState(@Param("table") String table, @Param("tableId") String tableId, @Param("id") Integer id);

//    @Update("update user_auth set state = #{state} where id = #{id}")
//    int changeUserAuthState(@Param("id") Integer id, @Param("state") Integer state);

//    @Select("select id from user_auth where user_id in (select user_id from ${table} where ${tableId} = #{id})")
//    int getUserAuthId(@Param("id") Integer id, @Param("tableId") String tableId, @Param("table") String table);

    @Select("select id from user where user_id = (select user_id from ${table} where ${tableId} = #{id})")
    int getUserId(@Param("table") String table, @Param("tableId") String tableId, @Param("id") Integer id);

    @Update("update user set state = #{state} where id = #{id}")
    int changeUserState(@Param("id") int id, @Param("state") int state);
}

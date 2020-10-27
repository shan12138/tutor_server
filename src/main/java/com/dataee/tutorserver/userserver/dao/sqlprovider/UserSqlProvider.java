package com.dataee.tutorserver.userserver.dao.sqlprovider;


import org.apache.ibatis.jdbc.SQL;

/**
 * @author JinYue
 * @CreateDate 2019/5/7 12:15
 */
public class UserSqlProvider {
    public String selectPersonId() {
        return new SQL()
                .SELECT("${table}_id")
                .FROM("${table}")
                .WHERE("user_id = #{userId}")
                .WHERE("state = 1").toString();
    }
}

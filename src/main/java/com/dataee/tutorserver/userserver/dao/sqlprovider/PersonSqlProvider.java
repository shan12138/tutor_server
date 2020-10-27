package com.dataee.tutorserver.userserver.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author JinYue
 * @CreateDate 2019/5/14 20:51
 */
public class PersonSqlProvider {
    public String selectPersonById(@Param("personRole") Integer personRole) {
        String table = (personRole == 20) ? "teacher" : "parent";
        String chineseTable = (personRole == 20) ? "教师" : "家长";
        String person_id = table + "_id";
        return new SQL()
                .SELECT(person_id + " as id")
                .SELECT("'" + chineseTable + "' as roleName")
                .SELECT(table + "_name as name")
                .SELECT("sex, telephone")
                .FROM(table)
                .WHERE(person_id + " = #{personId}")
               // .WHERE("state!=0")
                .toString();

    }
}

package com.dataee.tutorserver.userserver.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.javassist.runtime.Desc;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author JinYue
 * @CreateDate 2019/5/9 0:03
 */
public class ErrorQuestionSqlProvider {
    public String selectErrorQuestions(@Param("role") String role) {
        return new SQL() {{
            SELECT("id, person_id, person_role, essential_content, course_name, lesson_number, remark, time, state");
            FROM("error_question");
            if (!"admin".equals(role)) {
                WHERE("person_id=#{personId}");
            }
            WHERE("person_role=(select role_id from role where state = 1 and role_name = #{role})");
            WHERE("state=1");
            ORDER_BY("time");
        }}.toString();
    }
}

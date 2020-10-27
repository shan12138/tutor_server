package com.dataee.tutorserver.tutoradminserver.adminmanage.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/15 10:24
 */
public class QueryAdminSqlProvider {
    public String getQueryConditionSql(@Param("keyword") String keyword, @Param("roleId") Integer roleId) {
        StringBuilder sql=new StringBuilder("SELECT admin.id, admin.admin_name, account as telephone, " +
                "position, department_id, remark, \n" +
                "admin_auth.role_id, admin_auth.id as admin_auth_id from admin, admin_auth where admin.admin_id\n" +
                "= admin_auth.admin_id and admin.state = 1\n" +
                "and admin_auth.state = 1");
        if (keyword != null && !keyword.equals("")) {
           sql.append(" and (admin_name like concat('%', #{keyword}, '%') or account like " +
                   "concat('%', #{keyword}, '%'))");
        }
        if (roleId != null) {
            sql.append(" and role_id = #{roleId}");
        }
        return sql.toString();
    }
}

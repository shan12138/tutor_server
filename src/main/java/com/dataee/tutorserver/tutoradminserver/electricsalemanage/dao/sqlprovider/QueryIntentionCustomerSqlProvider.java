package com.dataee.tutorserver.tutoradminserver.electricsalemanage.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/24 21:23
 */
public class QueryIntentionCustomerSqlProvider {
    public String getQueryConditionSql(@Param("queryCondition") String queryCondition,
                                       @Param("parentSex") String parentSex,
                                       @Param("teacherSex") String teacherSex,
                                       @Param("state") String state) {
        String sql = "select * from intention_customer where ";
        String concat = " concat('%', #{queryCondition}, '%') ";
        if (!queryCondition.equals("")) {
            sql += " (name like" + concat + "or telephone like" + concat + "or weak_discipline like" + concat + ")";
        } else {
            sql += "1=1";
        }
        if (!parentSex.equals("")) {
            sql += " and sex = #{parentSex}";
        }
        if (!teacherSex.equals("")) {
            sql += " and tutor_sex = #{teacherSex}";
        }
        if (state.equals("未推送")) {
            sql += " and state = 1";
        }
        if (state.equals("已推送")) {
            sql += " and state = 2";
        }
        if (state.equals("全部")) {
            sql += " and state != 0";
        }
        return sql;
    }

}

package com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 管理端根据条件查询家长信息
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 20:05
 */
public class QueryParentSqlProvider {
    /**
     * 家长申请
     * @param queryCondition
     * @param state
     * @param sex
     * @return
     */
    public String queryParentByCondition(@Param("queryCondition") String queryCondition,
                                         @Param("state") String state,
                                         @Param("sex") String sex) {

        StringBuilder sql=new StringBuilder("SELECT  * from parent    WHERE  state=2 ");
        if (!queryCondition.equals("")) {

          sql.append("  and(parent_name like concat('%', #{queryCondition}, '%')  or telephone like concat('%', #{queryCondition}, '%'))");
        }
        if (state.equals("申请中")){

            sql.append(" and (state=2 )");
        }
        if (!sex.equals("")) {

            sql.append(" and  (sex =#{sex})");
        }
        return sql.toString();
    }


    /**
     * 正式家长
     * @param studentName
     * @param sex
     * @return
     */
    public String queryStudentByStudentNameAndSex(
            @Param("studentName") String studentName,
            @Param("sex") String sex) {

         StringBuilder sql=new StringBuilder("SELECT p.`parent_id`,p.`parent_name`,p.`telephone`,p.invited_parent_id,p.invited_partner_id, p.partner_id,s.`student_id`,s.sex,s.`student_name` FROM parent p INNER JOIN  student s ON s.`parent_id`=p.`parent_id`  WHERE (p.state = 3 OR p.state = 0)  ");
        if(!studentName.equals("")){

            sql.append("  and (s.student_name like concat('%', #{studentName}, '%'))  ");
        } if (!sex.equals("")) {

            sql.append(" and  (s.sex =#{sex})");
        }

        sql.append(" ORDER BY p.parent_id DESC");
        return sql.toString();
    }





}

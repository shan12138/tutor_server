package com.dataee.tutorserver.tutoradminserver.teachermanage.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/11 19:40
 */
public class QueryTeacherSqlProvider {
    public String queryTeacherByCondition(@Param("queryCondition") String queryCondition,
                                          @Param("state") String state,
                                          @Param("sex") String sex) {

       StringBuilder sql=new StringBuilder(" SELECT  teacher_id, teacher_name, telephone, sex, state FROM teacher WHERE (state = 2 or state = 3) ");



        if (!queryCondition.equals("")) {

          sql.append(" and (teacher_name like concat('%', #{queryCondition}, '%') or " +
                  "telephone like concat('%', #{queryCondition}, '%'))");
        }
        if(state.equals("待审核")){

            sql.append(" and  (state = 2)");
        }
        if(state.equals("已驳回")){

            sql.append(" and  (state = 3)");
        }
        if(state.equals("全部")){

            sql.append(" and  (state = 2 or state = 3)");
        }
        if(!sex.equals("")){

            sql.append(" and (sex = #{sex})");
        }
       return sql.toString();



    }

    /**
     * 修改带单
     * @param queryCondition
     * @param state
     * @param sex
     * @return
     */

    public String queryTeacherByCondition2(@Param("queryCondition") String queryCondition,
                                           @Param("state") String state,
                                           @Param("sex") String sex,
                                           @Param("start") Integer start,
                                           @Param("end") Integer end
                                           ) {

         StringBuilder sql = new StringBuilder("SELECT t.`teacher_id`,t.`teacher_name`,t.`telephone`, t.`sex`,  t.`state`,\n" +
                 "(SELECT COUNT(1) FROM course c ,student s WHERE c.student_id =s.student_id AND c.teacher_id =t.teacher_id )AS COUNT FROM teacher t \n" +
                 "WHERE (t.state=0 OR t.state=4)");

        if (queryCondition!=null && !queryCondition.equals("")) {
            sql.append(" and (teacher_name like concat('%', #{queryCondition}, '%') or " +
                    "telephone like concat('%', #{queryCondition}, '%'))");
        }
        if (state!=null && state.equals("已启用")) {
            sql.append(" and  (state = 4)");
        }
        if (state!=null && state.equals("已禁用")) {
            sql.append(" and  (state = 0)");
          }
        if (state!=null && state.equals("全部")) {
             sql.append(" and  (state = 0 or state = 4)");
         }

        if(sex!=null && !sex.equals("")){
            sql.append(" and (sex = #{sex})");
        }
        if (start != null && end != null) {
          sql.append("  GROUP  BY  t.`teacher_id`\n" +
                  "  HAVING  COUNT BETWEEN #{start} AND #{end}");
        }
        return sql.toString();
    }

    /**
     * 获取受邀请的老师的信息
     */
    public String queryTeacherInviteByCondition(@Param("queryCondition") String queryCondition,
                                                @Param("telephone") String telephone,
                                                @Param("partnerId") Integer partnerId,
                                                @Param("status") String status

    ) {

        StringBuilder sql = new StringBuilder("SELECT   ti.id,ti.teacher_name,ti.telephone,ti.teacher_id,p.partner_id, p.partner_name,ti.`status` FROM  teacher_invitation  ti\n" +
                "left JOIN   teacher t1  ON   ti.teacher_id = t1.teacher_id\n" +
                "inner JOIN   teacher t2  ON   ti.invited_teacher_id = t2.teacher_id\n" +
                "left  JOIN   partner  p ON    t2.partner_id   = p.partner_id where 1=1");

        if (queryCondition!=null && !queryCondition.equals("")) {
            sql.append(" and (ti.teacher_name like concat('%', #{queryCondition}, '%') or " +
                    "ti.telephone like concat('%', #{queryCondition}, '%'))");
        }
        if (telephone!=null && !telephone.equals("")) {
            sql.append(" and (ti.telephone like concat('%', #{telephone}, '%'))");
        }
        if(partnerId!=null&&!partnerId.equals("")){
            sql.append(" and (t2.partner_id=#{partnerId})");
        }
        if (status!=null && status.equals("未申请")) {
            sql.append(" and  (ti.status =\"未申请\" )");
        }
        if (status!=null && status.equals("申请中")) {
            sql.append(" and  (ti.status =\"申请中\")");
        }
        if (status!=null && status.equals("已拒绝")) {
            sql.append(" and  (ti.status =\"已拒绝\")");
        }
        if (status!=null && status.equals("待任教")) {
            sql.append(" and  (ti.status =\"待任教\")");
        }
        if (status!=null && status.equals("已任教")) {
            sql.append(" and  (ti.status =\"已任教\")");
        }
        return sql.toString();
    }
    //获取邀请老师赠礼列表

    public String queryTeacherGiftInviteByCondition(@Param("queryCondition") String queryCondition,
                                                @Param("invitePerson") Integer invitePerson,
                                                @Param("status") String status

    ) {

        StringBuilder sql = new StringBuilder("SELECT itg.id ,t2.teacher_id, t2.teacher_name AS invite_person,t2.telephone, t1.teacher_name AS  invited_person,p.partner_name,itg.invitation_success_date,itg.`status` FROM  invitation_teacher_gift    itg\n" +
                "INNER JOIN   teacher  t1 ON  itg.invitation_teacher_id =t1.teacher_id\n" +
                "INNER JOIN   teacher  t2 ON  itg.teacher_id =  t2.teacher_id\n" +
                "left JOIN  partner  p ON  t1.partner_id=p.partner_id where 1=1 ");

        if (queryCondition!=null && !queryCondition.equals("")) {
            sql.append(" and (t1.teacher_name like concat('%', #{queryCondition}, '%') or " +
                    "t2.teacher_name like concat('%', #{queryCondition}, '%'))");
        }
        if (invitePerson!=null && !invitePerson.equals("")) {
            sql.append(" and (t2.teacher_id = #{invitePerson})");
        }

        if (status!=null && status.equals("已发放")) {
            sql.append(" and  (itg.status =\"已发放\")");
        }
        if (status!=null && status.equals("待发放")) {
            sql.append(" and  (itg.`status` =\"待发放\")");
        }
        sql.append(" ORDER BY invitation_success_date  desc");
        return sql.toString();
    }

}



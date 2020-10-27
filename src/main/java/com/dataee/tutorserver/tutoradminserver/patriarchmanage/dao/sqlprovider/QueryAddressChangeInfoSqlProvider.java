package com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao.sqlprovider;

import com.dataee.tutorserver.entity.Teacher;
import org.apache.ibatis.annotations.Param;

/**
 * 查询地址变更信息
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/17 10:06
 */
public class QueryAddressChangeInfoSqlProvider {
    public String queryAddressChangeInfoByCondition(@Param("teacher") String teacher,
                                                    @Param("studentName")String studentName,
                                                    @Param("state") String state) {

        StringBuilder sql=new StringBuilder("SELECT  aci.id,cp.contract_id ,pc.sn,s.`student_name`,p.`telephone` AS parentTel,t.`telephone` AS teacherTel,t.`teacher_name`,CONCAT(aci.new_region, aci.new_address_detail) AS new_address,CONCAT(aci.old_region, aci.old_address_detail)AS old_address,aci.`remark`,aci.`is_confirmed` AS confirmed\n" +
                "FROM   address_change_info   aci\n" +
                "INNER JOIN address_course acc ON acc.address_id=aci.address_id\n" +
                "INNER JOIN  course c ON c.address_id=aci.address_id\n" +
                "INNER JOIN parent p ON p.parent_id=c.parent_id\n" +
                "INNER JOIN teacher t ON t.teacher_id=c.teacher_id\n" +
                "INNER JOIN  student s ON s.student_id=c.student_id\n" +
                "left  JOIN  contract_pdf cp ON cp.id_card=s.id_card\n" +
                "left  join  parent_contract pc on pc.id = cp.parent_contract_id   where c.state!=0   ");
            if(!teacher.equals("")){

                sql.append(" and t.`teacher_name` like concat('%', #{teacher}, '%')  ");
            }
            if(!studentName.equals("")){

                sql.append(" and s.`student_name` like concat('%', #{studentName}, '%')   ");
            }
            if(state != null && !state.equals("")){

                sql.append(" and aci.`is_confirmed` =#{state} ");
            }

            return sql.toString();
    }
}

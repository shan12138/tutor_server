package com.dataee.tutorserver.tutoradminserver.filemanage.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class  ErrorQuestionsSqlProvider {
    public String queryErrorQuestionsByCondition(
                                          @Param("studentName") String studentName,
                                          @Param("queryCondition") String queryCondition,
                                          @Param("lessonNumber") Integer lessonNumber,
                                          @Param("essentialContent") String essentialContent
                                                                                     ) {
     StringBuilder sql=new StringBuilder("SELECT e.`id`,e.`person_id`,e.`person_role`,e.`essential_content`,e.`lesson_number`,e.`remark`,e.`print`,e.`course`, e.`time`,c.`course_id`,c.`subject`,c.`grade`,s.`number`,s.`student_name`,cp.`contract_id`,pc.sn,t.`teacher_name`,p.`parent_name` FROM  error_question e \n" +
             "INNER JOIN course c ON c.course_id=e.course_id\n" +
             "INNER JOIN student s ON c.student_id=s.student_id \n" +
             "INNER JOIN parent p ON  p.`parent_id`=c.parent_id \n" +
             "INNER JOIN teacher t ON t.`teacher_id`=c.`teacher_id` \n" +
             "left JOIN contract_pdf  cp ON cp.id_card=s.id_card \n" +
             "left JOIN parent_contract  pc ON cp.parent_contract_id = pc.id \n" +
             "WHERE s.state=1 AND c.state=2 AND  e.state=1 AND cp.state!=0 ");
     if(studentName!=null&&!studentName.equals("")){

       sql.append("   and  (student_name like concat('%', #{studentName}, '%'))  ");
     }
     if(queryCondition!=null&&!queryCondition.equals("")){

        sql.append(" and (teacher_name like concat('%', #{queryCondition}, '%') or " +
                    "parent_name like concat('%', #{queryCondition}, '%'))");
     }
     if(lessonNumber!=null&&!lessonNumber.equals("")){

         sql.append(" and  (lesson_number   = #{lessonNumber}) ");
     }
     if (essentialContent!=null&&!essentialContent.equals("")) {

     sql.append(" and  (essential_content like concat('%', #{essentialContent}, '%'))");
     }
     return sql.toString();
    }

    public String queryTeacherResources( @Param("studentName")String studentName,@Param("teacher")String teacher,@Param("courseName")String courseName,@Param("type")String type,@Param("headTeacher")String headTeacher,@Param("courseAdmin")String courseAdmin ) {

        StringBuilder sql=new StringBuilder("SELECT rp.resource_id, rp.resource_name, rp.pdf_address, t.teacher_name, c.course_name, rp.resource_type, rp.upload_state ,stu.admin_name AS studyAdmin FROM  resource_pdf rp \n" +
                "INNER JOIN course c    ON rp.course_id=c.course_id\n" +
                "INNER JOIN  teacher t  on rp.person_id=t.teacher_id\n" +
                "inner JOIN  admin  ha ON  ha.id=c.head_admin_id   \n" +
                "inner JOIN admin sa ON sa.id=c.course_admin_id\n" +
                "INNER JOIN admin stu ON stu.id=c.study_admin_id\n" +
                "INNER JOIN  student s ON s.student_id=c.student_id\n" +
                "WHERE c.state = 2 AND  rp.state = 1 and t.state = 4 AND rp.lesson_id = 0");
         if(studentName!=null&&!studentName.equals("")){

             sql.append(" and  (s.student_name like concat('%', #{studentName}, '%'))");
         }
         if(teacher!=null&&!teacher.equals("")){

             sql.append(" and  (t.teacher_name like concat('%', #{teacher}, '%'))");
         }
         if(courseName!=null&&!courseName.equals("")){

             sql.append(" and  (c.course_name like concat('%', #{courseName}, '%'))");
         }
         if(type!=null&&!type.equals("")){

             sql.append(" and  (rp.resource_type like concat('%', #{type}, '%'))");
         }
         if(headTeacher!=null&&!headTeacher.equals("")){

             sql.append(" and  (ha.admin_name like concat('%', #{headTeacher}, '%'))");
         }
         if(courseAdmin!=null&&!courseAdmin.equals("")){

             sql.append(" and  (sa.admin_name like concat('%', #{courseAdmin}, '%'))");
         }
        return sql.toString();
    }















}

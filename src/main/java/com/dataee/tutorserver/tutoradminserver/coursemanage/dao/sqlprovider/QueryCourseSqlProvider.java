package com.dataee.tutorserver.tutoradminserver.coursemanage.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/21 14:30
 */
public class QueryCourseSqlProvider {
    public String getQueryCourseSql(@Param("studentName") String studentName,
                                    @Param("grade") String grade,@Param("subject") String subject,
                                    @Param("teacher") String teacher,
                                    @Param("headTeacher") String headTeacher,
                                    @Param("productId") Integer productId) {

         StringBuilder sql=new StringBuilder("SELECT c.id, c.course_id, c.course_name,pro.product_name,s.student_name,c.grade,c.subject,p.parent_name,ha.admin_name AS headTeacher,ha.account,sa.admin_name AS scheduleAdmin,t.teacher_name,t.telephone FROM course c   \n" +
                 "INNER JOIN student s ON s.student_id=c.student_id \n" +
                 "INNER JOIN parent p ON p.parent_id=c.parent_id \n" +
                 "INNER JOIN  admin ha ON ha.id=c.head_admin_id\n" +
                 "INNER JOIN  admin sa ON sa.id=c.course_admin_id\n" +
                 "INNER JOIN  product pro ON c.product_id = pro.id\n" +
                 "left JOIN  teacher t ON t.teacher_id=c.teacher_id  WHERE c.state!=0 AND s.state!=0 AND p.state=3");
          if(studentName!=null && !studentName.equals("")){
              sql.append("  and   s.student_name like concat('%', #{studentName}, '%')  ");
          }
          if (grade!=null && !grade.equals("")) {
              sql.append(" and c.grade = #{grade}");
          }
          if(subject!=null && !subject.equals("")){
              sql.append(" and c.subject=#{subject} ");
          }
          if(teacher!=null && !teacher.equals("")){
              sql.append(" and t.teacher_name like concat('%', #{teacher}, '%')  ");
          }
          if(headTeacher!=null && !headTeacher.equals("")){
              sql.append(" and ha.admin_name  like concat('%', #{headTeacher}, '%') ");
          }
          if(productId!=null && !productId.equals("")){
              sql.append(" and pro.id  = #{productId} ");
          }
            return sql.toString();
    }

    public String getQueryCourseSql1(@Param("week") Integer week,@Param("studentName") String studentName,@Param("teacher") String teacher,@Param("grade") String grade,@Param("subject") String subject,@Param("headTeacher") String headTeacher,@Param("startTime") String startTime,@Param("endTime") String endTime) {
       StringBuilder sql=new StringBuilder("SELECT resource_info.course_id, resource_info.lesson_id, course_name, resource_info.teacher_id, student_id, head_admin_id,course_admin_id,study_admin_id,lesson_number, course_time, resource_type, upload_state FROM resource_pdf\n" +
               "right JOIN (select course.course_id , lesson.id as lesson_id ,lesson_number,course_name, course.teacher_id, course.student_id,student_name,teacher_name, head_admin_id,course_admin_id,study_admin_id,admin.admin_name,course_time,course.grade,subject from lesson, course,teacher,student,admin\n" +
               "where (course.course_id = lesson.course_id and course.state = 2 AND course.teacher_id=teacher.teacher_id AND course.student_id=student.student_id and course.head_admin_id=admin.id) and (lesson.state = 1 and lesson.week_lessons_id in (select id from week_lessons where WEEK =#{week}))");
        if(studentName!=null&&!studentName.equals("")){
            sql.append("  and  student_name like concat('%', #{studentName}, '%')  ");
        }
        if(teacher!=null&&!teacher.equals("")){
            sql.append(" and teacher_name like concat('%', #{teacher}, '%')  ");
        }
        if (grade!=null&&!grade.equals("")) {
            sql.append(" and course.grade = #{grade}");
        }
        if(subject!=null&&!subject.equals("")){
            sql.append(" and subject=#{subject} ");
        }

        if(headTeacher!=null&&!headTeacher.equals("")){
            sql.append(" and  admin_name like concat('%', #{headTeacher}, '%')   ");
        }
        if (startTime!=null&&!startTime.equals("")&&endTime!=null&&!endTime.equals("")){
            sql.append(" and course_time BETWEEN #{startTime} AND #{endTime}  ");
        }
        sql.append(" )AS resource_info  on resource_pdf.state = 1 and resource_info.lesson_id = resource_pdf.lesson_id");
        return sql.toString();
    }


    public String getTempNotSpeakingTeacher(@Param("courseId")Integer id,@Param("queryCondition")String queryCondition,@Param("grade")String grade,
                                     @Param("subject")String subject,@Param("sex")String sex,@Param("state")String state){
        StringBuilder sql=new StringBuilder("select distinct t.teacher_id, t.teacher_name, t.telephone, t.sex, 0 as tempSpeaking from teacher t,teaching_area ta  WHERE t.teacher_id=ta.teacher_id  and ta.state!=0   AND  t.state = 4 AND t.teacher_id \n" +
                "not in(select teacher_id from temp_speaking where state = 1 and course_id = #{courseId}) ");

        if(queryCondition!=null&&!queryCondition.equals("")){
            sql.append(" and(t.teacher_name like concat('%', #{queryCondition}, '%')  or t.telephone like concat('%', #{queryCondition}, '%')) ");
        }
        if(grade!=null&&!grade.equals("")){
            sql.append(" and ta.grade=#{grade}");
        }

        if(sex!=null && !sex.equals("")){
            sql.append(" and t.sex=#{sex}");
        }
        if(subject!=null && !subject.equals("")){
            sql.append(" and ta.subject=#{subject}");
        }
        if(state!=null && state.equals("安排试讲")&&!state.equals("")){
            sql.append("  and t.state=10");
        }
            return sql.toString();
    }
    public String getTempSpeakingTeacher(@Param("courseId")Integer courseId,@Param("queryCondition")String queryCondition,@Param("grade")String grade,
                                     @Param("subject")String subject,@Param("sex")String sex,@Param("state")String state){
        StringBuilder sql=new StringBuilder("select distinct t.teacher_id, t.teacher_name, t.telephone, t.sex, 1 as tempSpeaking from teacher t,teaching_area ta  WHERE t.teacher_id=ta.teacher_id and ta.state!=0   AND  t.state = 4 AND t.teacher_id \n" +
                " in(select teacher_id from temp_speaking where state = 1 and course_id = #{courseId}) ");

        if(queryCondition!=null && !queryCondition.equals("")){
            sql.append(" and(t.teacher_name like concat('%', #{queryCondition}, '%')  or t.telephone like concat('%', #{queryCondition}, '%')) ");
        }
        if(grade!=null && !grade.equals("")){
            sql.append(" and ta.grade=#{grade}");
        }
        if(subject!=null && !subject.equals("")){
            sql.append(" and ta.subject=#{subject}");
        }
        if(sex!=null && !sex.equals("")){
            sql.append(" and t.sex=#{sex}");
        }
        if(state!=null && state.equals("待命") && !state.equals("")){
            sql.append("  and t.state=10");
        }
            return sql.toString();
    }

    public String getLessonsByCourseId(@Param("courseId")Integer courseId,@Param("teacherName")String teacherName,@Param("status")String status,
                                         @Param("startTime")String startTime,@Param("endTime")String endTime){
        StringBuilder sql=new StringBuilder("SELECT   l.id, l.remark_check_in_time,l.remark_check_out_time,t.teacher_name  FROM  lesson  l\n" +
                                            "INNER JOIN teacher t ON t.teacher_id =l.teacher_id\n" +
                                            " WHERE  l.course_id = #{courseId}   AND  l.state = 1 AND t.state=4");

        if(teacherName!=null && !teacherName.equals("")){
            sql.append(" and(t.teacher_name like concat('%', #{teacherName}, '%')) ");
        }
        if(status!=null && status.equals("已开课")){
            sql.append(" and  NOW()  BETWEEN l.remark_check_in_time AND l.remark_check_out_time ");
        }
        if(status!=null && status.equals("未开课")){
            sql.append(" and  (NOW()<l.remark_check_out_time  or   NOW()>l.remark_check_out_time)   ");
        }
        if (startTime!=null&&!startTime.equals("")&&endTime!=null&&!endTime.equals("")){
            sql.append(" and l.remark_check_in_time BETWEEN #{startTime} AND #{endTime}  and l.remark_check_out_time BETWEEN #{startTime} AND #{endTime}  ");
        }
        return sql.toString();
    }


















}

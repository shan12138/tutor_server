package com.dataee.tutorserver.tutoradminserver.filemanage.dao;

import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ErrorQuestionResponseBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.dao.sqlprovider.ErrorQuestionsSqlProvider;
import com.dataee.tutorserver.tutoradminserver.teachermanage.dao.sqlprovider.QueryTeacherSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/14 22:01
 */
@Repository
@Mapper
public interface ErrorQuestionsMapper {
    /**
     * 获取全部错题集
     *
     * @return
     */
   /* @Select("select id, person_id, person_role, essential_content, error_question.course_id, number, student_name, subject, lesson_number, grade, remark, print,course,time from error_question \n" +
            "inner join\n" +
            "(select course_id, number, student_name, subject, course.grade from course, student where course.state = 2 and student.student_id = course.student_id and student.state = 1) as course_info\n" +
            "on error_question.state = 1 where course_info.course_id = error_question.course_id")*/
    @SelectProvider(type = ErrorQuestionsSqlProvider.class, method = "queryErrorQuestionsByCondition")
   @Results(id = "adminErrorQuestionsMap", value = {
            @Result(column = "{personId = person_id,personRole = person_role}", property = "person", javaType = String.class,
                    one = @One(select = "com.dataee.tutorserver.userserver.dao.PersonMapper.queryPersonById")),
    })
    List<ErrorQuestionResponseBean> queryErrorQuestionsList(
                                                @Param("studentName")String studentName,
                                                @Param("queryCondition")String queryCondition,
                                                @Param("lessonNumber")Integer lessonNumber,
                                                @Param("essentialContent")String essentialContent
                                                            );

    /**
     * 查找指定的错题
     *
     * @param id
     * @return
     */


    @Select("select id, person_id, person_role, essential_content, error_question.course_id, number, student_name, subject, grade, lesson_number, remark, print,course,time from error_question\n" +
            "inner join\n" +
            "(select course_id, number, student_name, subject, course.grade from course, student where course.state = 2 and student.student_id = course.student_id and student.state = 1) as course_info\n" +
            "on error_question.state = 1 and error_question.id = #{id} where course_info.course_id = error_question.course_id")



    @Results(id = "adminErrorQuestionMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "{personId = person_id,personRole = person_role}", property = "person", javaType = String.class,
                    one = @One(select = "com.dataee.tutorserver.userserver.dao.PersonMapper.queryPersonById")),
            @Result(column = "id", property = "questionPicture",
                    many = @Many(select = "com.dataee.tutorserver.userserver.dao.ErrorQuestionMapper.queryErrorQuestionImages"))
    })
    ErrorQuestionResponseBean queryErrorQuestionById(@Param("id") Integer id);

    /**
     * 更新打印状态
     *
     * @param id
     * @param status
     * @return
     */
    @Update("update error_question set print = #{status} where state = 1 and id = #{id}")
    int updatePrintStatus(@Param("id") int id, @Param("status") boolean status);

    /**
     * 修改排课状态
     *
     * @param id
     * @param status
     * @return
     */
    @Update("update error_question set course = #{status} where state = 1 and id = #{id}")
    int updateCourseStatus(@Param("id") int id, @Param("status") boolean status);


    /**
     * 获取下载的包名
     *
     * @param errorQuestionId
     * @return
     */
    @Select("select concat_ws('-', student_name, subject, grade, lesson_number) as name from error_question\n" +
            "inner join\n" +
            "(select course_id, student_name, subject, course.grade from course, student where course.state = 2 and student.student_id = course.student_id and student.state = 1) as course_info\n" +
            "where error_question.state = 1 and error_question.id = #{id} and course_info.course_id = error_question.course_id")
    String queryErrorQuestionPackageName(@Param("id") int errorQuestionId);

}

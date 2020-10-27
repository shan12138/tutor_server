package com.dataee.tutorserver.userserver.dao;

import com.dataee.tutorserver.entity.ErrorQuestion;
import com.dataee.tutorserver.entity.Person;
import com.dataee.tutorserver.userserver.bean.CourseResponseBean;
import com.dataee.tutorserver.userserver.bean.ErrorQuestionRequestBean;
import com.dataee.tutorserver.userserver.dao.sqlprovider.ErrorQuestionSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/7 0:43
 */
@Mapper
@Repository
public interface ErrorQuestionMapper {
    /**
     * 添加错题
     *
     * @param requestBean
     * @param personId
     * @param role
     * @return
     */


    @Insert("insert into error_question(person_id, person_role, essential_content, course_id,course_name,lesson_id,lesson_number, remark)\n" +
            "values(#{personId}, #{role}, #{requestBean.keyWord}, #{requestBean.courseId},#{requestBean.courseName},#{requestBean.lessonId}, " +
            "#{requestBean.lessonNumber},#{requestBean.remarks})")
    @Options(useGeneratedKeys = true, keyProperty = "requestBean.id", keyColumn = "id")
    void addErrorQuestion(@Param("requestBean") ErrorQuestionRequestBean requestBean, @Param("personId") int personId, @Param("role") int role);

    /**
     * 保存错题图片资源地址
     *
     * @param errorQuestionId
     * @param errorQuestions
     */
    @Insert({"<script> ",
            "insert into error_question_image(error_question_id, picture_address) ",
            "values ",
            "<foreach collection='list' item='errorQuestionImageAddress' index='index' separator=','> ",
            "(#{errorQuestionId}, #{errorQuestionImageAddress}) ",
            "</foreach> ",
            "</script>"})
    void addErrorQuestionImages(@Param("errorQuestionId") int errorQuestionId, @Param("list") List<String> errorQuestions);

    /**
     * 查找个人错题列表
     *
     * @param personId
     * @param role
     * @return
     */
    @SelectProvider(type = ErrorQuestionSqlProvider.class, method = "selectErrorQuestions")
    @Results(id = "errorQuestionListMap", value = {
            @Result(property = "person", column = "{personId=person_id, personRole=person_role}", javaType = Person.class,
                    one = @One(select = "com.dataee.tutorserver.userserver.dao.PersonMapper.queryPersonById")),
    })
    List<ErrorQuestion> queryErrorQuestionsByPersonId(@Param("personId") Integer personId, @Param("role") String role);

    /**
     * 根据ID查找指定的错题
     *
     * @param errorQuestionId
     * @return
     */
    @Select("select id, person_id, person_role, essential_content, course_name, lesson_number, remark, time, state " +
            "from error_question where id=#{errorQuestionId} and state =1")
    @Results(id = "errorQuestionMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "person", column = "{personId=person_id, personRole=person_role}", javaType = Person.class,
                    one = @One(select = "com.dataee.tutorserver.userserver.dao.PersonMapper.queryPersonById")),
            @Result(property = "questionPicture", column = "id",
                    many = @Many(select = "com.dataee.tutorserver.userserver.dao.ErrorQuestionMapper.queryErrorQuestionImages"))
    })
    ErrorQuestion queryErrorQuestionByQuestionId(int errorQuestionId);

    /**
     * 获取图片地址
     *
     * @param id
     * @return
     */
    @Select("select picture_address from error_question_image where error_question_id = #{id} and state = 1")
    List<String> queryErrorQuestionImages(int id);


    /**
     * 删除指定的错题
     *
     * @param id
     * @return
     */
    @Update("update error_question set state = 0 where id = #{id}")
    Integer deleteErrorQuestion(Integer id);

    /**
     * 删除指定的错题图片
     *
     * @param errorQuestionId
     * @return
     */
    @Update("update error_question_image set state = 0 where error_question_id = #{errorQuestionId}")
    Integer deleteErrorQuestionImages(Integer errorQuestionId);
}

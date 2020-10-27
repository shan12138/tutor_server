package com.dataee.tutorserver.tutorauditionclassserver.auditionclassmanage.dao;

import com.dataee.tutorserver.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CreateFormMapper {

    @Insert("insert into  register_form (node_id, time, student_name, gender, school, grade, tutorial_subject, score, ranking," +
            " student_character, habit_attitude, family_situation, learn_responsibility, remark, type, course_time, address, subject," +
            "later_class, teacher_gender, teacher_character, teacher_other, course_consultant)values" +
            "(#{nodeId}, #{time}, #{studentName}, #{gender}, #{school}, #{grade}, #{tutorialSubject},#{score}," +
            "#{ranking},#{studentCharacter},#{habitAttitude},#{familySituation},#{learnResponsibility},#{remark}," +
            "#{type},#{courseTime},#{address},#{subject},#{laterClass},#{teacherGender},#{teacherCharacter},#{teacherOther},#{courseConsultant}) ")
    void  savePersonalRegisterForm(RegisterForm registerForm);

    @Insert("insert into accompany_register_form(node_id , time,remark, student_name,gender,school,grade,study_situation,student_character,audition_type,time_subject,address_num,expect_teacher,course_consultant)" +
            " values (#{nodeId} ,#{time},#{remark}, #{studentName}, #{gender}, #{school}, #{grade},#{studySituation},#{studentCharacter},#{auditionType},#{timeSubject},#{addressNum},#{expectTeacher},#{courseConsultant} )")
    void  saveAccompanyRegisterForm(AccompanyRegisterForm accompanyRegisterForm);

    @Insert("insert into head_master_examine(node_id,is_agree) values(#{nodeId},#{isAgree} )")
    void  saveHeadMasterExamine(HeadMasterExamine headMasterExamine);

    @Insert("insert into match_teacher_form(node_id,sn,version,teacher_name,teacher_gender,school,grade_major,contact_model,wechat_number,email,emergency_contact1,emergency_contact2,character_honer_experience_course,near_time_arrange,data_is_complete,match_score,toll,distance_time,train_time,teacher_consume_name,pdf_address,time)values" +
            "(#{nodeId},#{sn},#{version},#{teacherName},#{teacherGender},#{school},#{gradeMajor},#{contactModel},#{wechatNumber},#{email},#{emergencyContact1},#{emergencyContact2},#{characterHonerExperienceCourse},#{nearTimeArrange},#{dataIsComplete},#{matchScore},#{toll},#{distanceTime},#{trainTime},#{teacherName},#{pdfAddress},#{time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveMatchTeacherForm(MatchTeacherForm matchTeacherForm);

    @Insert("insert into train_form (node_id,get_form_time,train_score,suggestion_evaluation,end_time )values(#{nodeId},#{getFormTime},#{trainScore},#{suggestionEvaluation},#{endTime})")
    void saveTrainForm(TrainForm trainForm);

    @Insert("insert into audition_feedback_form (node_id,teacher_summary,audition_summary,time,audition_result,teacher_match_suggestion) values (#{nodeId},#{teacherSummary},#{auditionSummary},#{time},#{auditionResult},#{teacherMatchSuggestion})")
    void saveAuditionFeedbackForm(AuditionFeedbackForm auditionFeedbackForm);

    @Update("update register_form set node_id =#{nodeId},time =#{time},student_name=#{studentName},gender=#{gender},school=#{school},tutorial_subject=#{tutorialSubject},score=#{score},ranking=#{ranking},student_character=#{studentCharacter},habit_attitude=#{habitAttitude}," +
            "family_situation = #{familySituation},learn_responsibility = #{learnResponsibility},remark=#{remark},type =#{type},course_time=#{courseTime},address =#{address},subject = #{subject},later_class= #{laterClass},teacher_gender =#{teacherGender},teacher_character=#{teacherCharacter}," +
            "teacher_other=#{teacherOther},course_consultant=#{courseConsultant} where node_id = #{nodeId} ")
    void editRegisterForm(RegisterForm registerForm);

    @Update("update head_master_examine set node_id = #{nodeId},is_agree=#{isAgree} where node_id =#{nodeId}")
    void editHeadMasterExamine(HeadMasterExamine headMasterExamine);

    @Update("update match_teacher_form set node_id =#{nodeId},teacher_name =#{teacherName},teacher_gender=#{teacherGender},school=#{school},grade_major =#{gradeMajor},contact_model=#{contactModel},wechat_number =#{wechatNumber},email=#{email},emergency_contact1=#{emergencyContact1},emergency_contact2=#{emergencyContact2}" +
            "character_honer_experience_course =#{characterHonerExperienceCourse},near_time_arrange =#{nearTimeArrange},data_is_complete = #{dataIsComplete}, match_score =#{matchScore},toll =#{Toll},distance_time =#{distanceTime},train_time = #{trainTime},teacher_consume_name = #{teacherConsumeName},pdf_address = #{pdfAddress},time =#{time} where node_id =#{nodeId}")
    void editMatchTeacherForm(MatchTeacherForm matchTeacherForm);

    @Update("update train_form set node_id = #{nodeId},get_form_time =#{getFormTime},train_score = #{trainScore},suggestion_evaluation=#{suggestionEvaluation},end_time  = #{endTime}  where node_id =#{nodeId} ")
    void  editTrainForm(TrainForm trainForm);

    @Update("update audition_feedback_form set node_id  = #{nodeId},teacher_summary = #{teacherSummary},audition_summary = #{auditionSummary},time = #{time},audition_result = #{auditionResult},teacher_match_suggestion = #{teacherMatchSuggestion}  where node_id =#{nodeId} ")
    void  editAuditionFeedbackForm(AuditionFeedbackForm auditionFeedbackForm);

    @Update("update accompany_register_form set node_id = #{nodeId}, time = #{time},remark = #{remark},student_name =#{studentName},gender = #{gender}, " +
            " school = #{school},grade = #{grade},study_situation = #{studySituation},student_character = #{studentCharacter},audition_type = #{auditionType}," +
            " time_subject = #{timeSubject},address_num = #{addressNum},expect_teacher = #{expectTeacher},course_consultant =#{courseConsultant}  where node_id =#{nodeId} ")
    void  editAccompanyRegisterForm(AccompanyRegisterForm accompanyRegisterForm);

    @Select("select * from  register_form where node_id =#{nodeId} ")
    RegisterForm getRegisterFormById(Integer nodeId);

    @Select("select * from accompany_register_form where node_id = #{nodeId}")
    AccompanyRegisterForm getAccompanyRegisterFormById(Integer nodeId);

    @Select("select * from  head_master_examine where node_id = #{nodeId} ")
    HeadMasterExamine getHeadMasterExamineById(Integer nodeId);

    @Select("SELECT * from  match_teacher_form  where node_id = #{nodeId} \n" +
            "AND version = (SELECT MAX(version) from  match_teacher_form  where node_id = #{nodeId} ) ")
    MatchTeacherForm getMatchTeacherFormById(Integer nodeId);

    @Select("select * from  train_form where node_id = #{nodeId} ")
    TrainForm getTrainFormById(Integer nodeId);

    @Select("select * from  audition_feedback_form where node_id = #{nodeId} ")
    AuditionFeedbackForm getAuditionFeedbackFormById(Integer nodeId);

    @Select("SELECT * FROM task WHERE   work_flow_id = #{workFlowId} AND task_handler = #{adminId} ")
    Task getTaskByAdminAndWorkFlow(@Param("workFlowId") Integer workFlowId,@Param("adminId")Integer adminId);

    @Insert({"<script>",
            "insert into resource_image(match_teacher_id, image_address) ",
            "values ",
            "<foreach collection='list' item='path' index='index' separator=','>",
            "(#{resourceId}, #{path})",
            "</foreach>",
            "</script>"})
    int saveTeacherConsume(@Param("resourceId") Integer resourceId, @Param("list") List<String> paths);
}

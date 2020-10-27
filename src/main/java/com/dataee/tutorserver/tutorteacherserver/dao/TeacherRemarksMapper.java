package com.dataee.tutorserver.tutorteacherserver.dao;

import com.dataee.tutorserver.entity.RemarkQuestion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/8 23:11
 */
@Mapper
@Repository
public interface TeacherRemarksMapper {
    @Select("select *from teacher_record_question")
    List<RemarkQuestion> getRemarkQuestions();

    @Insert("insert into teacher_record(lesson_id, question_id, answer) values(#{lessonId}, #{questionId}, #{answer})")
    void insertNewRemarks(@Param("lessonId") Integer lessonId, @Param("questionId") Integer questionId, @Param("answer") String answer);

    @Update("update lesson set teacher_record_confirmed = 1 where id = #{lessonId}")
    void changeRecordState(int lessonId);
}

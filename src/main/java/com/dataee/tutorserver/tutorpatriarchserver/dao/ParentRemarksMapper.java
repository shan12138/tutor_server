package com.dataee.tutorserver.tutorpatriarchserver.dao;

import com.dataee.tutorserver.entity.RemarkQuestion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 更新家长评论
 *
 * @author JinYue
 * @CreateDate 2019/6/3 20:17
 */
@Repository
@Mapper
public interface ParentRemarksMapper {
    @Select("select *from parent_record_question")
    List<RemarkQuestion> getRemarkQuestions();

    @Update("update lesson set parent_record_confirmed = 1 where id = #{lessonId}")
    void changeRecordState(int lessonId);

    @Insert("insert into parent_record(lesson_id, question_id, answer) values(#{lessonId}, #{questionId}, #{answer})")
    void insertNewRemarks(@Param("lessonId") int lessonId, @Param("questionId") Integer questionId, @Param("answer") String answer);

    @Insert("insert into lesson_label(lesson_id) values(#{lessonId})")
    void insertLessonLabel(int lessonId);
}

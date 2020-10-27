package com.dataee.tutorserver.tutoradminserver.remarksmanage.dao;

import com.dataee.tutorserver.entity.TeacherLabel;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/8 23:35
 */
@Mapper
@Repository
public interface RemarksManageMapper {
    @Select("select question_id, question, answer from teacher_record, teacher_record_question where teacher_record.question_id = " +
            "teacher_record_question.id and lesson_id = #{lessonId}")
    List<Remarks> getTeacherRecord(Integer lessonId);

    @Update("update teacher_record set answer = #{answer} where lesson_id = #{lessonId} and question_id = #{questionId}")
    void updateTeacherRecord(@Param("lessonId") int lessonId, @Param("answer") String answer, @Param("questionId") Integer questionId);

    @Update("update lesson set teacher_record_confirmed = 2 where id = #{lessonId}")
    Integer changeTeacherRecordState(Integer lessonId);

    @Select("select question, answer from parent_record, parent_record_question where parent_record.question_id = " +
            "parent_record_question.id and lesson_id = #{lessonId}")
    List<Remarks> getParentRecord(Integer lessonId);

    @Select("select teacher_id from lesson where id = #{lessonId}")
    Integer getTeacherId(Integer lessonId);

    @Select("select *from lesson_label where lesson_id = #{lessonId}")
    TeacherLabel getLabel(Integer lessonId);

    @Update("update parent_record set answer = #{answer} where lesson_id = #{lessonId} and question_id = #{questionId}")
    void updateParentRecord(@Param("lessonId") int lessonId, @Param("questionId") Integer questionId, @Param("answer") String answer);

    @Update("update lesson_label set one = #{one}, two = #{two}, three = #{three}, four = #{four}, five = #{five}, six = #{six}, " +
            "seven = #{seven}, eight = #{eight}, nine = #{nine}, ten = #{ten} where lesson_id = #{lessonId}")
    void updateLessonLabel(TeacherLabel teacherLabel);

    @Update("update teacher_label set one_sum = one_sum + #{one}, two_sum = two_sum + #{two}, three_sum = three_sum + #{three}, " +
            " four_sum = four_sum + #{four}, five_sum = five_sum + #{five}, six_sum = six_sum + #{six}, " +
            "seven_sum = seven_sum + #{seven}, eight_sum = eight_sum + #{eight}, nine_sum = nine_sum + #{nine}, " +
            "ten_sum = ten_sum + #{ten}, total_time = total_time + 1 where teacher_id = #{teacherId}")
    void updateTeacherLabel(TeacherLabel teacherLabel);

    @Select("select id from teacher_label where teacher_id = #{teacherId}")
    Integer getTeacherLabelId(int teacherId);

    @Insert("insert into teacher_label(teacher_id) values(#{teacherId})")
    void insertTeacherLabel(Integer teacherId);

    @Update("update lesson set parent_record_confirmed = 2 where id = #{lessonId}")
    void changeParentRecordState(int lessonId);

    @Select("select id from lesson_label where lesson_id = #{lessonId}")
    Integer getLessonLabelId(int lessonId);

    @Insert("insert into lesson_label(lesson_id, one, two, three, four, five, six, seven, eight, nine, ten) " +
            "values(#{lessonId}, #{one}, #{two}, #{three}, #{four}, #{five}, #{six}, #{seven}, #{eight}, #{nine}, #{ten})")
    void insertLessonLabel(TeacherLabel teacherLabel);
}

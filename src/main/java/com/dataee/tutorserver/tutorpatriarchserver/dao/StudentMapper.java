package com.dataee.tutorserver.tutorpatriarchserver.dao;

import com.dataee.tutorserver.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 11:51
 */
@Repository
@Mapper
public interface StudentMapper {

    /**
     * 更新学生的编号
     *
     * @param studentId
     * @param idCard
     * @return
     */
    @Update("update student set id_card = #{idCard} where student_id = #{studentId} and state = 1")
    int updateStudentNumber(@Param("studentId") Integer studentId, @Param("idCard") String idCard);


    @Select("select * from student where student_id=#{studentId}")
    Student getStudentById(@Param("studentId")Integer studentId);


}

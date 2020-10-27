package com.dataee.tutorserver.tutorteacherserver.dao;

import com.dataee.tutorserver.entity.Leisure;
import com.dataee.tutorserver.tutorteacherserver.bean.TeacherOtherInfoStudentCardRequestBean;
import com.dataee.tutorserver.tutorteacherserver.dao.sqlprovider.TeacherInfoSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教师进一步报名
 *
 * @author JinYue
 * @CreateDate 2019/5/10 11:45
 */
@Mapper
@Repository
public interface TeacherSignUpMapper {
    /**
     * 修改教員的学生证信息
     *
     * @param studentCardPicture
     * @param personId
     */
    @UpdateProvider(type = TeacherInfoSqlProvider.class, method = "updateStudentCardByPersonId")
    void updateStudentCardByPersonId(@Param("studentNumber") String studentNumber, @Param("studentCardPicture") String studentCardPicture,
                                     @Param("personId") Integer personId);

    /**
     * 修改教员的支付宝信息
     *
     * @param aliPayAccount
     * @param aliPayPicture
     * @param personId
     */
    @UpdateProvider(type = TeacherInfoSqlProvider.class, method = "updateAliPayByPersonId")
    void updateAliPayByPersonId(@Param("aliPayAccount") String aliPayAccount,
                                @Param("aliPayPicture") String aliPayPicture,
                                @Param("openBankName") String openBankName,
                                @Param("personId") Integer personId);

    /**
     * 修改教员的头像
     *
     * @param address
     * @param personId
     */
    @Update("update teacher set head_picture = #{address} where teacher_id = #{personId} and state != 0")
    void updateHeadPortraitBypPersonId(@Param("address") String address, @Param("personId") Integer personId);

    /**
     * 修改教员的教学经历
     *
     * @param tutorExperience
     * @param personId
     */
    @Update("update teacher set tutor_experience = #{tutorExperience} where teacher_id = #{personId} and state != 0")
    void updateTutorExperienceByPersonId(@Param("tutorExperience") String tutorExperience, @Param("personId") Integer personId);

    /**
     * 修改教员的荣誉信息
     *
     * @param honour
     * @param personId
     */
    @Update("update teacher set honour = #{honour} where teacher_id = #{personId} and state != 0")
    void updateHonourByPersonId(@Param("honour") String honour, @Param("personId") Integer personId);

    /**
     * 修改教员的个人评价
     *
     * @param evaluation
     * @param personId
     */
    @Update("update teacher set evaluation = #{evaluation} where teacher_id = #{personId} and state != 0")
    void updateEvaluationByPersonId(@Param("evaluation") String evaluation, @Param("personId") Integer personId);

    /**
     * 新增教员的空闲时间
     *
     * @param lersureList
     * @param personId
     */
    @Insert({"<script>",
            "insert into leisure(teacher_id, day, morning, noon, evening) values",
            "<foreach collection='list' item='currRow' index='index' separator=','>",
            "(#{personId},#{currRow.day}, #{currRow.morning}, #{currRow.noon}, #{currRow.evening})",
            "</foreach>",
            "</script>"})
    void addLeisureTimeTableByPersonId(@Param("list") List<Leisure> lersureList, @Param("personId") Integer personId);

    /**
     * 删除课余时间
     *
     * @param id
     * @return
     */
    @Update("update leisure set state = 0 from id = #{id}")
    int deleteLeisureTimeTableByPersonId(Integer id);

    /**
     * 获取课余时间的id
     *
     * @param leisure
     * @param teacherId
     * @return
     */
    @Select("select id from leisure where state = 1 and day = #{lei.day} and morning = #{lei.morning} " +
            "and noon = #{lei.noon} and evening=#{lei.evening} and teacher_id = #{teacherId}")
    int queryLeisureId(@Param("lei") Leisure leisure, @Param("teacherId") Integer teacherId);

    /**
     * 修改教员状态为已注册
     *
     * @param personId
     * @return
     */
    @Update("update teacher set state = 2 where state = 1 and  teacher_id = #{personId}")
    int updateTeacherStateToTwo(Integer personId);
}

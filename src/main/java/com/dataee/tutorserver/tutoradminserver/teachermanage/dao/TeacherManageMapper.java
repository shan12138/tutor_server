package com.dataee.tutorserver.tutoradminserver.teachermanage.dao;

import com.dataee.tutorserver.entity.Product;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.ClassAndHour;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherListResponseBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.dao.sqlprovider.QueryTeacherSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 23:29
 */
@Mapper
@Repository
public interface TeacherManageMapper {
    @Select("select *from teacher where teacher_id = #{id}")
    @Results({
            @Result(property = "teachingArea", column = "teacher_id",
                    many = @Many(select = "com.dataee.tutorserver.tutorpatriarchserver.dao.ParentTeacherMapper." +
                            "getTeachingArea")),
            @Result(property = "teacherLabel", column = "teacher_id",
                    many = @Many(select = "com.dataee.tutorserver.tutorteacherserver.dao.TeacherCenterMapper." +
                            "getTeacherLabel")),
            @Result(property = "products", column = "teacher_id",
                    many = @Many(select = "getProductsByTeacherId")),
    })
    Teacher getTeacherDetailInfo(String id);


    @Select("select * from teacher_product INNER JOIN  product ON product.id=teacher_product.product_id where  teacher_id = #{teacherId} AND teacher_product.status=1")
    List<Product> getProductsByTeacherId(Integer teacherId);

    @Insert("insert into teacher_product(teacher_id,product_id) values(#{teacherId},#{productId})")
    void insertTeacherOfProduct(@Param("teacherId") Integer teacherId,@Param("productId")Integer productId);


    @SelectProvider(type = QueryTeacherSqlProvider.class, method = "queryTeacherByCondition2")
    @Results({
            @Result(property = "teacherId", column = "teacher_id"),
            @Result(property = "products", column = "teacher_id",
                    many = @Many(select = "getProductsByTeacherId")),
    })
    List<TeacherListResponseBean> queryTeacher(@Param("queryCondition") String queryCondition, @Param("state") String state, @Param("sex") String sex,@Param("start")Integer start,@Param("end")Integer end);


    @Select("select distinct teacher_id from course where teacher_id = #{teacherId} and state != 0")
    String getTeacherIdOfCourse(String teacherId);


    @Update("update teacher set remark = #{platformIntroduce} where teacher_id = #{teacherId}")
    void changePlatformIntroduce(@Param("platformIntroduce") String platformIntroduce, @Param("teacherId") Integer teacherId);

    @Select("select teacher_id, teacher_name, telephone, sex, state from teacher where state = 4 or state = 0 " +
            "order by teacher_id desc")
    List<TeacherListResponseBean> getTeacherAuthEdList();


    /**
     * 非正式教员检索
     * @param queryCondition
     * @param state
     * @param sex
     * @return
     */
    @SelectProvider(type = QueryTeacherSqlProvider.class, method = "queryTeacherByCondition")
    List<TeacherListResponseBean> getTeacherAuthIngList(@Param("queryCondition") String queryCondition,
                                                        @Param("state") String state,
                                                        @Param("sex") String sex);


    @Update("update teaching_area set state = 0 where teacher_id = #{teacherId}")
    void changeTeachingAreaState(Integer teacherId);


    @Insert("insert into teaching_area(teacher_id, grade, subject) values(#{teacherId}, #{grade}, #{subject})")
    void insertTeachingArea(@Param("teacherId") Integer teacherId, @Param("grade") String grade, @Param("subject") String subject);


    @Update("update teacher set interview_result = #{interviewResult} where teacher_id = #{teacherId}")
    void changeInterviewResult(@Param("teacherId") Integer teacherId, @Param("interviewResult") String interviewResult);

    @Update("update teacher_product set status = 0 where teacher_id = #{teacherId}")
    void changeTeacherProduct(@Param("teacherId") Integer teacherId);

    @Select("select * from teacher  where teacher_id = #{id}")
    Teacher getTeacherById(@Param("id") Integer id);





}

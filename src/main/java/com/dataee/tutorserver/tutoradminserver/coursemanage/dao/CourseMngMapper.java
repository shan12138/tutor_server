package com.dataee.tutorserver.tutoradminserver.coursemanage.dao;

import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.*;
import com.dataee.tutorserver.tutoradminserver.coursemanage.dao.sqlprovider.QueryCourseSqlProvider;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ParentContractRequestBean;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherListResponseBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 杨少聪
 * @Date 2019/5/17
 * @Description: com.dataee.tutorserver.tutoradminserver.coursemanage.dao
 */

@Mapper
@Repository
public interface CourseMngMapper {
    @Select("select course_name, address_id, course_id, parent_id, student_id, teacher_id,product_id,head_admin_id,course_admin_id" +
            " from course where state != 0 and course_id = #{courseId}")
    @Results(id = "courseMap", value = {
            @Result(property = "courseHourRecord", column = "course_id",
                    many = @Many(select = "com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCourseMapper." +
                            "getCourseHourRecordOfCourse")),
            @Result(property = "address", column = "address_id",
                    one = @One(select = "com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCourseMapper." +
                            "getAddressOfCourse")),
            @Result(property = "student", column = "student_id",
                    one = @One(select = "com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCenterMapper." +
                            "getChildDetailInfo")),
            @Result(property = "teacher", column = "teacher_id",
                    one = @One(select = "com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCourseMapper." +
                            "getTeacherName")),
            @Result(property = "parent", column = "parent_id",
                    one = @One(select = "com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCenterMapper." +
                            "getParentInfo")),
            @Result(property = "product", column = "product_id",
                    one = @One(select = "com.dataee.tutorserver.tutoradminserver.productmanage.dao.ProductManageMapper.getProductById")),
            @Result(column = "head_admin_id", property = "headAdmin",
                    one = @One(select = "getAdmin")),
            @Result(column = "course_admin_id", property = "courseAdmin",
                    one = @One(select = "getAdmin"))
    })
    Course getCourseDetailInfo(@Param("courseId") String courseId);


    @Select("select id, check_in_time, check_out_time, course_time, lesson_number, time," +
            " teacher_record_confirmed, parent_record_confirmed," +
            " teacher_name, class_time, teacher.teacher_id from lesson, teacher where teacher.teacher_id = " +
            "lesson.teacher_id and course_id = #{courseId} and lesson.state = 1 and teacher.state = 4" +
            " order by lesson_number desc")
    List<Lesson> getLessonDetailInfo(String courseId);

    @Select("select upload_state from resource_pdf, lesson where lesson.id = resource_pdf.lesson_id " +
            "and resource_pdf.state = 1 and lesson.id = #{lessonId}")
    Integer getRescourcePdfUploadState(int lessonId);

    @Select("SELECT id, course_id, subject, grade, course_name, teacher_id, parent_id, student_id, head_admin_id, " +
            "course_admin_id, study_admin_id, state FROM course where state != 0")
    @Results(
            id = "getCourseList",
            value = {
                    @Result(column = "course_id", property = "courseId"),
                    @Result(column = "subject", property = "subject"),
                    @Result(column = "grade", property = "grade"),
                    @Result(column = "course_name", property = "courseName"),
                    @Result(column = "teacher_id", property = "teacher",
                            one = @One(select = "getTeacher")),
                    @Result(column = "parent_id", property = "parent",
                            one = @One(select = "getParent")),
                    @Result(column = "student_id", property = "student",
                            one = @One(select = "getStudent")),
                    @Result(column = "head_admin_id", property = "headAdmin",
                            one = @One(select = "getAdmin")),
                    @Result(column = "course_admin_id", property = "courseAdmin",
                            one = @One(select = "getAdmin")),
                    @Result(column = "study_admin_id", property = "studyAdmin",
                            one = @One(select = "getAdmin")),
            }
    )
    List<CourseListResponseBean> getCourseList();

    @Select("select teacher_name, teacher_id, telephone from teacher where teacher_id = #{teacherId} and state != 0")
    Teacher getTeacher(@Param("teacherId") int teacher_id);

    @Select("select * from parent where parent_id = #{parentId} and state != 0")
    Parent getParent(@Param("parentId") int parent_id);

    @Select("select student_name from student where student_id = #{studentId} and state = 1")
    Student getStudent(@Param("studentId") int student_id);

    @Select("select admin_name, id, account from admin where id = #{id}")
    Administrator getAdmin(@Param("id") int id);

    @Update("update lesson set check_in_time = #{checkInTime}, check_out_time = #{checkOutTime}, " +
            "class_time = #{classTime} where id = #{id} and state = 1")
    int changeAttendanceRecord(Lesson lesson);


    @Update("update course_hour_record set consume_class_hour = #{consumeClassHour} where id = #{id} and state = 1")
    void changeCourseHourRecord(CourseHourRecord courseHourRecord);


    @Select("select rest_class_hour from course_hour_record where id = #{courseHourRecordId} and state = 1")
    Double getRestClassHour(Integer courseHourRecordId);

    @SelectProvider(type = QueryCourseSqlProvider.class, method = "getQueryCourseSql")
    List<CourseListResponseBean> queryCourse(@Param("studentName") String studentName,
                                             @Param("grade") String grade,@Param("subject")String subject, @Param("teacher") String teacher,
                                             @Param("headTeacher") String headTeacher,@Param("productId")Integer productId);

    @Select("select teacher_id, teacher_name from teacher where state = 4")
    List<Teacher> getAllTeacher();

    @Update("update course set teacher_id = #{teacherId}, state = 2 where id = #{id}")
    void setTeacherForCourse(@Param("id") Integer id, @Param("teacherId") Integer teacherId);

    @Select("select max(course_id) from course where state != 0")
    Integer getMaxCourseId();

    @Insert("insert into course(course_id, subject, grade, parent_id, student_id, address_id, head_admin_id, " +
            "course_admin_id,product_id) values(#{courseId}, #{subject}, #{grade}, #{parentId}, " +
            "#{studentId}, #{addressId}, #{headAdminId}, #{courseAdminId}, #{productId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createCourse(CreateCourseRequestBean course);

    @Update("update course set course_name = #{courseName} where id = #{id}")
    int setCourseName(@Param("id") Integer id, @Param("courseName") String courseName);

    @Select("select state from course where state = 1 and id = #{id}")
    Integer getState(Integer id);

    @Insert("insert into temp_speaking(course_id, teacher_id) values(#{id}, #{teacherId}) ")
    int setTempSpeakingTeacher(@Param("id") Integer id, @Param("teacherId") Integer teacherId);

    @Select("select id from temp_speaking where course_id = #{id} and teacher_id = #{teacherId} and state = 1")
    Integer getTempSpeakingId(@Param("id") Integer id, @Param("teacherId") Integer teacherId);

    @Update("update ${table} set state = 0 where id = #{id}")
    int changeTableState(@Param("table") String table, @Param("id") Integer id);

    @Select("select * from course where id = #{id}")
    CourseSqlEntity selectCourse(Integer id);

    @Insert("insert into course(course_id, address_id, subject, grade, course_name, teacher_id, parent_id, " +
            "student_id, class_times, state, head_admin_id, course_admin_id, study_admin_id,product_id" +
            ") values(#{courseId}, #{addressId}, #{subject}, #{grade}, " +
            "#{courseName}, #{teacherId}, #{parentId}, #{studentId}, 0, 2, " +
            "#{headAdminId}, #{courseAdminId}, #{studyAdminId}, #{productId})")
    int insertNewCourse(CourseSqlEntity course);

    @Select("select * from student where student_id = #{studentId}")
    Student getStudentByStudentId(Integer studentId);

    @Select("select id from course_hour_record where course_id = #{courseId} and state = 1")
    List<Integer> getCourseHourRecordId(Integer courseId);

    @Select("select *from course_hour_record where id = #{id}")
    CourseHourRecordSqlEntity selectCourseHourRecord(Integer id);

    @Insert("insert into course_hour_record(course_id, total_class_hour, consume_class_hour,is_free ,price,discount,createdAt) values(#{courseId}, #{totalClassHour}, 0,#{isFree}, #{price} ,#{discount},#{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertNewCourseHourRecord(CourseHourRecordSqlEntity courseHourRecord);

    @Select("SELECT * from course_hour_record where course_id = #{courseId} and state = 1")
    List<CourseHourRecord>  getAllCourseHourRecordByCourseId(Integer courseId);


    @Select("select id from course where course_id = #{courseId} and state = #{state}")
    int getCourseId(@Param("courseId") Integer courseId, @Param("state") Integer state);

    @Select("select id from course where subject = #{subject} and grade = #{grade}" +
            " and parent_id = #{parentId} and student_id = #{studentId} and state != 0")
    Integer getCourse(CreateCourseRequestBean course);


    @Select("select address.* from address where address.state = 1 and parent_id =#{parentId}")
    List<Address> getParentAddressList(Integer parentId);

    @Select("select student_id, student_name from student where parent_id = #{parentId} and state = 1")
    List<Student> getParentChildList(Integer parentId);

    @Update("update course set course_hour_record_id = #{id1} where id = #{id}")
    Integer setCourseHourRecordId(@Param("id") Integer id, @Param("id1") Integer id1);

    @Select("select grade from course where course_id = #{courseId} and state != 0")
    String getGrade(Integer courseId);

    @Select("select subject from course where course_id = #{courseId} and state != 0")
    String getSubject(Integer courseId);

    @Select("select teacher_name from teacher where teacher_id = #{teacherId} and state != 0")
    String getTeacherName(Integer teacherId);

    @Select("select teacher_id from course where state = #{state} and course_id = #{courseId}")
    Integer queryTeacherIdByCourseId(@Param("state") Integer state, @Param("courseId") Integer courseId);

    @Select("select course_id, course_name, id from course where teacher_id = #{teacherId} and state != 0")
    List<Course> getTeacherCourse(Integer teacherId);

    @Select("select teacher_id, teacher_name from teacher where teacher_id in (select teacher_id from " +
            "course where state != 0) and state=4")
    List<Teacher> getHasCourseTeacher();

    @SelectProvider(type = QueryCourseSqlProvider.class, method = "getTempNotSpeakingTeacher")
    List<TeacherListResponseBean> getTempNotSpeakingTeacher(@Param("courseId")Integer id,@Param("queryCondition")String queryCondition,@Param("grade")String grade,
                                                            @Param("subject")String subject,@Param("sex")String sex,@Param("state")String state);


    @SelectProvider(type = QueryCourseSqlProvider.class, method = "getTempSpeakingTeacher")
    List<TeacherListResponseBean> getTempSpeakingTeacher(@Param("courseId")Integer courseId,@Param("queryCondition")String queryCondition,@Param("grade")String grade,
                                                         @Param("subject")String subject,@Param("sex")String sex,@Param("state")String state);

    @Select("select teacher_id, teacher_name, telephone, sex, 1 as tempSpeaking from teacher " +
            "where state = 4 and teacher_id in( " +
            "select teacher_id from teaching_area where grade = #{grade} and subject = #{subject} and state = 1 " +
            "and teacher_id in (" +
            "select teacher_id from temp_speaking where state = 1 and course_id = #{courseId}))")
    List<TeacherListResponseBean> getTeacherSpeakingProper(@Param("grade") String grade, @Param("subject") String subject,
                                                           @Param("courseId") Integer id);

    @Select("select teacher_id, teacher_name, telephone, sex, 0 as tempSpeaking from teacher " +
            "where state = 4 and teacher_id in( " +
            "select teacher_id from teaching_area where grade = #{grade} and subject = #{subject} and state = 1" +
            " and teacher_id not in (" +
            "select teacher_id from temp_speaking where state = 1 and course_id = #{courseId}))")
    List<TeacherListResponseBean> getTeacherNotSpeakingProper(@Param("grade") String grade, @Param("subject") String subject,
                                                              @Param("courseId") Integer id);

    @Select("select state from course where course_id = #{courseId} and state != 0")
    int getCourseState(Integer courseId);

    @Select("select course_name from course where course_id = #{courseId} and state != 0")
    String getCourseName(Integer courseId);

    @Select("select parent_id from course where course_id = #{courseId} and state != 0")
    Integer getCourseParentId(Integer courseId);

    @Select("select parent_id from course where course_id = #{courseId} and state != 0")
    Integer getParentId(Integer courseId);

    @Select("select teacher_id from course where course_id = #{courseId} and state != 0")
    Integer getTeacherId(Integer courseId);

    @Select("select id, admin_name from admin, (select admin_id, admin_auth.role_id, " +
            "role_name from admin_role, admin_auth where admin_role.role_id = admin_auth.role_id) as temp1 " +
            "where admin.admin_id = temp1.admin_id and role_name = #{roleName}")
    List<Administrator> getAdminList(String roleName);
    @SelectProvider(type = QueryCourseSqlProvider.class, method = "getQueryCourseSql1")
    @Results(
            id = "queryCurrentCourseByWeek",
            value = {
                    @Result(column = "teacher_id", property = "teacher",
                            one = @One(select = "getTeacher")),
                    @Result(column = "student_id", property = "student",
                            one = @One(select = "getStudent")),
                    @Result(column = "head_admin_id", property = "headAdmin",
                            one = @One(select = "getAdmin")),
                    @Result(column = "course_admin_id", property = "courseAdmin",
                            one = @One(select = "getAdmin")),
                    @Result(column = "study_admin_id", property = "studyAdmin",
                            one = @One(select = "getAdmin")),
            }
    )
    List<CurrentCourseResponseBean> queryCurrentCourseByWeek(@Param("week") Integer week,@Param("studentName")String studentName,@Param("teacher") String teacher,@Param("grade") String grade,@Param("subject") String subject,@Param("headTeacher") String headTeacher,@Param("startTime") String startTime,@Param("endTime") String endTime);

    @Insert("insert into address_course (parent_id,region,address_detail)values (#{parentId},#{region},#{addressDetail})")
    @Options(useGeneratedKeys = true,keyProperty ="addressId",keyColumn = "address_id")
    int addCourseAddress(CourseAddress courseAddress);

    @Select("select parent_id, partner_state from parent where invited_parent_id = #{parentId}")
    ParentInvitation getParentInvitation(Integer parentId);

    @Update("update parent set partner_state = #{state}, invite_success_time" +
            " = now(), parent_state = #{parentState} where parent_id = #{parentId}")
    void updatePartnerState(@Param("state") String state, @Param("parentId") Integer parentId,
                            @Param("parentState") String parentState);

    @Select("select id from user  where username  = #{telephone}  AND  role_id = 40")
    Integer getPersonId(String telephone );

    @Insert("insert into parent_contract (sn,idCardNumber,b_name,b_sex,grade,school,partyAgentName,telephone,contactAddress,subject_name,product_name,otherSubject,totalCourseHour,price,totalMoney,upper_total_money,party_agent_idCard,startYear,startMonth,startDay,endYear,endMonth,endDay,isSign) values" +
            "(#{sn},#{idCardNumber},#{bName},#{bSex},#{grade},#{school},#{partyAgentName},#{telephone},#{contactAddress},#{subjectName},#{productName},#{otherSubject},#{totalCourseHour},#{price},#{totalMoney},#{upperTotalMoney},#{partyAgentIdCard},#{startYear},#{startMonth},#{startDay},#{endYear},#{endMonth},#{endDay},0)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void  saveContract(ParentContract parentContract);

    @Update("update contract_pdf set pdf_address = #{contractAddress} where contract_id = #{contractId}")
    void editContractPdf(ParentContractRequestBean parentContractRequestBean);

    @Select("select count(1) from parent_contract")
    Integer getContractSn();
}
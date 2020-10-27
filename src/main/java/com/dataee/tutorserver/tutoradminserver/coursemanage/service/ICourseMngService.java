package com.dataee.tutorserver.tutoradminserver.coursemanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.coursemanage.bean.*;

import java.util.List;

/**
 * @Author 杨少聪
 * @Date 2019/5/17
 * @Description: com.dataee.tutorserver.tutoradminserver.coursemanage.service
 */
public interface ICourseMngService {

    NewPageInfo getCourseList(Page page);

    void changeAttendanceRecord(Lesson lesson, double hour, Integer courseHourRecordId) throws BaseServiceException;

    NewPageInfo queryCourse(String studentName, String grade,String  subject, String teacher, String headTeacher,Integer productId, Page page);

    List<Teacher> getAllTeacher();

    void setTeacherForCourse(Integer courseId, Integer teacherId);

    NewPageInfo getTempTeacher(Integer courseId, Integer flag, Page page,String queryCondition,String grade,String subject,String sex,String state1) throws BaseServiceException;

    void createCourse(CreateCourseRequestBean course) throws BaseServiceException;

    void deleteCourse(Integer id) throws BaseServiceException;

    void setTempSpeakingTeacher(Integer courseId, Integer teacherId) throws BaseServiceException;

    void cancleTempSpeakingTeacher(Integer courseId, Integer teacherId);

    void changeTeacherForCourse(Integer courseId, Integer teacherId);

    List<Address> getParentAddressList(Integer parentId);

    List<Student> getParentChildList(Integer parentId);

    Integer getTeacherIdByCourseId(Integer state, Integer courseId);

    Course getCourseDetailInfo(String courseId);

    List<Course> getTeacherCourse(Integer teacherId);

    List<Teacher> getHasCourseTeacher();

    NewPageInfo getLessonDetailInfo(String courseId, Page page);

    List<Administrator> getAdminList(String roleName);

    /**
     * 获取当周课程
     *
     * @param page
     * @return
     */
    NewPageInfo<CurrentCourseResponseBean> getCurrCourseList(Page page,String studentName,String teacher,String grade,String subject,String headTeacher,String startTime,String endTime);

    NewPageInfo<CourseHourRecord>  getAllCourseHourRecordByCourseId(Integer courseId,Page page);

    CourseHourRecordDetail getTotalCourseHour(Integer courseId);

    void   addCourseHour(AddCourseHour addCourseHour) throws BaseServiceException;

    void giveFreeCourseHour(GiveFreeCourseHour giveFreeCourseHour)throws BaseServiceException;
}

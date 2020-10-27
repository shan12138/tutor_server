package com.dataee.tutorserver.tutorpatriarchserver.service.impl;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Bill;
import com.dataee.tutorserver.entity.Course;
import com.dataee.tutorserver.entity.CourseHourRecord;
import com.dataee.tutorserver.entity.Lesson;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ClassHourDetail;
import com.dataee.tutorserver.tutorpatriarchserver.dao.ParentCourseMapper;
import com.dataee.tutorserver.tutorpatriarchserver.service.IParentCourseService;
import com.dataee.tutorserver.tutorteacherserver.bean.ScheduleBean;
import com.dataee.tutorserver.tutorteacherserver.dao.TeacherCourseMapper;
import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;
import com.dataee.tutorserver.userserver.dao.CourseMapper;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/6 15:42
 */
@Service
public class ParentCourseServiceImpl implements IParentCourseService {
    @Autowired
    private ParentCourseMapper parentCourseMapper;
    @Autowired
    private TeacherCourseMapper teacherCourseMapper;


    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Course getCourseDetailInfo(String courseId) {
        return parentCourseMapper.getCourseDetailInfo(courseId);
    }

    @Override
    public List<ScheduleBean> getSchedule(Integer personId, Integer week, String year) {
        return parentCourseMapper.getSchedule(personId, week, year);
    }

    @Override
    public NewPageInfo getLessonDetailInfo(String courseId, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Lesson> lessons = parentCourseMapper.getLessonDetailInfo(courseId);
        for (Lesson lesson : lessons) {
            Integer resourceNum = teacherCourseMapper.getResourceNum(lesson.getId());
            if (resourceNum != 0) {
                resourceNum = 1;
            }
            lesson.setResourceNum(resourceNum);
        }
        PageInfo pageInfo = new PageInfo(lessons);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public void changeCourseAddress(Integer addressId, Integer courseId) {
        parentCourseMapper.changeCourseAddress(addressId, courseId);
    }

    @Override
    public Lesson getLessonById(Integer id) {
      Lesson lesson=  parentCourseMapper.getLessonById(id);
      Integer resourceNum = teacherCourseMapper.getResourceNum(lesson.getId());
      if (resourceNum != 0) {
            resourceNum = 1;
      }
      lesson.setResourceNum(resourceNum);
        return lesson;
    }

    @Override
    public List<GetCourseTeacherListResponseBean> getCourseList(String tableId, Integer id) {
        return courseMapper.getCourseList(tableId,id);
    }

    @Override
    public List<Lesson> getLessonsByCourseId(Integer courseId) {
        return parentCourseMapper.getLessonsByCourseId(courseId);
    }

    @Override
    public NewPageInfo getParentConsume(Integer courseId,Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
//        List<Lesson> lessons = parentCourseMapper.getLessonsByCourseId(courseId);
//        List<ParentBill> parentBillList =new ArrayList<>();
//        for (Lesson lesson:lessons){
//           ParentBill parentBill = parentCourseMapper.getParentConsumeByCourseId(lesson.getId());
//           if(parentBill!=null){
//               parentBill.setClassTime(lesson.getClassTime());
//               parentBill.setRemarkCheckInTime(lesson.getRemarkCheckInTime());
//               parentBill.setRemarkCheckOutTime(lesson.getRemarkCheckOutTime());
//               parentBillList.add(parentBill);
//           }
//        }
//        PageInfo pageInfo = new PageInfo(parentBillList);
        List<Bill> bills = teacherCourseMapper.getBills(courseId);
        PageInfo pageInfo = new PageInfo(bills);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }
}

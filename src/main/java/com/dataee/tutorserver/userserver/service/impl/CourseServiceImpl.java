package com.dataee.tutorserver.userserver.service.impl;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.entity.Grade;
import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.entity.Subject;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao.ParentManageMapper;
import com.dataee.tutorserver.userserver.bean.GetCourseTeacherListResponseBean;
import com.dataee.tutorserver.userserver.bean.GetTodayCourseResponseBean;
import com.dataee.tutorserver.userserver.dao.CourseMapper;
import com.dataee.tutorserver.userserver.service.ICourseService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/4/27 11:29
 */
@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ParentManageMapper parentManageMapper;


    @Override
    public List<Grade> getGrade() {
        return courseMapper.getGrade();
    }

    @Override
    public List<Subject> getSubject() {
        return courseMapper.getSubject();
    }

    @Override
    public NewPageInfo<GetCourseTeacherListResponseBean> getCourseList(String tableId, Integer id, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<GetCourseTeacherListResponseBean> courseTeacherListResponseBeanList = courseMapper.getCourseList(tableId, id);
        PageInfo pageInfo = new PageInfo(courseTeacherListResponseBeanList);
        NewPageInfo<GetCourseTeacherListResponseBean> newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public List<GetTodayCourseResponseBean> getTeacherTodayCourse(String userId) {
        return courseMapper.getTeacherTodayCourse(userId);
    }

    @Override
    public List<GetTodayCourseResponseBean> getParentTodayCourse(String userId) {
        return courseMapper.getParentTodayCourse(userId);
    }

    @Override
    public List<Parent> getParentList(int state) {
        List<Parent> parents = parentManageMapper.getParentList(state);
        return parents;
    }
}

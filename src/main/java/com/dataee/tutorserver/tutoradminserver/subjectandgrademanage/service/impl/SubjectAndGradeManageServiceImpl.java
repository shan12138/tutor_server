package com.dataee.tutorserver.tutoradminserver.subjectandgrademanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.Grade;
import com.dataee.tutorserver.entity.Subject;
import com.dataee.tutorserver.tutoradminserver.subjectandgrademanage.dao.SubjectAndGradeManageMapper;
import com.dataee.tutorserver.tutoradminserver.subjectandgrademanage.service.ISubjectAndGradeManageService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/7 23:08
 */
@Service
public class SubjectAndGradeManageServiceImpl implements ISubjectAndGradeManageService {
    @Autowired
    private SubjectAndGradeManageMapper subjectAndGradeManageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeData(String table, Integer id, Integer priority) throws BaseServiceException {
        int number = subjectAndGradeManageMapper.changeData(table, id, priority);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addData(String table, String name, Integer priority) throws BaseServiceException {
        try {
            int number = subjectAndGradeManageMapper.addData(table, name, priority);
            if (number != 1) {
                throw new SQLOperationException();
            }
        } catch (DuplicateKeyException e) {
            throw new BaseServiceException(ServiceExceptionsEnum.DATA_EXIST);
        }
    }

    @Override
    public NewPageInfo getAllSubject(Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Subject> subjects = subjectAndGradeManageMapper.getSubject();
        PageInfo pageInfo = new PageInfo(subjects);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public NewPageInfo getAllGrade(Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Grade> grades = subjectAndGradeManageMapper.getGrade();
        PageInfo pageInfo = new PageInfo(grades);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }
}

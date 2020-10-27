package com.dataee.tutorserver.tutoradminserver.levelmanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.entity.ParentLevel;
import com.dataee.tutorserver.entity.Product;
import com.dataee.tutorserver.entity.Teacher;
import com.dataee.tutorserver.entity.TeacherLevel;
import com.dataee.tutorserver.tutoradminserver.levelmanage.dao.LevelManageMapper;
import com.dataee.tutorserver.tutoradminserver.levelmanage.service.ILevelManageService;
import com.dataee.tutorserver.tutoradminserver.productmanage.dao.ProductManageMapper;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/5 11:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LevelManageServiceImpl implements ILevelManageService {
    @Autowired
    private LevelManageMapper levelManageMapper;
    @Autowired
    private ProductManageMapper productManageMapper;

    @Override
    public NewPageInfo getParentLevelList(Page page, int id) throws BaseServiceException {
        Product product = productManageMapper.getProductById(id);
        if(product == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.PRODUCT_NOT_FOUND);
        }
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<ParentLevel> parentLevel = levelManageMapper.getParentLevelList(id);
        PageInfo pageInfo = new PageInfo(parentLevel);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public void createParentLevel(int id, ParentLevel parentLevel) throws BaseServiceException {
        Product product = productManageMapper.getProductById(id);
        if(product == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.PRODUCT_NOT_FOUND);
        }
        Integer maxLevel = levelManageMapper.getMaxLevel(id);
        if(maxLevel == null) {
            parentLevel.setLevel(1);
        }
        else {
            parentLevel.setLevel(maxLevel + 1);
        }
        parentLevel.setProductId(id);
        levelManageMapper.createParentLevel(parentLevel);
    }

    @Override
    public void updateParentLevel(ParentLevel parentLevel) throws BaseServiceException {
        ParentLevel parentLevel1 = levelManageMapper.getParentLevelById(parentLevel.getId());
        if(parentLevel1 == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.PARENT_LEVEL_NOT_FOUND);
        }
        levelManageMapper.updateParentLevel(parentLevel);
    }

    @Override
    public void deleteParentLevel(int id) throws BaseServiceException {
        ParentLevel parentLevel = levelManageMapper.getParentLevelById(id);
        if(parentLevel == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.PARENT_LEVEL_NOT_FOUND);
        }
        Integer maxLevel = levelManageMapper.getMaxLevel(parentLevel.getProductId());
        if(maxLevel != parentLevel.getLevel()) {
            throw new BaseServiceException(ServiceExceptionsEnum.CANT_DELETE_PARENT_LEVEL);
        }
        levelManageMapper.deleteParentLevel(id);
    }

    @Override
    public NewPageInfo getTeacherLevelList(Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<TeacherLevel> teacherLevel = levelManageMapper.getTeacherLevelList();
        PageInfo pageInfo = new PageInfo(teacherLevel);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }

    @Override
    public void createTeacherLevel(TeacherLevel teacherLevel) {
        Integer maxLevel = levelManageMapper.getTeacherMaxLevel();
       if(maxLevel == null) {
           maxLevel = 1;
       }
       else {
           maxLevel++;
       }
       teacherLevel.setLevel(maxLevel);
       levelManageMapper.createTeacherLevel(teacherLevel);
    }

    @Override
    public void updateTeacherLevel(TeacherLevel teacherLevel) throws BaseServiceException {
        TeacherLevel teacherLevel1 = levelManageMapper.getTeacherLevelById(teacherLevel.getId());
        if(teacherLevel1 == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.TEACHER_LEVEL_NOT_FOUND);
        }
        levelManageMapper.updateTeacherLevel(teacherLevel);
    }

    @Override
    public void deleteTeacherLevel(int id) throws BaseServiceException {
        TeacherLevel teacherLevel = levelManageMapper.getTeacherLevelById(id);
        if(teacherLevel == null) {
            throw new BaseServiceException(ServiceExceptionsEnum.TEACHER_LEVEL_NOT_FOUND);
        }
        Integer maxLevel = levelManageMapper.getTeacherMaxLevel();
        if(maxLevel != teacherLevel.getLevel()) {
            throw new BaseServiceException(ServiceExceptionsEnum.CANT_DELETE_PARENT_LEVEL);
        }
        levelManageMapper.deleteTeacherLevel(id);
    }
}

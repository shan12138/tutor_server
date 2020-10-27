package com.dataee.tutorserver.tutoradminserver.departmentmanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.entity.Department;
import com.dataee.tutorserver.tutoradminserver.departmentmanage.dao.DepartmentManageMapper;
import com.dataee.tutorserver.tutoradminserver.departmentmanage.service.IDepartmentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/25 21:00
 */
@Service
public class DepartmentManageServiceImpl implements IDepartmentManageService {
    @Autowired
    private DepartmentManageMapper departmentManageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Department createDepartment(String name, Integer parentId) throws BaseServiceException {
        Integer number = departmentManageMapper.getDepartment(name, parentId);
        if (number == null) {
            Department department = new Department(name, parentId, 1);
            departmentManageMapper.createDepartment(department);
            return department;
        } else {
            throw new BaseServiceException(ServiceExceptionsEnum.DATA_EXIST);
        }

    }

    @Override
    public List<Department> getDepartment() {
        return departmentManageMapper.getAllDepartment();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDepartment(Integer id) throws BaseServiceException {
        Integer count = departmentManageMapper.isDepartmentLeaf(id);
        if (count != 0) {
            throw new BaseServiceException(ServiceExceptionsEnum.CANT_DELETE_USING);
        } else {
            Integer parentId = departmentManageMapper.getParentId(id);
            if (parentId == 0) {
                throw new BaseServiceException(ServiceExceptionsEnum.CANT_DELETE_ROOT);
            }
            departmentManageMapper.deleteDepartment(id);
        }
    }

    @Override
    public Department changeDepartment(String name, Integer id) {
        departmentManageMapper.changeDepartment(name, id);
        return departmentManageMapper.getDepartmentById(id);
    }
}

package com.dataee.tutorserver.tutoradminserver.departmentmanage.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.entity.Department;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/25 21:12
 */
public interface IDepartmentManageService {
    Department createDepartment(String name, Integer parentId) throws BaseServiceException;

    List<Department> getDepartment();

    void deleteDepartment(Integer id) throws BaseServiceException;

    Department changeDepartment(String name, Integer id);
}

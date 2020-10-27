package com.dataee.tutorserver.userserver.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.userserver.bean.UserBean;
import com.dataee.tutorserver.userserver.dao.PersonMapper;
import com.dataee.tutorserver.userserver.service.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JinYue
 * @CreateDate 2019/5/14 1:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PersonServiceImpl implements IPersonService {
    private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
    @Autowired
    PersonMapper personMapper;
    private final String PARENT_ROLE = "parent";

    @Override
    public Integer getPersonState(Integer personId, String role) throws BaseServiceException {
        Integer state = personMapper.queryPersonStateByPersonIdAndRole(personId, role);
        if (state == null) {
            throw new SQLOperationException();
        }
        if (PARENT_ROLE.equals(role) && state == 3) {
            state += 1;
        }
        return state;
    }

    @Override
    public Boolean findPerson(Integer personId, String role) {
        Integer id = personMapper.queryPersonByPersonIdAndRole(personId, role);
        if (id != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getPersonName(String tableName, Integer personId) {
        String personName = personMapper.queryPersonNameById(personId, tableName);
        return personName;
    }

    @Override
    public UserBean getUser(Integer personId, String role) {
        if (role.equals("teacher")) {
            return personMapper.getUser(personId, "teacher", "teacher_id", "teacher_name");
        } else if (role.equals("parent")) {
            return personMapper.getUser(personId, "parent", "parent_id", "parent_name");
        }
        return null;
    }
}

package com.dataee.tutorserver.tutorteacherserver.service.impl;

import com.dataee.tutorserver.entity.Leisure;
import com.dataee.tutorserver.tutorteacherserver.dao.LeisureMapper;
import com.dataee.tutorserver.tutorteacherserver.service.ILeisureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/24 9:08
 */
@Service
public class LeisureServiceImpl implements ILeisureService {
    private final Logger logger = LoggerFactory.getLogger(LeisureServiceImpl.class);

    @Autowired
    private LeisureMapper leisureMapper;


    @Override
    public List<Leisure> getLeisureById(@NotNull Integer teacherId) {
        List<Leisure> leisureList = leisureMapper.queryLeisureByteacherId(teacherId);
        return leisureList;
    }

    @Override
    public Integer getTeacherOfCourse(Integer courseId) {
        return leisureMapper.getTeacherIdOfCourse(courseId);
    }
}

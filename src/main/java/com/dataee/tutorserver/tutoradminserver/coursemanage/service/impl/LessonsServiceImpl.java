package com.dataee.tutorserver.tutoradminserver.coursemanage.service.impl;

import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.tutoradminserver.coursemanage.dao.LessonsMapper;
import com.dataee.tutorserver.tutoradminserver.coursemanage.dao.SaveScheduleMngMapper;
import com.dataee.tutorserver.tutoradminserver.coursemanage.service.ILessonsService;
import com.dataee.tutorserver.utils.TimeWeekUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/27 22:32
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LessonsServiceImpl implements ILessonsService {
    private final Logger logger = LoggerFactory.getLogger(LessonsServiceImpl.class);

    @Autowired
    private SaveScheduleMngMapper saveScheduleMngMapper;
    @Autowired
    private LessonsMapper lessonsMapper;


    @Override
    public void deleteLessons(@NotNull List<Integer> weekLessonIdList) throws SQLOperationException {
        if (weekLessonIdList == null || weekLessonIdList.size() == 0) {
            return;
        }
        for (Integer weekLessonId : weekLessonIdList) {
            logger.debug("------------------weekLessonId---------------------:{}", weekLessonId);
            lessonsMapper.deleteLessonsByWeekLessonId(weekLessonId);
        }
    }

    @Override
    public List<Integer> getWeekLessonIds(Integer week, Integer courseId, Integer teacherId, Integer year) {
        List<Integer> weekLessonIdList = lessonsMapper.queryWeekLessonIdListAfterWeek(week, courseId, teacherId, year);
        return weekLessonIdList;
    }

    @Override
    public Integer getWeekOfYearId(Integer week, Integer courseId, Integer teacherId, Integer year) {
        Integer weekLessonId = saveScheduleMngMapper.queryWeekBYWeek(week, courseId, teacherId, year);
        return weekLessonId;
    }

    @Override
    public void deleteLessons(Integer weekLessonId, Integer dayOfWeek) throws SQLOperationException {
        int count = lessonsMapper.deleteLessonsAfterDay(weekLessonId, dayOfWeek);
    }


    @Override
    public void deleteWeekLessons(List<Integer> weekLessonsIdList) throws SQLOperationException {
        if (weekLessonsIdList == null || weekLessonsIdList.size() == 0) {
            return;
        }
        int count = 0;
        for (Integer weekLesson : weekLessonsIdList) {
            count = lessonsMapper.deleteWeekLesson(weekLesson);
            if (count == 0) {
                throw new SQLOperationException();
            } else {
                count = 0;
            }
        }
    }

    @Override
    public void cleanAllLessonsByWeek(Integer teacherId, Integer courseId) throws SQLOperationException, IllegalParameterException {
        //    获取weekOfYear和dayOfYear
        Integer weekOfYear = TimeWeekUtil.getWeekOfYear();
        logger.debug("--------------weekOfYear------------:{}", weekOfYear);
        Integer dayOfWeek = TimeWeekUtil.getDayOfWeek() - 1;
        logger.debug("--------------dayofweek------------:{}", dayOfWeek);
        Integer year = TimeWeekUtil.getCurYear();
        //   获取当前周之后的本门课所有排课的周的主键
        List<Integer> weekLessonsIdList = getWeekLessonIds(weekOfYear, courseId, teacherId, year);
        //删除本周之后的所有的lessons
        if (weekLessonsIdList != null && weekLessonsIdList.size() != 0) {
            deleteLessons(weekLessonsIdList);
        }
        //获取本周的week_lessons的主键
        Integer weekLessonId = getWeekOfYearId(weekOfYear, courseId, teacherId, year);
        if (weekLessonId == null) {
            return;
        }
        //删除本周之后本天之后的lessons
        deleteLessons(weekLessonId, dayOfWeek);
        //删除所有的week_lessons
        //weekLessonsIdList.add(weekLessonId);
        deleteWeekLessons(weekLessonsIdList);
    }
}

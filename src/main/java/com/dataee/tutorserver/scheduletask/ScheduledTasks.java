package com.dataee.tutorserver.scheduletask;

import com.dataee.tutorserver.entity.MessageInformation;
import com.dataee.tutorserver.scheduletask.bean.LessonBean;
import com.dataee.tutorserver.scheduletask.service.IScheduleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/29 16:04
 */
@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private IScheduleTaskService scheduleTaskService;

    @Scheduled(cron = "0 0/60 * * * ?")
//    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws ParseException {
        System.out.println(new Date() + "1111111111111111111111");
        //获取所有未读课件的课堂
        List<LessonBean> lessonList = scheduleTaskService.getAllNotReadResourceLessons();
        for (LessonBean lesson : lessonList) {
            //如果现在是上课前12个小时
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, 12);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(lesson.getCourseTime());
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);
            long aTime = calendar.getTimeInMillis();
            long bTime = calendar1.getTimeInMillis();
            //两个时间差小于10分钟内进行提醒
            if (Math.abs(aTime - bTime) / (1000 * 60) <= 10) {
                //查看是否已有提醒过的消息在数据库表中，若有则不必再次提醒
                Integer isRead = scheduleTaskService.getIsRead(lesson.getLessonId());
                if (isRead == null) {
                    //将信息放入message中
                    MessageInformation messageInformation = new MessageInformation(lesson.getTeacherId(), 20, "您于12个小时之后有课程，" +
                            "现已上传课件，请注意查看！", lesson.getLessonId());
                    scheduleTaskService.insertIntoNewMessage(messageInformation);
                }
            }
        }
    }
}

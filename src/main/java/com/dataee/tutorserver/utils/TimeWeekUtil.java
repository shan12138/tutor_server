package com.dataee.tutorserver.utils;

import com.dataee.tutorserver.commons.bean.TimeBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 星期日期周转换工具
 *
 * @author JinYue
 * @CreateDate 2019/5/24 22:13
 */
public class    TimeWeekUtil {

 /*   private static final Integer[] MORNING = {8, 12};
    private static final Integer[] NOON = {14, 16};
    private static final Integer[] EVENING = {19, 23};*/
    private static final Integer[] MORNING = {0, 12};
    private static final Integer[] NOON = {12, 18};
    private static final Integer[] EVENING = {19, 23};

    /**
     * 给定第几周和星期几转换出当前日期
     *
     * @param day
     * @param time
     * @return
     */
    public static TimeBean dayToDate(Integer year, Integer week, Integer day, Integer time) {
        Calendar calendar = getCalender();
        TimeBean timeBean = new TimeBean();
        Integer[] dayTime = null;
        switch (time) {
            case 1:
                dayTime = MORNING;
                break;
            case 2:
                dayTime = NOON;
                break;
            case 3:
                dayTime = EVENING;
                break;
            default:
                //异常
                break;
        }
        //设置日历到year年的第week周的周一
        calendar.setWeekDate(year, week, 2);
        //向后推迟几天
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, dayTime[0]);
        //获取到当前周周一的时间
        Date startDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, dayTime[1]);
        Date endDate = calendar.getTime();
        //格式化
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //生成对象
        timeBean.setStartTime(ft.format(startDate));
        timeBean.setEndTime(ft.format(endDate));
        return timeBean;
    }

    /**
     * 通过今天的日期转换出今天处在第几周
     *
     * @return
     */
    public static Integer getWeekOfYear() {
        Calendar calendar = getCalender();
        Integer weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        return weekOfYear;
    }


    /**
     * 获取当前的年份
     *
     * @return
     */
    public static Integer getCurYear() {
        Calendar calendar = getCalender();
        return calendar.get(Calendar.YEAR);
    }


    /**
     * 获取当前日期所在对的周
     *
     * @param startDate
     * @return
     */
    public static Integer getWeekOfYear(Date startDate) {
        Calendar calendar = getCalender();
        calendar.setTime(startDate);
        Integer weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        return weekOfYear;
    }

    /**
     * 获取今天的星期
     *
     * @return
     */
    public static Integer getDayOfWeek() {
        Calendar calendar = getCalender();
        Integer weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekDay == 1) {
            weekDay = 7;
        }
        return weekDay - 1;
    }

    private static Calendar getCalender() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar;
    }
}

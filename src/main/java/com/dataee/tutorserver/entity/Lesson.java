package com.dataee.tutorserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课堂类（即一节课，一个课程对应多个课堂）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson extends Course {

    /**
     * ID编号
     */
    private Integer id;

    /**
     * 所属课程
     */
    private Course course;

    /**
     * 上课的老师（由于存在更换教员的情况，因此lesson中需存储教员,
     * 但是teacher类冗余信息太多，因此这里只存储名字）
     */
    private String teacherName;

    private Integer teacherId;

    /**
     * 上课时间
     */
    private String courseTime;

    /**
     * 下课时间
     */
    private String endCourseTime;

    /**
     * 是否签到
     */
    private Integer checkIn;

    /**
     * 签到时间
     */
    private String checkInTime;

    /**
     * 签退时间
     */
    private String checkOutTime;

    /**
     * 代表上午下午晚上
     */
    private Integer time;

    /**
     * 该节课的课堂时长
     */
    private Double classTime;

    /**
     * 教员评价审核状态
     */
    private Integer teacherRecordConfirmed;

    /**
     * 家长评价审核状态
     */
    private Integer parentRecordConfirmed;

    /**
     * 上传课件的状态
     */
    private Integer uploadState;

    /**
     * 课次
     */
    private Integer lessonNumber;

    private Integer resourceNum;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date remarkCheckInTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private  Date remarkCheckOutTime;

    /**
     * 状态
     */
    private Integer state;

}
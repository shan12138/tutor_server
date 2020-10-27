package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.*;

/**
 * 错题记录类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorQuestion {

    /**
     * ID编号
     */
    private Integer id;

    /**
     * 所属用户
     */
    private Person person;

    /**
     * 题目概要
     */
    private String essentialContent;

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 课次
     */
    private Integer lessonNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 题目照片
     */
    private List<String> questionPicture;

    /**
     * 是否打印
     */
    private boolean print;

    /**
     * 是否排课
     */
    private boolean course;

    /**
     * 上传时间
     */
    private Date time;

    /**
     * 状态
     */
    private Integer state;

}
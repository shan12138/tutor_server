package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 问题类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    /**
     * ID编号
     */
    private Integer id;

    /**
     * 所属科目
     */
    private String subject;

    /**
     * 类别
     */
    private String grade;

    /**
     * 类型
     */
    private String cType;

    /**
     * 问题描述
     */
    private String content;

    /**
     * 选项1
     */
    private String optionOne;

    /**
     * 选项2
     */
    private String optionTwo;

    /**
     * 选项3
     */
    private String optionThree;

    /**
     * 选项4
     */
    private String optionFour;

    /**
     * 答案
     */
    private String answer;

    /**
     * 所属章节
     */
    private String chapter;

    /**
     * 教材版本
     */
    private String edition;

    /**
     * 所属学期
     */
    private String term;

    /**
     * 标题
     */
    private String title;

    /**
     * 题型
     */
    private String qType;

    /**
     * 状态
     */
    private Integer state;

}
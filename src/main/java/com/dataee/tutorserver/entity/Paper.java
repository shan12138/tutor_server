package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * 试卷类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paper {

    /**
     * ID编号
     */
    private Integer id;

    /**
     * 视频链接
     */
    private List<String> videoHref;

    /**
     * PDF链接
     */
    private List<String> pdfHref;

    /**
     * 问题列表
     */
    private List<Question> questions;

    /**
     * 问题数量
     */
    private Integer questionNumber;

    /**
     * 状态
     */
    private Integer state;

}
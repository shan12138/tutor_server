package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * 教案类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyPdf {

    /**
     * ID编号
     */
    private Integer id;

    /**
     * 教案所属课程
     */
    private Course course;

    /**
     * 教案地址
     */
    private String pdfAddress;

    /**
     * 状态
     */
    private Integer state;

}
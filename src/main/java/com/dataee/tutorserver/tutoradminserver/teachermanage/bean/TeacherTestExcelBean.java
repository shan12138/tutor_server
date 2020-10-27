package com.dataee.tutorserver.tutoradminserver.teachermanage.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/16 12:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherTestExcelBean extends BaseRowModel {
    /**
     * 所属科目
     */
    @ExcelProperty(index = 0)
    private String subject;

    /**
     * 年级
     */
    @ExcelProperty(index = 1)
    private String grade;

    /**
     * 类型
     */
    @ExcelProperty(index = 2)
    private String cType;

    /**
     * 题目
     */
    @ExcelProperty(index = 3)
    private String content;

    /**
     * 答案
     */
    @ExcelProperty(index = 4)
    private String answer;

    /**
     * 所属章节
     */
    @ExcelProperty(index = 5)
    private String chapter;

    /**
     * 教材版本
     */
    @ExcelProperty(index = 6)
    private String edition;

    /**
     * 所属学期
     */
    @ExcelProperty(index = 7)
    private String term;

    /**
     * 选项1
     */
    @ExcelProperty(index = 8)
    private String optionFour;


    /**
     * 选项2
     */
    @ExcelProperty(index = 9)
    private String optionThree;

    /**
     * 选项3
     */
    @ExcelProperty(index = 10)
    private String optionTwo;

    /**
     * 选项4
     */
    @ExcelProperty(index = 11)
    private String optionOne;


    /**
     * 题型
     */
    @ExcelProperty(index = 12)
    private String qType;


    /**
     * 标题
     */
    @ExcelProperty(index = 13)
    private String title;


    public TeacherTestExcelBean(Object object) {
        //List test = (ArrayList<String>) object;
        //this.setSubject(test.get(0));
    }
}

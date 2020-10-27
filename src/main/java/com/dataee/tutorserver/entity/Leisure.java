package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * 教员的空余时间
 */
@Data
@AllArgsConstructor
public class Leisure {

    /**
     * ID编号
     */
    private Integer id;

    /**
     * 所属的教师
     */
    private Teacher teacher;

    /**
     * 哪一天
     */
    @NotNull(message = "没有指定具体时间")
    @Min(value = 0, message = "日期上传有误")
    private Integer day;

    /**
     * 上午
     */
    @Min(value = 0, message = "信息有误")
    @Max(value = 1, message = "信息有误")
    private Integer morning;

    /**
     * 下午
     */
    @Min(value = 0, message = "信息有误")
    @Max(value = 1, message = "信息有误")
    private Integer noon;

    /**
     * 晚上
     */
    @Min(value = 0, message = "信息有误")
    @Max(value = 1, message = "信息有误")
    private Integer evening;

    /**
     * 状态
     */
    private Integer state;

    public Leisure() {
        this.morning = 0;
        this.noon = 0;
        this.evening = 0;
        //排课默认0代表删除，1代表新增
        this.state = 1;
    }
}
package com.dataee.tutorserver.tutorteacherserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 上传空余信息的请求bean
 * 具体时间1：表示空闲，0表示不空闲
 *
 * @author JinYue
 * @CreateDate 2019/5/10 19:59
 */
@Data
@AllArgsConstructor
public class TeacherOtherInfoLeisureTimeRequestBean {
    /**
     * 数字表示周几
     */
    @NotNull(message = "没有指定具体时间")
    private Integer day;

    /**
     * 上午
     */
    @Max(value = 1, message = "信息有误")
    private Integer morning;

    /**
     * 下午
     */
    @Max(value = 1, message = "信息有误")
    private Integer noon;

    /**
     * 晚上
     */
    @Max(value = 1, message = "信息有误")
    private Integer evening;

    public TeacherOtherInfoLeisureTimeRequestBean() {
        this.morning = 0;
        this.noon = 0;
        this.evening = 0;
    }
}

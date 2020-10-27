package com.dataee.tutorserver.commons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 为时间转换工具提供的bean
 *
 * @author JinYue
 * @CreateDate 2019/5/24 23:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeBean {
    private String startTime;
    private String endTime;
}

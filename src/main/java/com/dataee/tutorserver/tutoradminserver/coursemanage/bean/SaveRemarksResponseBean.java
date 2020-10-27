package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/22 15:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveRemarksResponseBean {
    @Min(value = 2019, message = "教学年份有误")
    private Integer year;
    @NotNull(message = "日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @NotNull(message = "课程信息不完整")
    private Integer courseId;
    private Integer teacherId;
    @NotBlank(message = "备注信息不能为空")
    private String remarks;
}

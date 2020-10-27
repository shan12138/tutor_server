package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import com.dataee.tutorserver.utils.TimeWeekUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * @author JinYue
 * @CreateDate 2019/6/30 20:46
 */
@Data
@AllArgsConstructor
public class SaveLessonRequestBean {
    private Integer id;
    @NotNull(message = "课程信息不完整")
    private Integer courseId;
    private Integer teacherId;
    @Min(value = 2019, message = "教学年份有误")
    private Integer year;
    @NotNull(message = "日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @Valid
    @NotNull(message = "当前排课信息为空，请重新选择！")
    private LessonBean lesson;

    public SaveLessonRequestBean() {
        this.year = TimeWeekUtil.getCurYear();
    }
}

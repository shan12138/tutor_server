package com.dataee.tutorserver.tutoradminserver.filemanage.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 课件对应的没节lesson
 *
 * @author JinYue
 * @CreateDate 2019/6/29 13:23
 */
@Data
public class CoursewareRequestBean extends ResourceRequestBean {
    @NotNull(message = "没有指定当周课程")
    private Integer lessonId;

    public CoursewareRequestBean() {
        super();
    }

    public CoursewareRequestBean(Integer coursewareId,
                                 @NotBlank(message = "上传文件不能为空") String coursewareName,
                                 @NotBlank(message = "上传的文件地址不能为空") String pdfAddress,
                                 @NotNull(message = "课程名不能为空") Integer courseId,
                                 @NotNull(message = "课程类型不能为空") String coursewareType,
                                 @NotNull(message = "未指定合同人") Integer personId,
                                 @NotNull(message = "没有指定当周课程") Integer lessonId) {
        super(coursewareId, coursewareName, pdfAddress, courseId, coursewareType, personId);
        this.lessonId = lessonId;
    }
}

package com.dataee.tutorserver.tutoradminserver.filemanage.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 教学资源对应了没门课course
 *
 * @author JinYue
 * @CreateDate 2019/6/29 13:21
 */
@Data
public class TeachingResourceRequestBean extends ResourceRequestBean {
    public TeachingResourceRequestBean() {
        super();
    }

    public TeachingResourceRequestBean(Integer coursewareId,
                                       @NotBlank(message = "上传文件不能为空") String coursewareName,
                                       @NotBlank(message = "上传的文件地址不能为空") String pdfAddress,
                                       @NotNull(message = "课程名不能为空") Integer courseId,
                                       @NotNull(message = "课程类型不能为空") String coursewareType,
                                       @NotNull(message = "未指定合同人") Integer personId) {
        super(coursewareId, coursewareName, pdfAddress, courseId, coursewareType, personId);
    }
}

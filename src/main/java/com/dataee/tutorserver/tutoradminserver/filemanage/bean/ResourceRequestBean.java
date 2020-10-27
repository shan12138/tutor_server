package com.dataee.tutorserver.tutoradminserver.filemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author JinYue
 * @CreateDate 2019/6/9 22:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRequestBean {
    private Integer resourceId;
    @NotBlank(message = "上传文件不能为空")
    private String resourceName;
    @NotBlank(message = "上传的文件地址不能为空")
    private String pdfAddress;
    @NotNull(message = "课程名不能为空")
    private Integer courseId;
    @NotNull(message = "资源类型不能为空")
    private String resourceType;
    @NotNull(message = "未指定合同人")
    private Integer personId;
}

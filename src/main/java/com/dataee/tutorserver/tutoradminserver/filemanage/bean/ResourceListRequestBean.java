package com.dataee.tutorserver.tutoradminserver.filemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JinYue
 * @CreateDate 2019/6/10 21:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceListRequestBean {
    private int resourceId;
    private String resourceName;
    private String pdfAddress;
    private String teacherName;
    private String courseName;
    private String studyAdmin;
    private String resourceType;
    private Integer uploadState;
}

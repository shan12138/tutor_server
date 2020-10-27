package com.dataee.tutorserver.tutorteacherserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author JinYue
 * @CreateDate 2019/6/16 0:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyCenterResponseBean {
    private Integer resourceId;
    private String resourceName;
    private String pdfAddress;
    private String courseName;
}

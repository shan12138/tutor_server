package com.dataee.tutorserver.tutorteacherserver.bean;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 课件信息(名称+图片)
 *
 * @author JinYue
 * @CreateDate 2019/6/29 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCoursewareResponseBean {
    private Integer coursewareId;
    private String coursewareName;
    private NewPageInfo<String> imageInfo;
}

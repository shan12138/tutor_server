package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教案
 *
 * @author JinYue
 * @CreateDate 2019/5/23 3:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyResource {
    private Integer id;
    private String name;
    private String address;
    private Teacher teacher;
    private Student student;
    private Integer state;
}

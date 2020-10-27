package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/5 14:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherLevel {
    private Integer id;
    private String levelName;
    private Integer level;
    private Integer amount;
}

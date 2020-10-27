package com.dataee.tutorserver.userserver.bean;

import com.dataee.tutorserver.entity.Grade;
import com.dataee.tutorserver.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/21 15:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeAndSubjectResponseBean {
    private List<Grade> gradeList;
    private List<Subject> subjectList;
}

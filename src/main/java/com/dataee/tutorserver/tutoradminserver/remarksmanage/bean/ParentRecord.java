package com.dataee.tutorserver.tutoradminserver.remarksmanage.bean;

import com.dataee.tutorserver.entity.TeacherLabel;
import com.dataee.tutorserver.tutorpatriarchserver.bean.Remarks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/9 21:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentRecord {
    private Integer lessonId;
    private List<Remarks> remarksList;
    private TeacherLabel teacherLabel;
}

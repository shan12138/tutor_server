package com.dataee.tutorserver.tutoradminserver.teachermanage.bean;

import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.ClassAndHour;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/11 20:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDetailResponseBean {
    //    private Teacher teacher;
    private List<ClassAndHour> classAndHour;
}

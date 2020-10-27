package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import com.dataee.tutorserver.entity.Administrator;
import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.userserver.bean.GradeAndSubjectResponseBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/21 15:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAddCourseResponseBean extends GradeAndSubjectResponseBean {
    private List<Parent> parentList;
    private List<Administrator> scheduleAdminList;
    private List<Administrator> parentAdminList;
    private List<Administrator> courseAdminList;
}

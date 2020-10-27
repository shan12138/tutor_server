package com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean;

import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.entity.Partner;
import com.dataee.tutorserver.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 正式家长，多个孩子合并
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormalParent {
    private Integer parentId;
    private String parentName;
    private String telephone;
    private Parent parent ;
    private Partner partner;
    private List<Student> students;
    private Partner invitePartner;

    public static List<FormalParent> fromParents(List<Parent> parents) {
        List<FormalParent> formalParents = new ArrayList<>();

        for (Parent parent : parents) {
            List<Student> students = new ArrayList<>();
            for (Student student : parent.getStudent()) {
                Student formalStudent = new Student();
                formalStudent.setStudentName(student.getStudentName());
                formalStudent.setSex(student.getSex());
                students.add(formalStudent);
            }
            Parent inviteParent = (parent.getParent()==null) ? null : parent.getParent();
            Partner partner = (parent.getPartner() ==null) ? null : parent.getPartner();
            Partner invitePartner = (parent.getInvitePartner()==null) ? null : parent.getInvitePartner();

            FormalParent formalParent = new FormalParent(
                    parent.getParentId(),
                    parent.getParentName(),
                    parent.getTelephone(),
                    inviteParent,
                    partner,
                    students,
                    invitePartner
            );
            formalParents.add(formalParent);
        }
        return formalParents;
    }
}

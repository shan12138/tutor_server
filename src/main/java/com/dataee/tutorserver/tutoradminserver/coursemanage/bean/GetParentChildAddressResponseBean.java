package com.dataee.tutorserver.tutoradminserver.coursemanage.bean;

import com.dataee.tutorserver.entity.Address;
import com.dataee.tutorserver.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/25 18:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetParentChildAddressResponseBean {
    private List<Student> studentList;
    private List<Address> addressList;
}

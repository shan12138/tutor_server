package com.dataee.tutorserver.tutoradminserver.teachermanage.bean;

import com.dataee.tutorserver.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 23:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherListResponseBean {
    private Integer teacherId;
    private String teacherName;
    private String telephone;
    private Integer state;
    private Integer sex;
    //0代表待命，1代表已分配试讲
    private Integer tempSpeaking;
    private List<Product> products;
}

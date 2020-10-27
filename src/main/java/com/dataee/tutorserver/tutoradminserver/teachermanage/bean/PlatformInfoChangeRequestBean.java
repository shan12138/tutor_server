package com.dataee.tutorserver.tutoradminserver.teachermanage.bean;

import com.dataee.tutorserver.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/17 9:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformInfoChangeRequestBean {
    private String interviewResult;
    private List<String> teachingArea;
    private Integer teacherId;
    private List<Integer>  products;
}

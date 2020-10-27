package com.dataee.tutorserver.tutorpatriarchserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/8 23:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemarksListBean {
    @NotNull
    private Integer lessonId;
    private List<Remarks> remarksList;
}

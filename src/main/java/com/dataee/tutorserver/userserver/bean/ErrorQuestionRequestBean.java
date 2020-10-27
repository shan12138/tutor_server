package com.dataee.tutorserver.userserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 上传错题请求bean
 *
 * @author JinYue
 * @CreateDate 2019/5/7 0:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorQuestionRequestBean {
    /**
     * 数据库对应
     * * essential_content
     */
    private Integer id;
    @NotBlank(message = "关键词不能为空")
    private String keyWord;
    @NotNull(message = "没有选定课程")
    private Integer courseId;
    @NotBlank(message = "课程不能为空")
    private String courseName;
    @NotNull(message = "没有选择课次")
    private Integer lessonId;
    @NotNull(message = "课次不能为空")
    private Integer lessonNumber;
    private String remarks;
    private List<String> errorQuestions;
}

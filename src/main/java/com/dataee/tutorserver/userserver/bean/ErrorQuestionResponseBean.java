package com.dataee.tutorserver.userserver.bean;

import com.dataee.tutorserver.entity.ErrorQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/7 1:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorQuestionResponseBean {
    private List<ErrorQuestion> errorQuestions;
}

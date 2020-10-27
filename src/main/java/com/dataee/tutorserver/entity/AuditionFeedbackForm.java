package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditionFeedbackForm {
    private Integer id;
    private Node node;
    private String teacherSummary;
    private String auditionSummary;
    private Date time;
    private String auditionResult;
    private String teacherMatchSuggestion;
    private Integer nodeId;
}

package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainForm {
    private  Integer id;
    private  Node    node;
    private  Date    getFormTime;
    private  Double  trainScore;
    private  String  suggestionEvaluation;
    private  Date    endTime;
    private Integer  nodeId;
}

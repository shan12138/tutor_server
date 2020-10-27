package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private Integer  nodeId;
    private WorkFlow workFlow;
    private Integer  sequenceNum;
    //操作者
    private Administrator  operator;
    //提交给谁
    private Administrator  submitter;
    private Date           operationTime;
    private Date           createTime;
    private Integer        stepNum;
    private Integer        type;

    private Integer submitterId;
}

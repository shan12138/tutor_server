package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkFlow {
    private  Integer workFlowId;
    private  String  workFlowSn;
    private  String  workFlowName;
    private  Administrator administrator;
    private  Date    joinTime;
    private  List<Node>  nodes;

}

package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private Integer id;
    private String  workFlowSn;
    private String  sn;
    private Integer version;
    private Date    time;
    private String  way;
    private Node node;
    private Integer nodeId;
}

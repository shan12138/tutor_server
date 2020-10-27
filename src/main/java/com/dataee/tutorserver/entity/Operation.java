package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    private  Integer id;
    private  Node    node;
    private  String  type;
    private  String  comment;
    private  Integer nodeId;

}

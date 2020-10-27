package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeadMasterExamine {
    private  Integer id;
    private  Node node;
    private  String isAgree;
    private  Integer nodeId;
}

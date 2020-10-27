package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
   private  Integer  taskId;
   private  String   taskName;
   private  WorkFlow workFlow;
   private  Node     node;
   private  Administrator taskHandler;
   private  String   state;
}

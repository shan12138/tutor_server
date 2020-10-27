package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 家长等级
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentLevel {
   private Integer id;
   private String  levelName;
   private Integer level;
   private Double  firstCommissionRatio;
   private Double  nextCommissionRatio;
   private Product product;
   private Integer productId;
}

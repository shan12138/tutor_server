package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 提现记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalsRecord {
   private Integer id;
   private Partner partner;
   private Double withdrawalsMoney;
   private Date applicationTime;
   private String state;
   private Integer partnerId;
}

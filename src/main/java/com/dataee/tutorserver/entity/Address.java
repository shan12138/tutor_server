package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地址信息类
 * Address表中记录的是当前有效的地址，即审核成功的
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    /**
     * 地址ID
     */
    private Integer addressId;

    /**
     * 家长
     */
    private Parent parent;

    /**
     * 地区信息
     */
    private String region;

    /**
     * 详细地址信息
     */
    private String addressDetail;

    /**
     * 是否审核
     */
    private Integer confirmed;

    /**
     * 状态
     */
    private Integer state;








}
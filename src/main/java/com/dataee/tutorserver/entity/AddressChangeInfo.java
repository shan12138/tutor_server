package com.dataee.tutorserver.entity;

import java.util.Date;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/6 9:31
 * Address中只记录有效地址（即审核通过的地址）
 * AddressChangeInfo中记录旧地址和新地址（都要存储）
 * 当处于申请中状态时，Address中有效地址为空
 * 当审核通过后，Address中有效地址为AddressChangeInfo中的新地址
 * 当审核失败后，Address中有效地址为AddressChangeInfo旧地址
 */
public class AddressChangeInfo {
    /**
     * ID编号
     */
    private Integer id;

    /**
     * 所修改的地址信息
     */
    private Address address;

    /**
     *
     */

    /**
     * 旧的地址信息
     */
    private String oldAddress;

    /**
     * 新的地区信息
     */
    private String newRegion;

    /**
     * 新的详细地址信息
     */
    private String newAddressDetail;

    /**
     * 管理员审核时间
     */
    private Date confirmTime;

    /**
     * 管理员审核人ID
     */
    private String confirmUserId;

    /**
     * 备注（如反驳审核时，填写反驳原因）
     */
    private String remark;

    /**
     * 是否审核通过
     * 0表示审核中
     * 1表示审核通过
     * 2表示审核不通过
     */
    private Integer confirmed;

    /**
     * 状态
     */
    private Integer state;
}

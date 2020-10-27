package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.Date;


/**
 * 合同类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractPdf {

    /**
     * ID编号
     */
    private Integer contractId;

    /**
     * 家长的合同数据
     */
    private ParentContract parentContract;

    private Integer parentContractId;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 没签名的合同的地址
     */
    private String pdfAddress;
    /**
     * 签名的合同的地址
     */
    private String signedPdfAddress;
    
    /**
     * 合同的编号
     */
    private String idCard;

    /**
     * 所属的用户
     */
    private Person person;

    private Integer personRole;

    /**
     * 文件当前的状态
     * 上传中
     * 已经上传
     */
    private int uploadState;

    /**
     * 创建时间
     */


    private Date crtDate;
    /**
     * 状态
     */
    private Integer state;

}
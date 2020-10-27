package com.dataee.tutorserver.tutorpatriarchserver.bean;

import com.dataee.tutorserver.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentContractBean {
    /**
     * ID编号
     */
    private Integer contractId;

    private String  sn;
    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同的地址
     */
    private String pdfAddress;

    private String signedPdfAddress;

    /**
     * 合同的编号
     */
    private String number;

    /**
     * 所属的用户
     */
    private Person person;

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

    private Integer id;

    private String  signImage;

    private Integer isSign;

    List<String> contractImages;
}

package com.dataee.tutorserver.commons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.List;

/**
 * 合同访问url
 *
 * @author JinYue
 * @CreateDate 2019/5/6 1:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractUrlResponseBean {
    private URL contractUrl;
    private URL signedContractUrl;
}

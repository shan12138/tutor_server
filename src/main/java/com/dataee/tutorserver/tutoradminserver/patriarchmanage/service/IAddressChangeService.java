package com.dataee.tutorserver.tutoradminserver.patriarchmanage.service;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.tutoradminserver.messagemanage.bean.InfoChangeVerifyRequestBean;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 20:46
 */
public interface IAddressChangeService {
    NewPageInfo getAllAddressChange(Page page);

    void acceptAddressChange(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws SQLOperationException;

    void denyAddressChange(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws SQLOperationException;

    NewPageInfo queryAddressChangeInfo(String teacher,String studentName, String state, Page page);
}

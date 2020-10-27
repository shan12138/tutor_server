package com.dataee.tutorserver.tutoradminserver.patriarchmanage.service.impl;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.Address;
import com.dataee.tutorserver.entity.CourseAddress;
import com.dataee.tutorserver.entity.MessageInformation;
import com.dataee.tutorserver.tutoradminserver.messagemanage.bean.InfoChangeVerifyRequestBean;
import com.dataee.tutorserver.tutoradminserver.messagemanage.dao.MessageManageMapper;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.AddressChangeResponseBean;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao.AddressChangeMapper;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.service.IAddressChangeService;
import com.dataee.tutorserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 20:46
 */
@Service
public class AddressChangeServiceImpl implements IAddressChangeService {
    @Autowired
    private AddressChangeMapper addressChangeMapper;
    @Autowired
    private MessageManageMapper messageManageMapper;

    @Override
    public NewPageInfo getAllAddressChange(Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<AddressChangeResponseBean> addressChangeResponseBeans = addressChangeMapper.getAllAddressChange();
        PageInfo pageInfo = new PageInfo(addressChangeResponseBeans);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }



   /* @Override
    @Transactional(rollbackFor = Exception.class)
    public void acceptAddressChange(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws SQLOperationException {
        int number = addressChangeMapper.verifyAddressChange(infoChangeVerifyRequestBean);
        if (number != 1) {
            throw new SQLOperationException();
        }
        Address address = addressChangeMapper.getAddress(infoChangeVerifyRequestBean.getId());
        number = addressChangeMapper.changeAddress(address);
        if (number != 1) {
            throw new SQLOperationException();
        }
        Integer parentId = addressChangeMapper.getParentId(address.getAddressId());
        MessageInformation messageInformation = new MessageInformation(
                parentId, 40, "家庭地址信息审核通过",
                infoChangeVerifyRequestBean.getRemark()
        );
        number = messageManageMapper.addToMessageTable(messageInformation);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void denyAddressChange(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws SQLOperationException {
        int number = addressChangeMapper.verifyAddressChange(infoChangeVerifyRequestBean);
        int addressId = addressChangeMapper.getAddressId(infoChangeVerifyRequestBean.getId());
        addressChangeMapper.changeAddressState(addressId);
        if (number != 1) {
            throw new SQLOperationException();
        }
        Integer parentId = addressChangeMapper.getParentId(addressId);
        MessageInformation messageInformation = new MessageInformation(
                parentId, 40, "家庭地址信息审核未通过",
                infoChangeVerifyRequestBean.getRemark()
        );
        number = messageManageMapper.addToMessageTable(messageInformation);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }*/
    /**同意：
     * 1.同意后改状态（根据id）
     * 2.根据id找出修改的
     * 3.从地址变更表中把新地址取出来
     *4,修改address_course中的值
     *
     *
     * 不同意的话：
     * 1.修改状态，根据id
     */
      @Override
    @Transactional(rollbackFor = Exception.class)
    public void acceptAddressChange(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws SQLOperationException {


        int number = addressChangeMapper.verifyAddressChange(infoChangeVerifyRequestBean);
        if (number != 1) {
            throw new SQLOperationException();
        }
        CourseAddress address = addressChangeMapper.getAddress(infoChangeVerifyRequestBean.getId());
        number = addressChangeMapper.changeCourseAddress(address);
        if (number != 1) {
            throw new SQLOperationException();
        }
        Integer parentId = addressChangeMapper.getParentId(address.getAddressId());
        MessageInformation messageInformation = new MessageInformation(
                parentId, 40, "课程地址信息审核通过",
                infoChangeVerifyRequestBean.getRemark()
        );
        number = messageManageMapper.addToMessageTable(messageInformation);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void denyAddressChange(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean) throws SQLOperationException {
        int number = addressChangeMapper.verifyAddressChange(infoChangeVerifyRequestBean);
        int addressId = addressChangeMapper.getAddressId(infoChangeVerifyRequestBean.getId());
      // addressChangeMapper.changeCourseAddressState(addressId);
        if (number != 1) {
            throw new SQLOperationException();
        }
        Integer parentId = addressChangeMapper.getParentId(addressId);
        MessageInformation messageInformation = new MessageInformation(
                parentId, 40, "课程地址信息审核未通过",
                infoChangeVerifyRequestBean.getRemark()
        );
        number = messageManageMapper.addToMessageTable(messageInformation);
        if (number != 1) {
            throw new SQLOperationException();
        }
    }
    @Override
    public NewPageInfo queryAddressChangeInfo(String teacher,String studentName, String state, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<AddressChangeResponseBean> addressChangeResponseBeans = addressChangeMapper.queryAddressChangeInfo(teacher,studentName, state);
        PageInfo pageInfo = new PageInfo(addressChangeResponseBeans);
        NewPageInfo newPageInfo = PageInfoUtil.read(pageInfo);
        return newPageInfo;
    }
}

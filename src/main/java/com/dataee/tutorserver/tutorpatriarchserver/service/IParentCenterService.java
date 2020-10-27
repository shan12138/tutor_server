package com.dataee.tutorserver.tutorpatriarchserver.service;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.dataee.tutorserver.commons.bean.Page;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.entity.Address;
import com.dataee.tutorserver.entity.MessageInformation;
import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.entity.Student;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ChangeAddressRequestBean;
import com.dataee.tutorserver.tutorpatriarchserver.bean.CreateAddressRequestBean;
import com.dataee.tutorserver.tutorpatriarchserver.bean.EnrollChildInfoRequestBean;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ParentContractBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/4/29 10:57
 */
public interface IParentCenterService {
    NewPageInfo<Student> getOwnChildren(Integer parentId, Page page);

    Student getChildDetailInfo(String studentId);

    void addChildInfo(EnrollChildInfoRequestBean enrollChildInfoRequestBean) throws BaseServiceException;

    Parent getParentInfo(String parentId);

    void changeParentInfo(Integer parentId, String parentName, Integer sex) throws SQLOperationException;

    void addAddress(CreateAddressRequestBean createAddressRequestBean) throws SQLOperationException;

    List<Address> getAddress(String parentId);

    void changeAddressInfo(ChangeAddressRequestBean changeAddressRequestBean) throws SQLOperationException, BaseControllerException;

    void enrollParentInfo(EnrollChildInfoRequestBean enrollChildInfo) throws BaseServiceException, BaseControllerException;

    NewPageInfo<MessageInformation> getMsgList(Integer parentId, int role, Page page);

    Integer getState(Integer parentId);

    void changeChildInfo(EnrollChildInfoRequestBean enrollChildInfo);

    Student getOneChildDetail(Integer parentId);

    Address getOneAddress(Integer addressId);

    Integer getAddressId(Integer personId);

    void changeOneAddressInfo(ChangeAddressRequestBean changeAddressRequestBean);

    void changeParentState(Integer parentId);

    List<String> getWeakDiscipline(Integer studentId);

    void createParent(Integer parentId, String parentName, Integer sex, String idCard, String inviteCode) throws BaseServiceException;

    List<Address> getOfficialAddress(String parentId);

    Integer getMsgNum(Integer personId, int roleId);

    List<Address> getAddressByParentId(Integer parentId);

    void changeCourseAddressInfo(ChangeAddressRequestBean changeAddressRequestBean) throws SQLOperationException;

    List<String> getContractAddress(Integer personId,  Integer personRole );

    NewPageInfo<ParentContractBean> getParentContractList(Integer personId,  Integer personRole,Page page);

    ParentContractBean getParentContractByid(Integer contractId) throws BaseServiceException;

    void getUploadParentContract(Integer contractId,String path) throws BaseServiceException;
}

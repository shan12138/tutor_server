package com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao;

import com.dataee.tutorserver.entity.Address;
import com.dataee.tutorserver.entity.CourseAddress;
import com.dataee.tutorserver.tutoradminserver.messagemanage.bean.InfoChangeVerifyRequestBean;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.AddressChangeResponseBean;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao.sqlprovider.QueryAddressChangeInfoSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/5 20:47
 */
@Mapper
@Repository
public interface AddressChangeMapper {

    @Select("SELECT  cp.contract_id ,s.`student_name`,p.`telephone` AS parentTel,t.`telephone` AS teacherTel,t.`teacher_name`,CONCAT(aci.new_region, aci.new_address_detail) AS new_address,CONCAT(aci.old_region, aci.old_address_detail)AS old_address,aci.`remark`,aci.`is_confirmed` AS confirmed\n" +
            "FROM   address_change_info   aci\n" +
            "INNER JOIN address_course acc ON acc.address_id=aci.address_id\n" +
            "INNER JOIN  course c ON c.address_id=aci.address_id\n" +
            "INNER JOIN parent p ON p.parent_id=c.parent_id\n" +
            "INNER JOIN teacher t ON t.teacher_id=c.teacher_id\n" +
            "INNER JOIN  student s ON s.student_id=c.student_id\n" +
            "INNER JOIN  contract_pdf cp ON cp.id_card=s.id_card")
    List<AddressChangeResponseBean> getAllAddressChange();

    @Update("update address_change_info set is_confirmed = #{accepted}, remark = #{remark} where id = #{id} and is_confirmed=0")
    int verifyAddressChange(InfoChangeVerifyRequestBean infoChangeVerifyRequestBean);


 /*   @Select("select address_id, new_region, new_address_detail from address_change_info where id = #{id} and state = 1")
    @Results(value = {
            @Result(property = "region", column = "new_region"),
            @Result(property = "addressDetail", column = "new_address_detail")
    })
    Address getAddress(Integer id);*/

 @Select("select address_id, new_region, new_address_detail from address_change_info where id = #{id}")
 @Results(value = {
         @Result(property = "region", column = "new_region"),
         @Result(property = "addressDetail", column = "new_address_detail")
 })
 CourseAddress getAddress(Integer id);

    @Update("update address set region = #{region}, address_detail = #{addressDetail} where address_id = #{addressId}")
    int changeAddress(Address address);


    @Update("update  address_course set region = #{region}, address_detail = #{addressDetail} where address_id = #{addressId}")
    int changeCourseAddress(CourseAddress courseAddress);

    /**
     * 地址变更
     * @param teacher
     * @param studentName
     * @param state
     * @return
     */
    @SelectProvider(type = QueryAddressChangeInfoSqlProvider.class, method = "queryAddressChangeInfoByCondition")
    List<AddressChangeResponseBean> queryAddressChangeInfo(@Param("teacher") String teacher,
                                                           @Param("studentName")String studentName,
                                                           @Param("state") String state);

    @Select("select address_id from address_change_info where id = #{id}")
    int getAddressId(Integer id);

    @Update("update address set state = 0 where address_id = #{addressId}")
    void changeAddressState(int addressId);

    /**
     * 修改课程地址状态
     * @param addressId
     */
    @Update("update address_course set state = 0 where address_id = #{addressId}")
    void changeCourseAddressState(int addressId);

    @Select("select parent_id from address_course where address_id = #{addressId}")
    Integer getParentId(Integer addressId);


    @Select("select * from address where  parent_id = #{parentId}  LIMIT 0,1")
    Address getAddressById(Integer parentId);


























}

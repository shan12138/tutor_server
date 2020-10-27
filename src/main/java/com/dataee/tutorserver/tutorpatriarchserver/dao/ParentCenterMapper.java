package com.dataee.tutorserver.tutorpatriarchserver.dao;

import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ChangeAddressRequestBean;
import com.dataee.tutorserver.tutorpatriarchserver.bean.CreateAddressRequestBean;
import com.dataee.tutorserver.tutorpatriarchserver.bean.EnrollChildInfoRequestBean;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ParentContractBean;
import org.apache.commons.collections4.Get;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/4/29 10:56
 */
@Mapper
@Repository
public interface ParentCenterMapper {
    @Select("select student.*, contract_id, contract_name, upload_state from student left join\n" +
            "(select contract_id, id_card, contract_name, upload_state from contract_pdf where state = 1)\n" +
            "as contract on student.id_card = contract.id_card where student.state = 1 and parent_id = #{parentId}")
    List<Student> getOwnChildren(Integer parentId);

    @Select("select * from student where student_id = #{studentId} and state = 1")
    @Results({
            @Result(property = "weakDiscipline", column = "student_id",
                    many = @Many(select = "getWeakDiscipline")),
    })
    Student getChildDetailInfo(String studentId);

    @Select("select concat(grade, '-', subject) as weakDiscipline from weak_discipline where student_id  = #{studentId} and state = 1")
    List<String> getWeakDiscipline(@Param("studentId") Integer studentId);

    @Insert("insert into student(student_name, sex, grade,school,id_card, parent_id) values(#{studentName}, " +
            "#{sex}, #{grade},#{school},#{idCard}, #{parentId})")
    @Options(useGeneratedKeys = true, keyProperty = "studentId", keyColumn = "student_id")
    int addChildInfo(EnrollChildInfoRequestBean enrollChildInfoRequestBean);

    @Select("select * from parent where parent_id = #{parentId} and state != 0")
    Parent getParentInfo(String parentId);

    @Update("update parent set parent_name = #{parentName}, sex = #{sex} where parent_id = #{parentId}")
    int changeParentInfo(@Param("parentId") Integer parentId, @Param("parentName") String parentName, @Param("sex") Integer sex);

  /* @Insert("insert into address(parent_id) values(#{parentId})")
    @Options(useGeneratedKeys = true, keyProperty = "addressId", keyColumn = "address_id")*/

    @Insert("insert into address(parent_id,region,address_detail) VALUES (#{parentId},#{newRegion},#{newAddressDetail})")
    int addAddress(CreateAddressRequestBean createAddressRequestBean);


    @Insert("insert into address_change_info(address_id, new_region, new_address_detail) values(#{addressId}, #{newRegion}, #{newAddressDetail})")
    int addAddressChangeInfo(CreateAddressRequestBean createAddressRequestBean);


    @Select("select address.address_id, new_region, new_address_detail, is_confirmed from address_change_info inner join " +
            " address on address.address_id = address_change_info.address_id and address.state = 1 and address_change_info.state = 1 " +
            "where parent_id = #{parentId} and is_confirmed != 2")
    @Results(id = "addressMap", value = {
            @Result(property = "addressId", column = "address_id"),
            @Result(property = "region", column = "new_region"),
            @Result(property = "addressDetail", column = "new_address_detail"),
            @Result(property = "confirmed", column = "is_confirmed")
    })
    List<Address> getAddress(String parentId);

    @Update("update address_change_info set old_region = #{oldRegion}, old_address_detail = #{oldAddressDetail}, " +
            "new_region = #{newRegion}, new_address_detail = #{newAddressDetail}, is_confirmed = 0 where address_id = #{addressId}")
    int updateNewAddress(ChangeAddressRequestBean changeAddressRequestBean);

    @Select("select region from address where address_id = #{id} and state = 1")
    String getOldRegion(Integer id);

    @Select("select address_detail from address where address_id = #{id} and state = 1")
    String getOldAddressDetail(Integer id);



    @Select("select region from address_course where address_id = #{id} ")
    String getOldCourseRegion(Integer id);

    @Select("select address_detail from address_course where address_id = #{id} ")
    String getOldCourseAddressDetail(Integer id);

    @Update("update parent set state = 2 where parent_id = #{parentId}")
    int enrollParentInfo(String parentId);

    @Select("select * from message_information where #{parentId} = person_id and #{role} = person_role " +
            "and state = 1 order by time desc")
    List<MessageInformation> getParentMsgList(@Param("parentId") Integer parentId, @Param("role") int role);

    @Select("select state from parent where parent_id = #{parentId}")
    Integer getState(Integer parentId);

    @Update("update student set student_name = #{studentName}, sex = #{sex}, grade = #{grade},school = #{school},id_card = #{idCard} " +
            "where student_id = #{studentId}")
    void changeChildInfo(EnrollChildInfoRequestBean enrollChildInfo);

    @Select("select student_id from student where parent_id = #{parentId} and state = 1")
    Integer getOneChildId(Integer parentId);

    @Select("select id, new_region, new_address_detail from address_change_info where address_id = #{addressId} and state = 1")
    Address getOneAddress(Integer addressId);

    @Select("select address_id from address where parent_id = #{parentId} and state = 1")
    Integer getAddressId(Integer parentId);

    @Update("update address_change_info set new_region = #{newRegion}, new_address_detail = #{newAddressDetail} " +
            "where address_id = #{addressId} and state = 1")
    void changeOneAddressInfo(ChangeAddressRequestBean changeAddressRequestBean);

    @Update("update parent set state = 2 where parent_id = #{parentId} and state = 1")
    void changeParentState(Integer parentId);

    @Insert("insert into weak_discipline(student_id, grade, subject) values(#{studentId}, #{grade}, #{subject})")
    void addChildWeakDiscipline(@Param("studentId") int studentId, @Param("grade") String grade, @Param("subject") String subject);

    @Update("update weak_discipline set state = 0 where student_id = #{studentId}")
    void changeWeakDisciplineState(int studentId);

    @Update("update parent set parent_name = #{parentName}, sex = #{sex}, invited_code = " +
            " #{invitedCode}, invited_parent_id = #{invitedParentId}, invite_code = #{ownInviteCode}," +
            " parent_level = #{parentLevel}, partner_id = #{partnerId} where parent_id = #{parentId}")
    void createParent(@Param("parentId") Integer parentId,
                      @Param("parentName") String parentName,
                      @Param("sex") Integer sex,
                      @Param("invitedCode") String inviteCode,
                      @Param("invitedParentId") Integer invitedParentId,
                      @Param("ownInviteCode") String ownInviteCode,
                      @Param("parentLevel") Integer parentLevel,
                      @Param("partnerId") Integer partnerId);

    @Select("select *from address where parent_id = #{parent_id} and state != 0 and region is not null " +
            "and address_detail is not null")
    List<Address> getOfficialAddress(String parentId);

    @Select("select count(*) from message_information where person_id = #{personId} and person_role = #{roleId} and is_read = 0")
    Integer getMsgNum(@Param("personId") Integer personId, @Param("roleId") int roleId);

    @Update("update message_information set is_read = 1 where id = #{id}")
    void setRead(Integer id);

    @Update("update address set state=#{state} where address_id=#{addressId}")
    void  updateHomeAddress(@Param("state") Integer state,@Param("addressId")Integer addressId);

    @Insert("insert into address_change_info(address_id, old_region, old_address_detail,new_region, new_address_detail,is_confirmed) values(#{addressId},#{oldRegion}, #{oldAddressDetail}, #{newRegion}, #{newAddressDetail},0)")
    int addCourseAddressChangeInfo(ChangeAddressRequestBean changeAddressRequestBean);

    @Select("SELECT  * FROM  address WHERE parent_id =#{parentId} ")
    List<Address> getAddressByParentId(Integer parentId);

    @Update("UPDATE  address SET region=#{newRegion} ,address_detail=#{newAddressDetail}  WHERE address_id=#{addressId}")
    int updateHomeaddress (ChangeAddressRequestBean changeAddressRequestBean);

    @Select("SELECT * FROM course WHERE course_id=#{courseId} and state != 0")
    @Results(value = {
            @Result(id= true ,property = "id",column = "id"),
            @Result(property = "courseId",column = "course_id"),
            @Result(property = "address", column = "address_id", one = @One(select = "getCourseAddressById"),javaType = CourseAddress.class)
    })
    Course getCourseById(Integer courseId);


    @Select("select * from address_course where address_id=#{addressId}")
    CourseAddress getCourseAddressById(Integer addressId);

    @Select("select parent_id, parent_level, partner_id from parent where invite_code = #{inviteCode}")
    Parent getParentByInviteCode(String inviteCode);

    @Select("select partner_id from partner where invite_code = #{inviteCode}")
    Partner getPartnerByInviteCode(String inviteCode);

    @Update("update parent set parent_name = #{parentName}, sex = #{sex}, id_card = #{idCard}, invited_code = " +
            " #{invitedCode}, invited_partner_id = #{partnerId}, invite_code = #{ownInviteCode}," +
            " parent_level = 1, partner_id = #{partnerId} where parent_id = #{parentId}")
    void createParentOfPartner(@Param("parentId") Integer parentId,
                      @Param("parentName") String parentName,
                      @Param("sex") Integer sex,
                      @Param("idCard")String idCard,
                      @Param("invitedCode") String inviteCode,
                      @Param("ownInviteCode") String ownInviteCode,
                      @Param("partnerId") Integer partnerId);

    @Select("select pdf_address from  contract_pdf where person_id = #{personId} and  person_role = #{personRole} and state =1")
    List<String> getContractAddress(@Param("personId")Integer personId,@Param("personRole") Integer personRole );


    @Select("select * from contract_pdf cp inner join parent_contract pc on  pc.id = cp.parent_contract_id   where person_id = #{personId} and  person_role = #{personRole} and state =1   ")
    List<ParentContractBean> getParentContractList(@Param("personId")Integer personId,@Param("personRole") Integer personRole);

    @Update("update parent_contract set sign_image  = #{signImage},isSign =1  where id = #{Id}  ")
    void  editParentContract(@Param("signImage") String signImage,@Param("Id") Integer Id );

    @Select("select * from contract_pdf cp inner join parent_contract pc on  pc.id = cp.parent_contract_id   where contract_id = #{contractId}   ")
    @Results({
            @Result(column = "contract_id",property = "contractImages",many = @Many(select = "getContractImages"))
    })
    ParentContractBean getParentContractByid(Integer contractId);


    @Select("select image_address from contract_image where contract_id = #{contractId} ")
    List<String>  getContractImages(Integer contractId);

    @Select("select * from parent_contract where id = #{Id} ")
    ParentContract getParentContractById(Integer id);

    @Update("update contract_pdf set signed_pdf_address  = #{signedPdfAddress}  where contract_id = #{contractId}  ")
    void  editParentContractPdf(@Param("signedPdfAddress") String signedPdfAddress,@Param("contractId") Integer  contractId );

}

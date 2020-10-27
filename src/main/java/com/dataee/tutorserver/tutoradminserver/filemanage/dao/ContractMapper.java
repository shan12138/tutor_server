package com.dataee.tutorserver.tutoradminserver.filemanage.dao;

import com.dataee.tutorserver.entity.ContractPdf;
import com.dataee.tutorserver.entity.Person;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ContractRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.TeacherContractRequestBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件路径的存储和查询
 *
 * @author JinYue
 * @CreateDate 2019/5/5 22:47
 */
@Mapper
@Repository
public interface ContractMapper {
    /**
     * 保存指定教员的合同
     *
     * @param contract
     * @param roleId
     * @return
     */
    @Insert("insert into contract_pdf(contract_name,parent_contract_id,course_id, pdf_address, person_id, person_role) " +
            "values(#{contract.contractName},#{contract.parentContractId},#{contract.courseId}, #{contract.contractAddress},#{contract.personId}, #{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "contract.contractId", keyColumn = "contract_id")
    void insertContract(@Param("contract") ContractRequestBean contract, @Param("roleId") int roleId);

    /**
     * 获取资源路径
     *
     * @param contractId
     * @return
     */
    @Select("select pdf_address,signed_pdf_address from contract_pdf where contract_id = #{contractId} and state = 1")
    ContractPdf selectSourceAddressByContractNameAndPersonId(@Param("contractId") int contractId);


    /**
     * 删除合同pdf
     *
     * @param contractId
     * @return
     */
    @Update("update contract_pdf set state = 0 where contract_id= #{contractId} and state=1")
    int deleteContractByFilenameAndPersonIdAndRoleId(@Param("contractId") int contractId);


    /**
     * 删除所有改合同的图片
     *
     * @param ContractId
     * @return
     */
    @Update("update contract_image set state = 0 where state = 1 and contract_id = #{contractId}")
    int deleteContractImageByContractId(@Param("contractId") int ContractId);


    /**
     * 获取教员或者家长的合同列表
     *
     * @param personId
     * @return
     */
    @Select("select contract_id, contract_name, pdf_address,id_card, person_id, person_role, crt_date, upload_state, contract_pdf.state from " +
            "contract_pdf, role where person_role = role.role_id and role.role_name = #{role} and role.state = 1 and contract_pdf.state = 1 and person_id = #{personId}")
    @Results(id = "contractsMapper", value = {
            @Result(column = "{personId=person_id, personRole=person_role}", property = "person", javaType = Person.class,
                    many = @Many(select = "com.dataee.tutorserver.userserver.dao.PersonMapper.queryPersonById"))
    })
    List<ContractPdf> queryContractsByPerson(@Param("role") String role, @Param("personId") String personId);


    /**
     * 查询指定的合同Id
     *
     * @param name
     * @param adress
     * @param personId
     * @param roleId
     * @return
     */
    @Select("select contract_id from contract_pdf where contract_name = #{contractName} and pdf_address = #{contractAddress} " +
            "and person_id = #{personId} and person_role = #{roleId} and state = 1")
    Integer queryContractId(@Param("contractName") String name, @Param("contractAddress") String adress,
                            @Param("personId") Integer personId, @Param("roleId") Integer roleId);

    /**
     * 保存合同图片地址
     *
     * @param contractId
     * @param paths
     * @return
     */
    @Insert({"<script>",
            "insert into contract_image(contract_id, image_address) ",
            "values ",
            "<foreach collection='list' item='path' index='index' separator=','>",
            "(#{contractId}, #{path})",
            "</foreach>",
            "</script>"})
    Integer insertContractImages(@Param("contractId") int contractId, @Param("list") List<String> paths);


    /**
     * 同时删除合同的PDF文件和图片记录
     *
     * @param contractId
     * @return
     */
    @Update("update contract_pdf, contract_image set contract_pdf.state = 0, contract_image.state = 0\n" +
            "where contract_pdf.contract_id = #{contractId} and contract_pdf.contract_id = contract_image.contract_id")
    int deleteContractPdfAndImage(@Param("contractId") Integer contractId);


    /**
     * 更改合同上传的状态
     *
     * @param contractId
     * @return
     */
    @Update("update contract_pdf set upload_state = 1 where contract_id = #{contractId}")
    int updateUploadState(@Param("contractId") Integer contractId);


    /**
     * 更改contract_PDF中的number
     *
     * @param contractId
     * @param idCard
     * @return
     */
    @Update("update contract_pdf set id_card = #{idCard} where state = 1 and contract_id = #{contractId}")
    int setContractNumber(@Param("contractId") int contractId, @Param("idCard") String idCard);


    /**
     * 删除合同通过number
     *
     * @param idCard
     * @return
     */
    @Update("update contract_pdf set state = 0 where (id_card = #{idCard} or id_card = (select id_card from student where student_id = #{studentId} and state = 1)) and state = 1")
    int deleteContractByStudentNumberOrStudentId(@Param("idCard") String idCard, @Param("studentId") Integer studentId);

    /**
     * 清除学生编号
     *
     * @param idCard
     * @return
     */
    @Update("update student set id_card = '' where state = 1 and id_card = #{idCard}")
    int deleteStudentNumberByNumber(@Param("idCard") String idCard);

}

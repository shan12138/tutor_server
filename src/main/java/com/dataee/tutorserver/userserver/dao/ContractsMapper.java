package com.dataee.tutorserver.userserver.dao;

import com.dataee.tutorserver.entity.ContractPdf;
import com.dataee.tutorserver.entity.ParentContract;
import com.dataee.tutorserver.entity.Person;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对合同的操作
 *
 * @author JinYue
 * @CreateDate 2019/5/6 1:39
 */
@Mapper
@Repository
public interface ContractsMapper {
    /**
     * 根据文件名和人物ID获取到合同的资源路径
     *
     * @param id
     * @return
     */
    @Select("select image_address from contract_image where contract_id = #{id} and state = 1")
    List<String> selectResourceAddressByFilenameAndPersonId(@Param("id") int id);

    /**
     * 获取资源路径
     *
     * @param contractId
     * @return
     */
    @Select("select pdf_address from contract_pdf where contract_id = #{contractId} and state = 1")
    String selectSourceAddressByContractNameAndPersonId(@Param("contractId") int contractId);

    /**
     * 获取资源路径
     *
     * @param contractId
     * @return
     */
    @Select("select signed_pdf_address from contract_pdf where contract_id = #{contractId} and state = 1")
    String selectSourceAddress(@Param("contractId") int contractId);


    /**
     * 获取合同列表
     *
     * @param personId
     * @return
     */
    @Select("select contract_id, contract_name, pdf_address, person_id, person_role, crt_date, state from contract_pdf " +
            "where person_id = #{personId} and person_role= #{personRole} and state = 1")
    @Results(id = "contractMapper", value = {
            @Result(column = "{personId=person_id, personRole=person_role}", property = "person", javaType = Person.class,
                    many = @Many(select = "com.dataee.tutorserver.userserver.dao.PersonMapper.queryPersonById"))
    })
    List<ContractPdf> queryContractListByPersonId(@Param("personId") Integer personId, @Param("personRole") Integer personRole);

    @Select("select * from contract_pdf where contract_id = #{contractId}")
    @Results({
            @Result(property = "parentContract",column = "parent_contract_id",one =@One(select = "com.dataee.tutorserver.userserver.dao.ContractsMapper.getParentContractById") )
    })
    ContractPdf getContractPdf(Integer contractId);

    @Select("select * from parent_contract where id = #{id} ")
    ParentContract getParentContractById(Integer id);
}

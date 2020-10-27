package com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao;

import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.ClassAndHour;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.bean.UpdateInvitation;
import com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao.sqlprovider.QueryParentSqlProvider;
import com.dataee.tutorserver.tutoradminserver.teachermanage.dao.sqlprovider.QueryTeacherSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 15:23
 */
@Mapper
@Repository
public interface ParentManageMapper {
    @Select("select *from parent where state = #{state} order by parent_id desc")
    List<Parent> getParentList(Integer state);

    @Select("select *from parent where parent_id = #{parentId}")
    @Results(value = {
            @Result(property = "address", column = "parent_id", many = @Many(select = "getAddressOfParent"))
    })
    Parent getParentDetail(@Param("parentId") String parentId);

    @Select("select *from address where parent_id = #{parentId} and state = 1")
    List<Address> getAddressOfParent(@Param("parentId") String parentId);

    /**
     *家长申请检索
     * @param queryCondition
     * @param state
     * @param sex
     * @return
     */
    @SelectProvider(type = QueryParentSqlProvider.class, method = "queryParentByCondition")
    List<Parent> queryParent(@Param("queryCondition") String queryCondition, @Param("state") String state, @Param("sex") String sex);


    List<ClassAndHour> getClassAndHourOfParent(String parentId);

    /**
     * 正式家长
     * @return
     */
    @SelectProvider(type = QueryParentSqlProvider.class, method = "queryStudentByStudentNameAndSex")
    @Results(value = {
            @Result(property = "student", column = "student_id", many = @Many(select = "com.dataee.tutorserver.tutorpatriarchserver.dao.StudentMapper.getStudentById"),javaType = List.class),
            @Result(property = "partner", column = "partner_id", one = @One(select = "getPartnerById")),
            @Result(property = "parent", column = "invited_parent_id", one = @One(select = "getParentById")),
            @Result(property = "invitePartner", column = "invited_partner_id", one = @One(select = "getPartnerById"))
    })
    List<Parent> getAuthEdParentList(@Param("studentName")String studentName,@Param("sex")String sex );

    @Update("update address set state = 0 where parent_id = #{parentId}")
     void changeAddressState(int parentId);

    @Update("update parent set parent_level = #{parentLevel},partner_id = #{partnerId} where parent_id = #{parentId}")
    void changeInvitation(UpdateInvitation updateInvitation);

    @Select("select * from parent where parent_id =#{parentId}")
    Parent getParentById(Integer parentId);

    @Select("select * from partner where partner_id =#{partnerId}")
    Partner getPartnerById(Integer partnerId);

    @Select("select * from partner")
    List<Partner> getPartnerList();

    @Select("select distinct level from parent_level")
    List<ParentLevel> getParentLevelList();
}

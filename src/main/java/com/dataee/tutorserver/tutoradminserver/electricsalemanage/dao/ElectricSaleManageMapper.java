package com.dataee.tutorserver.tutoradminserver.electricsalemanage.dao;

import com.dataee.tutorserver.entity.IntentionCustomer;
import com.dataee.tutorserver.tutoradminserver.electricsalemanage.dao.sqlprovider.QueryIntentionCustomerSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/29 21:02
 */
@Mapper
@Repository
public interface ElectricSaleManageMapper {
    @Select("select * from intention_customer where state != 0")
    List<IntentionCustomer> getIntentionCustomer();

    @Select("select * from intention_customer where id = #{id} and state != 0")
    IntentionCustomer getIntentionCustomerDetailInfo(Integer id);


    @Update("update intention_customer set name = #{name}, sex = #{sex}, position = #{position}, " +
            "permanent_address = #{permanentAddress}, child_basic_info = #{childBasicInfo}, " +
            "weak_discipline = #{weakDiscipline}, tutor_sex = #{tutorSex}, tutor_demand = #{tutorDemand} where id = #{id}")
    Integer changeIntentionCustomer(IntentionCustomer intentionCustomer);

    @Insert("insert into intention_customer(name, sex, telephone, position, permanent_address, child_basic_info, " +
            "weak_discipline, tutor_sex, tutor_demand) values(#{name}, #{sex}, #{telephone}, #{position}, " +
            "#{permanentAddress}, #{childBasicInfo}, #{weakDiscipline}, #{tutorSex}, #{tutorDemand})")
    int createIntentionCustomer(IntentionCustomer intentionCustomer);

    @Update("update intention_customer set state = 2 where id = #{id}")
    void changeCustomerState(Integer id);

   @Select("select * from intention_customer where state = 2")
    List<IntentionCustomer> getCourseIntentionCustomer(@Param("queryCondition")String queryCondition,@Param("parentSex")String parentSex,@Param("tutorSex")String tutorSex );

    @SelectProvider(type = QueryIntentionCustomerSqlProvider.class, method = "getQueryConditionSql")
    List<IntentionCustomer> queryIntentionCustomer(@Param("queryCondition") String queryCondition,
                                                   @Param("parentSex") String parentSex,
                                                   @Param("teacherSex") String teacherSex,
                                                   @Param("state") String state);
}

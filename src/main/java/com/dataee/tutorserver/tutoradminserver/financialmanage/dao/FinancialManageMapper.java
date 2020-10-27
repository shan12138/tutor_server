package com.dataee.tutorserver.tutoradminserver.financialmanage.dao;

import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.entity.ParentInvitation;
import com.dataee.tutorserver.entity.Partner;
import com.dataee.tutorserver.entity.WithdrawalsRecord;
import com.dataee.tutorserver.tutoradminserver.financialmanage.dao.sqlprovider.QueryFinancialSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 13:40
 */
@Mapper
public interface FinancialManageMapper {
    @SelectProvider(type = QueryFinancialSqlProvider.class, method = "queryWithdraw")
    @Results(
            value = {
                    @Result(column = "partner_id", property = "partner",
                            one = @One(select = "getPartnerById")),
            }
    )
    List<WithdrawalsRecord> getWithdrawList(@Param("keyWord") String keyWord,
                                            @Param("partnerId") Integer partnerId,
                                            @Param("state") String state);

    @Select("select telephone, partner_id, partner_name, alipay_account from partner where partner_id = #{id}")
    Partner getPartnerById(int id);

    @Update("update withdrawals_record set state = #{state} where id = #{id}")
    void onlineDistribution(@Param("id") int id, @Param("state") String state);

    @Select("select * from withdrawals_record where id = #{id}")
    WithdrawalsRecord getWithDrawById(int id);

    @SelectProvider(type = QueryFinancialSqlProvider.class, method = "queryInvitationGiftParent")
    @Results(
            value = {
                    @Result(column = "parent_id", property = "parent",
                            one = @One(select = "getParentById")),
                    // 所属的合伙人
                    @Result(column = "partner_id", property = "partner",
                            one = @One(select = "getPartnerById")),
                    // 邀请自己的家长
                    @Result(column = "invited_parent_id", property = "invitedParent",
                            one = @One(select = "getParentById")),
            }
    )
    List<ParentInvitation> getInvitationGiftParentList(@Param("keyWord") String keyWord,
                                                       @Param("parentId") Integer parentId,
                                                       @Param("state") String state);


    @Select("select parent_id, telephone, parent_name, invite_success_time, parent_state from parent where parent_id = #{id}")
    Parent getParentById(int id);

//    @Select("select partner_name, partner.telephone from parent, partner where parent_id = #{id} and " +
//            "parent.partner_id = partner.partner_id")
//    Partner getPartnerByParentId(int id);

    @Update("update parent set parent_state = #{state} where parent_id = #{id}")
    void invitationParentGiftDistribution(@Param("id") int id, @Param("state") String state);

    @Insert("insert into bill(time, flow_type, kind, number, source, target, target_id) values(now(), " +
            "#{flowType}, #{kind}, #{number}, #{source}, #{target}, #{targetId})")
    void createWithdrawBill(@Param("flowType") String flowType, @Param("kind") String kind,
                            @Param("number") Double number, @Param("source") String source,
                            @Param("target") String target, @Param("targetId") Integer targetId);
}

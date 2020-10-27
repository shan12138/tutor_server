package com.dataee.tutorserver.tutoradminserver.financialmanage.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/15 16:16
 */
public class QueryFinancialSqlProvider {
    public String queryWithdraw(@Param("keyWord") String keyWord,
                                @Param("partnerId") Integer partnerId,
                                @Param("state") String state) {
        StringBuilder sql = new StringBuilder("select withdrawals_record.*, partner.partner_name" +
                " from withdrawals_record, partner where " +
                " partner.partner_id = withdrawals_record.partner_id");
        if(keyWord != null) {
            sql.append(" and partner_name like concat('%', #{keyWord}, '%')");
        }
        if(partnerId != null) {
            sql.append(" and partner.partner_id = #{partnerId}");
        }
        if(state != null) {
            sql.append(" and withdrawals_record.state = #{state}");
        }
        sql.append(" order by withdrawals_record.state, application_time desc");
        return sql.toString();
    }

    public String queryInvitationGiftParent(@Param("keyWord") String keyWord,
                                            @Param("parentId") Integer parentId,
                                            @Param("state") String state) {
        StringBuilder sql = new StringBuilder("select parent_id, partner_id," +
                " invited_parent_id, invite_success_time " +
                "from parent where partner_state = '已签约' and invited_parent_id is not null");
        if(keyWord != null) {
            sql.append(" and parent_name like concat('%', #{keyWord}, '%')");
        }
        if(parentId != null) {
            sql.append(" and temp.parent_id = #{parentId}");
        }
        if(state != null) {
            if(state.equals("待发放")) {
                sql.append(" and parent_state = \"未得礼品\"");
            }
            else if(state.equals("已发放")) {
                sql.append(" and parent_state = \"已得礼品\"");
            }
        }
        sql.append(" order by invite_success_time desc ");
        return sql.toString();
    }
}

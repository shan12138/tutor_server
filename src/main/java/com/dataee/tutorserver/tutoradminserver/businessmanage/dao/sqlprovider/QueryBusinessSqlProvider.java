package com.dataee.tutorserver.tutoradminserver.businessmanage.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/15 15:19
 */
public class QueryBusinessSqlProvider {
    public String queryPartnerByCondition(@Param("keyWord") String keyWord,
                                         @Param("telephone") String telephone) {
        StringBuilder sql = new StringBuilder("SELECT * from partner where 1 = 1");
        if(keyWord != null && !keyWord.equals("")) {
            sql.append(" and (partner_name like concat('%', #{keyWord}, '%') " +
                    "or invite_code like concat('%', #{keyWord}, '%') " +
                    "or  alipay_account like concat('%', #{keyWord}, '%') " +
                    "or  alipay_name like concat('%', #{keyWord}, '%'))");
        }
        if(telephone != null && !telephone.equals("")){
            sql.append(" and telephone like concat('%', #{telephone}, '%')");
        }
        return sql.toString();
    }

    public String queryInvitedParent(@Param("keyWord") String keyWord,
                                     @Param("telephone") String telephone,
                                     @Param("state") String state,
                                     @Param("limit") Integer limit,
                                     @Param("page") Integer page) {
        StringBuilder sql = new StringBuilder("SELECT * from parent_invitation where 1 = 1 ");
        if(keyWord != null) {
            sql.append(" and parent_name like concat('%', #{keyWord}, '%') ");
        }
        if(telephone != null) {
            sql.append(" and telephone like concat('%', #{telephone}, '%') ");
        }
        if(state != null) {
            sql.append(" and admin_state = #{state} ");
        }
        sql.append(" order by id desc limit #{page}, #{limit}");
        return sql.toString();
    }

    public String queryMatchedParent(@Param("id") Integer id, @Param("partnerId") Integer partnerId) {
        StringBuilder sql = new StringBuilder("select parent.parent_id, parent_invitation.id as parent_invitation_id, product_id, " +
                "parent.invited_partner_id, parent.parent_name, parent.telephone, parent.parent_level, " +
                "parent.invited_parent_id, parent.partner_id, parent_invitation.admin_id, admin_state" +
                " from parent, parent_invitation where parent.parent_id = parent_invitation.invited_parent_id" +
                "  and parent_invitation.id = #{id}");
        if(partnerId != null) {
            sql.append(" and parent.partner_id = #{partnerId}");
        }
        return sql.toString();
    }
}

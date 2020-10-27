package com.dataee.tutorserver.tutoradminserver.businessmanage.dao;

import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutoradminserver.businessmanage.bean.PartnerResponseBean;
import com.dataee.tutorserver.tutoradminserver.businessmanage.dao.sqlprovider.QueryBusinessSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 16:59
 */
@Mapper
public interface BusinessManageMapper {
    @SelectProvider(type = QueryBusinessSqlProvider.class, method = "queryPartnerByCondition")
    @Results(
            id = "getPartnerNum",
            value = {
                    @Result(column = "partner_id", property = "partnerId"),
                    @Result(column = "partner_id", property = "inviteParentNum",
                            one = @One(select = "getInviteParentNum")),
                    @Result(column = "partner_id", property = "inviteTeacherNum",
                            one = @One(select = "getInviteTeacherNum")),
            }
    )
    List<PartnerResponseBean> getPartnerList(@Param("keyWord") String keyWord, @Param("telephone") String telephone);

    @Select("select count(*) from parent_invitation where partner_id = #{id}")
    Integer getInviteParentNum(int id);

    @Select("select count(*) from teacher_invitation where partner_id = #{id}")
    Integer getInviteTeacherNum(int id);

    @Select("select * from partner where partner_id = #{id}")
    Partner getPartnerById(Integer id);

    @Update("update partner set state = #{state} where partner_id = #{id}")
    void changePartnerState(@Param("id") Integer id, @Param("state") String state);

    @Select("select id, username, user_id from user where username = #{telephone} and role_id = 60 and state = 1")
    User getUserByTelephone(String telephone);

    @Insert("insert into partner(partner_name, telephone, " +
            "we_chat_user_id, invite_code) values(#{partnerName}, #{telephone}, " +
            "#{weChatUserId}, #{inviteCode})")
    void createPartner(Partner partner);

    @Select("select * from parent_level where level = #{level} and product_id = #{productId}")
    ParentLevel getParentLevel(@Param("level") int level, @Param("productId") int productId);

    @Select("select parent_name from parent where parent_id = #{id}")
    Parent getInviteParent(int id);

    @Select("select partner_name from partner where partner_id = #{id}")
    Partner getInvitePartner(int id);

    @Select("select admin_name from admin where id = #{id}")
    Administrator getConsultant(int id);

    @Select("select * from parent where telephone = #{telephone}")
    List<Parent> getParentByTelephone(String telephone);

    @Select("select invited_parent_id from parent_invitation where id = #{id}")
    Integer getInvitedParentId(int id);

    @Update("update parent_invitation set invited_parent_id = #{parentId}, " +
            "admin_state = #{state} where id = #{id}")
    void matchParentAccount(@Param("id") Integer id, @Param("parentId") Integer parentId, @Param("state") String state);

    @Select("select * from admin, admin_auth where admin.admin_id = admin_auth.admin_id " +
            "and role_id = 105")
    List<Administrator> getConsultantList();

    @Update("update parent_invitation set admin_id = #{consultantId}, admin_state = #{state} " +
            "where id = #{parentInvitationId}")
    void distributionConsultant(@Param("parentInvitationId") Integer parentInvitationId,
                                @Param("consultantId") Integer consultantId,
                                @Param("state") String state);

    @Select("select we_chat_user_id, nick_name from we_chat_user where user_id = #{userId}")
    WeChatUser getWeChatUser(String userId);

    @Select("select * from partner where telephone = #{telephone}")
    Partner getPartnerByTelephone(String telephone);

    @Select("select * from we_chat_user where we_chat_user_id = #{weChatUserId}")
    WeChatUser getWeChatUserById(Integer weChatUserId);

    @Select("select * from partner where we_chat_user_id = #{id}")
    Partner getPartnerByWeChatUserId(Integer id);

    @SelectProvider(type = QueryBusinessSqlProvider.class, method = "queryInvitedParent")
    List<ParentInvitation> getParentInvitations(@Param("keyWord") String keyWord,
                                                @Param("telephone") String telephone,
                                                @Param("state") String state,
                                                @Param("limit") Integer limit,
                                                @Param("page") Integer page);

    @Select("SELECT id as parent_invitation_id, product_id, \n" +
            "parent_name, telephone, parent_id, partner_id, \n" +
            "admin_state, admin_id \n" +
            "FROM parent_invitation\n" +
            "WHERE id = #{id}")
    @Results(
            value = {
                    // 获取家长邀请的邀请人
                    @Result(column = "parent_id", property = "parent",
                            one = @One(select = "getInviteParent")),
                    // 获取合伙人邀请的邀请人
                    @Result(column = "partner_id", property = "invitePartner",
                            one = @One(select = "getInvitePartner")),
                    @Result(column = "admin_id", property = "consultant",
                            one = @One(select = "getConsultant")),
            }
    )
    Parent getNotMatchedParent(int id);

    @SelectProvider(type = QueryBusinessSqlProvider.class, method = "queryMatchedParent")
    @Results(
            value = {
                    @Result(column = "{level = parent_level, productId = product_id" +
                            "}", property = "parentLevelClass",
                            one = @One(select = "getParentLevel")),
                    // 获取家长邀请的邀请人
                    @Result(column = "invited_parent_id", property = "parent",
                            one = @One(select = "getInviteParent")),
                    // 获取合伙人邀请的邀请人
                    @Result(column = "invited_partner_id", property = "invitePartner",
                            one = @One(select = "getInvitePartner")),
                    // 获取所属的合伙人
                    @Result(column = "partner_id", property = "partner",
                            one = @One(select = "getInvitePartner")),
                    // 获取咨询师
                    @Result(column = "admin_id", property = "consultant",
                            one = @One(select = "getConsultant")),
            }
    )
    Parent getMatchedParent(@Param("id") Integer id, @Param("partnerId") Integer partnerId);

    @Select("select id from parent_invitation where invited_parent_id = #{parentId}")
    Integer getInviteParentByParentId(Integer parentId);

    @Select("select * from parent_invitation where id = #{id}")
    @Results(
            value = {
                    @Result(column = "product_attribute_value", property = "productAttributeValue")
            }
    )
    ParentInvitation getInviteParentById(Integer id);

    @Select("SELECT count(0) FROM parent_invitation ")
    Integer getSumCountParentInvitation();
}

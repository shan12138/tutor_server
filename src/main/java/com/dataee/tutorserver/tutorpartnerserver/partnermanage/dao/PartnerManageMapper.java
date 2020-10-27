package com.dataee.tutorserver.tutorpartnerserver.partnermanage.dao;

import com.dataee.tutorserver.entity.*;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean.Commission;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean.ParentDetailInfo;
import com.dataee.tutorserver.tutorpartnerserver.partnermanage.bean.PartnerParentRequestBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/12 15:53
 */
@Mapper
public interface PartnerManageMapper {
    @Select("select * from partner where we_chat_user_id = #{weChatUserId}")
    Partner getPartnerByWeChatUserId(Integer weChatUserId);

    @Select("select count(*) from parent where invited_partner_id = #{partnerId}")
    Integer getInviteParentNum(@Param("partnerId") Integer partnerId);

    @Select("select count(*) from parent where invited_partner_id = #{partnerId} and partner_state = #{partnerState}")
    Integer getSignNum(@Param("partnerId") Integer partnerId,
                             @Param("partnerState") String partnerState);

    @Select("SELECT sum(total_class_hour*price*discount/100) AS sumMoney FROM course_hour_record WHERE course_id IN " +
            "(SELECT course_id FROM course WHERE parent_id IN " +
            "(SELECT parent_id FROM parent WHERE invited_partner_id = #{partnerId}) and state != 0) AND is_free = 0")
    Double getSumMoney(@Param("partnerId") Integer partnerId);

    @Select("SELECT course_hour_record.id, course.course_id, total_class_hour*price*discount AS money, parent_id, product_id \n" +
            "FROM course_hour_record, course WHERE course.course_id IN\n" +
            "(SELECT course_id FROM course WHERE parent_id IN\n" +
            "(SELECT parent_id FROM parent WHERE invited_partner_id = #{partnerId} " +
            ") and state != 0) AND is_free = 0\n" +
            "AND course_hour_record.course_id = course.course_id")
    List<Commission> getCommissions(@Param("partnerId") Integer partnerId);

    @Select("select parent_level.* from parent_level, parent where parent.parent_id  = #{parentId}" +
            " and parent.parent_level = parent_level.level and product_id = #{productId}")
    ParentLevel getParentLevelByParentId(@Param("parentId") Integer partnerId, @Param("productId") Integer productId);

    @Select("select parent_name, telephone, id, product_id from parent_invitation " +
            "where partner_id = #{partnerId} order by id desc")
    @Results({
            @Result(property = "product", column = "product_id",
                    one = @One(select = "getProductById")),
    })
    List<ParentInvitation> getParentInvitationList(@Param("partnerId") int partnerId);

    @Select("select * from product where id = #{id}")
    Product getProductById(int id);

    @Select("select parent_id, parent_name, telephone, partner_state from parent " +
            "where invited_partner_id = #{partnerId} " +
            " order by partner_state asc, " +
            "invite_success_time desc")
    List<ParentInvitation> getParentInvitationRegisterList(@Param("partnerId") int partnerId);

    @Select("select course.parent_id, course_id, course_name, parent_name, telephone from course, parent " +
            "where parent.parent_id = course.parent_id and parent.parent_id = #{parentId} and course.state != 0")
    @Results(
            value = {
                    @Result(column = "course_id", property = "sumMoney",
                            one = @One(select = "getCourseSumMoney")),
                    @Result(column = "course_id", property = "totalClassHour",
                            one = @One(select = "getTotalClassHour")),
                    @Result(column = "course_id", property = "consumeClassHour",
                            one = @One(select = "getConsumeClassHour")),
                    @Result(column = "course_id", property = "commissionList",
                            one = @One(select = "getCommissionList")),
            }
    )
    List<ParentDetailInfo> getParentInfoDetail(@Param("parentId") int parentId);

    @Select("select sum(total_class_hour*price*discount/100) from course_hour_record " +
            "where course_id = #{id} and is_free = 0")
    Double getCourseSumMoney(int id);

    @Select("select sum(total_class_hour) from course_hour_record where course_id = #{id}")
    Integer getTotalClassHour(int id);

    @Select("select sum(consume_class_hour) from course_hour_record where course_id = #{id}")
    Integer getConsumeClassHour(int id);

    @Select("select course.course_id, total_class_hour*price*discount as money, " +
            "consume_class_hour*price*discount as realMoney, " +
            "course.parent_id, product_id from course_hour_record, course" +
            " where course.course_id = #{courseId} and course.state != 0 and is_free = 0 and course.course_id" +
            " = course_hour_record.course_id and course_hour_record.state != 0")
    List<Commission> getCommissionList(@Param("courseId") int courseId);

    @Insert("insert into parent_invitation(parent_name, telephone, partner_code, partner_id, " +
            "product_attribute_value, product_id) values(#{parent.parentName}, #{parent.telephone}, " +
            "#{inviteCode}, #{partnerId}, #{parent.productAttributeValue}, #{parent.productId})")
    void inviteParent(@Param("partnerId") Integer partnerId, @Param("inviteCode") String inviteCode,
                      @Param("parent") PartnerParentRequestBean parent);

    @Select("select * from partner where we_chat_user_id = #{weChatUserId}")
    Partner getPartnerInfo(Integer weChatUserId);

    @Update("update partner set alipay_account = #{alipayAccount}, alipay_name = #{alipayName} " +
            "where we_chat_user_id = #{weChatUserId}")
    void updatePartnerInfo(@Param("weChatUserId") Integer weChatUserId,
                           @Param("alipayAccount") String alipayAccount,
                           @Param("alipayName") String alipayName);

    @Select("select id from product")
    List<Product> getProducts();

    @Select("select parent_id from parent where partner_id = #{partnerId}")
    List<Parent> getParents(Integer partnerId);

    @Select("select course_id from course, parent " +
            "where parent.parent_id = course.parent_id and product_id = #{productId} and parent.parent_id = #{parentId}" +
            " and course.state != 0")
    @Results(
            value = {
                    @Result(column = "course_id", property = "commissionList",
                            one = @One(select = "getCommissionList")),
            }
    )
    List<ParentDetailInfo> getMoney(@Param("parentId") int parentId, @Param("productId") int productId);

    @Select("select sum(withdrawals_money) from withdrawals_record where partner_id = #{partnerId}" +
            " and state = #{state}")
    Double getWithdrawingMoney(@Param("partnerId") Integer partnerId, @Param("state") String state);

    @Insert("insert into withdrawals_record(partner_id, withdrawals_money, application_time) " +
            "values(#{partnerId}, #{number}, now())")
    void createWithdrawMoney(@Param("partnerId") Integer partnerId, @Param("number") Integer number);

    @Select("select * from withdrawals_record where partner_id = #{partnerId}")
    List<WithdrawalsRecord> getWithdrawDetail(Integer partnerId);

    @Select("select sum(withdrawals_money) from withdrawals_record where partner_id = #{partnerId}" +
            " and state != #{state}")
    Double getWithdrawedMoney(@Param("partnerId") Integer partnerId, @Param("state") String state);

    @Select("select * from bill where target_id = #{partnerId} and kind != #{state}")
    List<Bill> getBills(@Param("partnerId") Integer partnerId, @Param("state") String state);

    @Select("select * from parent_level where level = (select max(level) from parent_level " +
            "where product_id = #{id}) and product_id = #{id}")
    ParentLevel getMaxParentLevel(Integer id);

    @Select("select telephone from we_chat_user where we_chat_user_id = #{weChatUserId}")
    String getPartnerTelephone(Integer weChatUserId);
}

package com.dataee.tutorserver.tutorpatriarchserver.dao;

import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.entity.ParentInvitation;
import com.dataee.tutorserver.entity.Product;
import com.dataee.tutorserver.tutorpatriarchserver.bean.ParentInvitationRequestBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 10:27
 */
@Mapper
public interface InvitationMapper {
    @Select("select * from parent where parent_id = #{parentId}")
    Parent getParentById(Integer parentId);

    @Insert("insert into parent_invitation(parent_name, telephone, student_name, student_sex," +
            " parent_invitation_code, parent_id, product_attribute_value, product_id) values(" +
            "#{invitation.parentName}, #{invitation.telephone}, #{invitation.studentName}, " +
            "#{invitation.studentSex}, #{parentCode}, #{parentId}, #{invitation.productAttributeValue}, " +
            "#{invitation.productId})")
    void inviteParent(@Param("parentId") Integer parentId,
                      @Param("parentCode") String parentCode,
                      @Param("invitation") ParentInvitationRequestBean parentInvitation);

    @Select("select parent_name, parent_sex, product_id, telephone, product_name" +
            " from parent_invitation, product where parent_id = #{parentId}" +
            " and parent_invitation.product_id = product.id")
    @Results({
            @Result(property = "product", column = "product_id",
                    one = @One(select = "getProductById")),
    })
    List<ParentInvitation> getInvitationList(Integer parentId);

    @Select("select parent_name, parent_state, telephone from parent where invited_code = #{code}")
    List<ParentInvitation> getInvitationRegisterList(String code);

    @Select("select * from product where id = #{id}")
    Product getProductById(int id);

    @Select("select count(*) from parent where invited_parent_id = #{parentId}")
    Integer getInviteParentNum(Integer parentId);

    @Select("select count(*) from parent where invited_parent_id = #{parentId} and partner_state = #{partnerState}")
    Integer getSigningNum(@Param("parentId") Integer parentId, @Param("partnerState") String partnerState);

    @Select("select invite_code from parent where parent_id = #{parentId}")
    String getInviteCode(Integer parentId);
}

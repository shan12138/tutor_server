package com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao;

import com.dataee.tutorserver.entity.Parent;
import com.dataee.tutorserver.entity.ParentInvitation;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/7 10:29
 */
@Mapper
public interface ParentInvitationMapper {
    @Select("SELECT * FROM parent_invitation WHERE admin_id = #{adminId}")
    List<ParentInvitation> getInvitationList(Integer adminId);

    @Update("update parent_invitation set admin_state = #{state} where id = #{id}")
    void updateInviteParentAdminState(@Param("id") Integer id, @Param("state") String state);

    @Select("select parent_name, parent_id, telephone from parent where parent_id = #{id}")
    Parent getParentTeleById(int id);

}

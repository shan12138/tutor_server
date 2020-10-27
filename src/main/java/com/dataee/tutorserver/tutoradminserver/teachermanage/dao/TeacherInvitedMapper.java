package com.dataee.tutorserver.tutoradminserver.teachermanage.dao;

import com.dataee.tutorserver.entity.TeacherInvitation;
import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.InviteTeacherGift;
import com.dataee.tutorserver.tutoradminserver.teachermanage.dao.sqlprovider.QueryTeacherSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherInvitedMapper {
    @SelectProvider(type = QueryTeacherSqlProvider.class, method = "queryTeacherInviteByCondition")
    @Results({
            @Result(property = "teacher", column = "teacher_id",
                many = @Many(select = "com.dataee.tutorserver.tutoradminserver.teachermanage.dao.TeacherManageMapper.getTeacherById")),
            @Result(property = "partner", column = "partner_id",
                    many = @Many(select = "com.dataee.tutorserver.tutoradminserver.patriarchmanage.dao.ParentManageMapper.getPartnerById")),
    })
    public List<TeacherInvitation>  getTeacherInviteList(@Param("queryCondition") String queryCondition,
                                                         @Param("telephone") String telephone,
                                                         @Param("partnerId") Integer partnerId,
                                                         @Param("status") String status);


    @SelectProvider(type = QueryTeacherSqlProvider.class, method = "queryTeacherGiftInviteByCondition")
    public List<InviteTeacherGift>  getTeacherGiftInviteList(@Param("queryCondition") String queryCondition,
                                                             @Param("invitePerson") Integer invitePerson,
                                                             @Param("status") String status);


    @Update("update  invitation_teacher_gift SET  `status` =\"已发放\" WHERE id =#{id}")
     void  updateInviteTeacherGiftStatus(Integer id);

}

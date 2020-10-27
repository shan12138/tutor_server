package com.dataee.tutorserver.tutoradminserver.adminmanage.dao;

import com.dataee.tutorserver.tutoradminserver.adminmanage.bean.AdminRequestBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author JinYue
 * @CreateDate 2019/5/21 18:51
 */
@Repository
@Mapper
public interface AdminRegisterMapper {

    /**
     * 添加管理员
     *
     * @param crtId
     * @param adminInfo
     * @return
     */
    @Insert("insert into admin(admin_id, admin_name, account, password, crt_id, position, department_id, remark) " +
            "values(#{adminInfo.adminId}, #{adminInfo.adminName},#{adminInfo.account},#{adminInfo.password}," +
            "#{crtId}, #{adminInfo.position}, #{adminInfo.departmentId}, #{adminInfo.remark})")
    int  addNewAdmin(@Param("crtId") String crtId, @Param("adminInfo") AdminRequestBean adminInfo);

    /**
     * 添加管理员身份
     *
     * @param crtId
     * @param roleId
     * @param adminId
     * @return
     */
    @Insert("insert into admin_auth(admin_id, role_id, crt_id)values(#{adminId}, #{roleId}, #{crtId})")
    int addAdminAuth(@Param("crtId") String crtId, @Param("roleId") Integer roleId, @Param("adminId") String adminId);
}

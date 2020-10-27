package com.dataee.tutorserver.config;


import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.UserPrincipals;
import com.dataee.tutorserver.entity.Permission;
import com.dataee.tutorserver.entity.Role;
import com.dataee.tutorserver.tutoradminserver.adminmanage.service.IAdminLoginService;
import com.dataee.tutorserver.tutoradminserver.adminmanage.service.impl.AdminLoginServiceImpl;
import com.dataee.tutorserver.tutoradminserver.rolemanage.service.IRoleManageService;
import com.dataee.tutorserver.userserver.service.IPersonService;
import com.dataee.tutorserver.userserver.service.IUserService;
import com.dataee.tutorserver.userserver.service.impl.PersonServiceImpl;
import com.dataee.tutorserver.userserver.service.impl.UserServiceImpl;
import com.dataee.tutorserver.utils.EncodeUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义身份认证的条件
 *
 * @Author Jinyue
 * @CreateDate 2019/4/18
 */
public class ShiroRealm extends AuthorizingRealm {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    private IPersonService personService;

    public void setPersonService(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @Autowired
    private IAdminLoginService adminLoginService;

    @Autowired
    private IRoleManageService roleManageService;

    public void setAdminLoginService(AdminLoginServiceImpl adminLoginService) {
        this.adminLoginService = adminLoginService;
    }


    /**
     * stop:禁用
     * register:ing 注册用户
     * register:ed 注册完成基本信息，可以考试
     * auth:ing 考试完成
     * auth:ed 认证成为正式成员
     */
    private final String[] permissions = {"stop", "register:ing", "register:ed", "auth:ing", "auth:ed"};
    private final String ADMIN_ROLE = "admin";
    private final String ADMIN_DEFAULT_PERMISSION = "auth:ed";

    /**
     * 判断用户是否在缓存记录(即Shiro是否Remember userserver)和登录
     *
     * @param token 该凭证来自客户端传来的用户信息
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        logger.info(userToken.toString());
        String username = userToken.getUsername();
        String role = userToken.getHost();
        UserPrincipals principals = createPrincipals(username, role);
        if (principals != null) {
            principals.setCurrRole(role);
            //获取用户口令
            String userId = principals.getUserId();
            String secret = getSecret(userId, role);
            if (secret != null) {
                //该对象会记录在Shiro中认为是info,登录和缓存判断时使用
                SimpleAuthenticationInfo simpleAuthenticationInfo =
                        new SimpleAuthenticationInfo(principals, secret, EncodeUtil.getSalt(userId), getName());
                return simpleAuthenticationInfo;
            }
        }
        return null;
    }

    /**
     * 定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
     *
     * @param principals 缓存中的用户凭证
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserPrincipals userPrincipals = (UserPrincipals) super.getAvailablePrincipal(principals);
        if (userPrincipals != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Role> roles = userPrincipals.getRoles();
            if (roles != null) {
                Set<String> roleSet = roles.stream().map(role -> role.getRoleName()).collect(Collectors.toSet());
                //存放用户角色
                info.setRoles(roleSet);
            } else {
                return null;
            }
            try {
                if (!ADMIN_ROLE.equals(userPrincipals.getCurrRole())) {
                    //获取当前用户的状态
                    Role role = userPrincipals.getRoles().get(0);
                    Integer state = personService.getPersonState(userPrincipals.getPersonId(), role.getRoleName());
                    String permission = permissions[state];
                    Set<String> permissionSet = new HashSet<>();
                    permissionSet.add(permission);
                    //存放普通用户的权限
                    info.setStringPermissions(permissionSet);
                }
                else{
                    Role role = userPrincipals.getRoles().get(0);
                    List<Permission> permissionList = roleManageService.getPermissionsOfCurrentUser(role.getRoleId());
                    Set<String> permissionSet = new HashSet<>();
                    for(Permission permission: permissionList) {
                        permissionSet.add(permission.getName());
                    }
                    permissionSet.add(ADMIN_DEFAULT_PERMISSION);
                    // 存放管理员权限
                    info.setStringPermissions(permissionSet);
                }
            }
            catch (BaseServiceException bse) {
                logger.error(bse.getMessage(), bse);
                return null;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return null;
            }
            return info;
        }
        return null;
    }

    private UserPrincipals createPrincipals(String username, String role) {
        UserPrincipals userPrincipals = null;
        if (ADMIN_ROLE.equals(role)) {
            userPrincipals = adminLoginService.getPrincipals(username, role);
            Integer personId = userPrincipals.getId();
            userPrincipals.setPersonId(personId);
        } else {
            userPrincipals = userService.getPrincipals(username, role);
        }
        return userPrincipals;
    }


    private String getSecret(String userId, String role) {
        if (ADMIN_ROLE.equals(role)) {
            return adminLoginService.findSecretByAdminId(userId);
        } else {
            return userService.findSecretByUserId(userId);
        }
    }

}

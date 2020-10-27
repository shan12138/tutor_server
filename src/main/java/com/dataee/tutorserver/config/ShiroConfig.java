package com.dataee.tutorserver.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Value("${session.sessionTimeoutClean}")
    private Long sessionTimeoutClean;

    /**
     * 该方法默认使用SimpleCredentialsMatcher
     * 但是该方法只是将token中的密码和数据库中的密码直接匹配
     * 如果客户端传来的密码和数据库中的密码形式相同则可用
     * 如果密码是经过加密处理则要使用HashedCredentialsMatcher
     * 特殊功能可以重写该方类中的密码认证的方法
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher shiroHashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(2);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * 自定义验证方法加入到容器中
     * 当前使用的不是框架默认的简单密码匹配方法
     * 该方是MD5加盐的非对称加密算法
     * 为了防止加密碰撞于是对密码进行加盐处理
     *
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        //加入加密算法
        shiroRealm.setCredentialsMatcher(shiroHashedCredentialsMatcher());
        return shiroRealm;
    }


    /**
     * Shiro的核心管理部分
     * public SecurityManager securityManager()
     * 上
     * @return述写法会产生bean出图
     *      *
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(shiroRealm());
        //设置session的时间
        //defaultWebSecurityManager.setSessionManager(sessionManager());
        return defaultWebSecurityManager;
    }

    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        //    配置全局会话的超时时间，默认是30分钟
        sessionManager.setGlobalSessionTimeout(15552000000L);
        //是否开启定时调度器进行检测过期session 默认为true
        //sessionManager.setSessionValidationSchedulerEnabled(true);
        // 定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
        //sessionManager.setSessionValidationInterval(sessionTimeoutClean);
        return sessionManager;
    }


    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", new ShiroUserFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        //拦截器
        Map<String, String> map = new LinkedHashMap<>();
        //对所有用户认证,路由不存在时不能预定义，否则Tomcat无法启动
        //不用登陆即可访问的页面
        //1、静态资源
        map.put("/v2/api-docs", "anon");
        map.put("/configuration/ui", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/webjars/**", "anon");
        map.put("/*.html", "anon");
        map.put("/**/*.css", "anon");
        map.put("/**/*.js", "anon");
        map.put("/**/*.png", "anon");
        map.put("/**/*.jpg", "anon");
        map.put("/**/*.jpeg", "anon");
        map.put("/**/*.html", "anon");
        //软件中无需认证的页面
        map.put("/*/verification/*", "anon");
        map.put("/*/user", "anon");
        map.put("/**/verCode", "anon");
        map.put("/*/account/**", "anon");
        map.put("/wx/user/**", "anon");
        map.put("/admin/login", "anon");
        //全部 需要登录后操作
        map.put("/**", "authc");
        //登出
        map.put("/teacher/logout", "anon");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/*/login");
        //错误页面，认证不通过跳转
        //shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }


    /**
     * 加入注解的使用，不加入这个注解不生效
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}

package com.cn.shiro.config;

import com.cn.shiro.filter.JwtFilter;
import com.cn.shiro.filter.MyRolesAuthorizationFilter;
import com.cn.shiro.service.ShiroService;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 */
@Configuration
public class ShiroConfig {
    @Bean
    public SecurityManager securityManager(MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 SecurityManager，并注入 shiroRealm
        securityManager.setRealm(myRealm);

        /*
         * 关闭shiro自带的session，详情见文档
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 过滤器工厂配置
     * @param securityManager
     * @param config
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, ShiroService config) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filters = new HashMap<>();
        filters.put( "zqRoles", new MyRolesAuthorizationFilter() );
        filters.put("jwt", new JwtFilter());
        factoryBean.setFilters(filters);
        //鉴权失败
        factoryBean.setUnauthorizedUrl("/auth/402");
        factoryBean.setSecurityManager(securityManager);
        //factoryBean.setLoginUrl("/auth/401");

        /*
         * 自定义url规则
         */
        factoryBean.setFilterChainDefinitionMap(config.loadFilterChainDefinitionMap());
        return factoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
   // @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager  securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}

package com.cn.config;

import com.cn.filter.JWTAuthenticationFilter;
import com.cn.filter.JWTAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * 登录拦截全局配置
 *
 */
@Slf4j
@Configuration
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Resource
    private UrlForbiddenHandler accessDeniedHandler; //自定义权限不足处理器：返回状态码403

    @Resource
    private SlefLogoutSuccessHandler logoutSuccessHandler; //自定义注销成功处理器：返回状态码200

    @Resource
    private SelfUserDetailsService selfUserDetailsService; //登录处理器

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/common/**"); //无条件允许访问
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        //设置密码加密方式
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置数据库密码加密方式
        auth.userDetailsService(selfUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // url权限认证处理
        http.authorizeRequests()
                .anyRequest()
                .access("@rbacauthorityservice.hasPermission(request,authentication)")
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)  // 无权访问时：返回状态码403
                .authenticationEntryPoint(new JWTAuthenticationEntryPoint());   // 未登录时，或者token过期，返回状态码401

        // 开启自动配置的注销功能
        http.logout() //用户注销, 清空session
                .logoutUrl("/doLogout") //自定义注销请求路径
                .logoutSuccessHandler(logoutSuccessHandler); //注销成功处理器(前后端分离)：返回状态码200

        http.csrf().disable();
    }
}

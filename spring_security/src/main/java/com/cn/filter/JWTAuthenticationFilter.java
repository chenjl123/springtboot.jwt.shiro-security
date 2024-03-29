package com.cn.filter;

import com.cn.config.SelfUserDetails;
import com.cn.util.Constants;
import com.cn.util.GsonUtil;
import com.cn.util.JwtTokenUtil;
import com.cn.util.UrlResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * 验证用户名密码正确后 生成一个token并将token返回给客户端
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/doLogin");
    }

    /**
     * 验证操作 接收并解析用户凭证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        // 创建一个token并调用authenticationManager.authenticate()让spring-security去进行验证
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getParameter("username"),request.getParameter("password")));
    }

    /**
     * 验证【成功】后调用的方法
     * 若验证成功 生成token并返回
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,HttpServletResponse response,FilterChain chain,Authentication authResult) throws IOException {
        SelfUserDetails user= (SelfUserDetails) authResult.getPrincipal();

        // 从User中获取权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // 创建Token
       // System.out.println("登录设置token角色："+authorities.toString());
        //user.getAuthorities()
        String token = JwtTokenUtil.createToken(user.getUsername(), user.getRoles());

        // 让浏览器能访问到其它响应头
        response.addHeader("Access-Control-Expose-Headers","token");

        // 设置编码 防止乱码问题
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // 在请求头里返回创建成功的token
        // 设置请求头为带有"Bearer "前缀的token字符串
        response.setHeader("token", JwtTokenUtil.TOKEN_PREFIX + token);

        // 处理编码方式 防止中文乱码
        response.setContentType("text/json;charset=utf-8");
        // 将反馈塞到HttpServletResponse中返回给前台
        UrlResponse sresponse = new UrlResponse();
        sresponse.setCode(Constants.SUCCESS);
        sresponse.setMessage("登录成功");
        response.getWriter().write(GsonUtil.GSON.toJson(sresponse));
    }

    /**
     * 验证【失败】调用的方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String returnData="";
        // 账号过期
        if (failed instanceof AccountExpiredException) {
            returnData="账号过期";
        }
        // 用户不存在
        else if (failed instanceof UsernameNotFoundException) {
            returnData="用户不存在";
        }
        // 密码错误
        else if (failed instanceof BadCredentialsException) {
            returnData="账号或者密码错误";
        }
        // 密码过期
        else if (failed instanceof CredentialsExpiredException) {
            returnData="密码过期";
        }
        // 账号不可用
        else if (failed instanceof DisabledException) {
            returnData="账号不可用";
        }
        //账号锁定
        else if (failed instanceof LockedException) {
            returnData="账号锁定";
        }
        // 其他错误
        else{
            returnData="未知异常";
        }

        // 处理编码方式 防止中文乱码
        response.setContentType("text/json;charset=utf-8");

        UrlResponse sresponse = new UrlResponse();
        sresponse.setCode(Constants.LOGIN_ERROR);
        sresponse.setMessage(returnData);

        // 将反馈塞到HttpServletResponse中返回给前台
        response.getWriter().write(GsonUtil.GSON.toJson(sresponse));
    }
}

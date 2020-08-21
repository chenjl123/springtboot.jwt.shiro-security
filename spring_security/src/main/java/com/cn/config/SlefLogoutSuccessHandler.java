package com.cn.config;

import com.cn.util.Constants;
import com.cn.util.GsonUtil;
import com.cn.util.UrlResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义注销成功处理器：返回状态码200
 */
@Slf4j
@Component
public class SlefLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UrlResponse response = new UrlResponse();
        response.setCode(Constants.SUCCESS);
        response.setMessage("退出登录成功");
        log.info("logout success");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        httpServletResponse.getWriter().write(GsonUtil.GSON.toJson(response));
    }
}

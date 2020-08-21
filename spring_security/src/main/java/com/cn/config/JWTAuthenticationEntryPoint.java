package com.cn.config;

import com.cn.util.Constants;
import com.cn.util.GsonUtil;
import com.cn.util.UrlResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token验证失败
 *
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        UrlResponse sresponse = new UrlResponse();
        sresponse.setCode(Constants.NOLOGIN);
        sresponse.setMessage("您未登录，或者登录凭证已失效，请重新登录！");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(GsonUtil.GSON.toJson(response));
    }
}

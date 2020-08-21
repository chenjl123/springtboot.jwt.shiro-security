package com.cn.config;

import com.cn.util.Constants;
import com.cn.util.GsonUtil;
import com.cn.util.UrlResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 自定义权限不足处理器：返回状态码403
 *
 */
@Component
@Slf4j
public class UrlForbiddenHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        UrlResponse response = new UrlResponse();
        response.setCode(Constants.FORBIDDEN);
        response.setMessage("没有权限访问改资源");
        log.error("no authoried....");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/json;charset=UTF-8");
        httpServletResponse.getWriter().write(GsonUtil.GSON.toJson(response));
    }
}

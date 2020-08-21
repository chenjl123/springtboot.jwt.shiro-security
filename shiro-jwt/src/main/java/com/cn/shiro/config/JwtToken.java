package com.cn.shiro.config;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
public class JwtToken implements AuthenticationToken {

    private String token;

    private String exipreAt;

    public JwtToken(String token) {
        this.token = token;
    }

    public JwtToken(String token, String exipreAt) {
        this.token = token;
        this.exipreAt = exipreAt;
    }

    //身份
    @Override
    public Object getPrincipal() {
        return null;
    }

    //凭据
    @Override
    public Object getCredentials() {
        return this.token;
    }
}

package com.cn.shiro.controller;

import com.cn.shiro.config.JwtToken;
import com.cn.shiro.utils.JwtUtil;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class LoginController {

    @RequestMapping("login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        return JwtUtil.sign(username, password);
    }

    @RequestMapping("401")
    public String error(){
        return "token不能为空！";
    }

    @RequestMapping("402")
    public String noPow(){
        return "没权限访问！";
    }

    @RequestMapping("test")
    public  String test(){
        return "hi test";
    }
}

package com.cn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 */
@RestController
@Slf4j
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping("list")
    public String userList() {
        return "list user";
    }

    @GetMapping("add")
    public String userAdd() {
        return "list add";
    }
}

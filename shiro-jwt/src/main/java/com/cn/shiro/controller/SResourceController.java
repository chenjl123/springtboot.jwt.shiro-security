package com.cn.shiro.controller;


import com.cn.shiro.utils.ResultResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-16
 */
@RestController
@RequestMapping("/resource")
public class SResourceController {

    @RequestMapping("list")
    public ResultResponse list(){
        ResultResponse result = new ResultResponse(200,"success");
       return  result;
    }
}

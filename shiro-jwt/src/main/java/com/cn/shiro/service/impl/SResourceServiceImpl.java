package com.cn.shiro.service.impl;

import com.cn.shiro.model.SResource;
import com.cn.shiro.mapper.SResourceMapper;
import com.cn.shiro.service.ISResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-16
 */
@Service
public class SResourceServiceImpl extends ServiceImpl<SResourceMapper, SResource> implements ISResourceService {
}

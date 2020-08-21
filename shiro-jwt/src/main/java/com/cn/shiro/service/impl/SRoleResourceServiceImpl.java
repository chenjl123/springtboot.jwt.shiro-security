package com.cn.shiro.service.impl;

import com.cn.shiro.model.SRole;
import com.cn.shiro.model.SRoleResource;
import com.cn.shiro.mapper.SRoleResourceMapper;
import com.cn.shiro.service.ISRoleResourceService;
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
public class SRoleResourceServiceImpl extends ServiceImpl<SRoleResourceMapper, SRoleResource> implements ISRoleResourceService {

    @Resource
    private SRoleResourceMapper sRoleResourceMapper;
    @Override
    public List<SRole> listByResourceId(String resourceId) {
        return sRoleResourceMapper.listByResourceId(resourceId);
    }
}

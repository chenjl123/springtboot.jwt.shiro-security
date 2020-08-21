package com.cn.shiro.service.impl;

import com.cn.shiro.model.SRole;
import com.cn.shiro.model.SUserRole;
import com.cn.shiro.mapper.SUserRoleMapper;
import com.cn.shiro.service.ISUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SUserRoleServiceImpl extends ServiceImpl<SUserRoleMapper, SUserRole> implements ISUserRoleService {
    @Resource
    private SUserRoleMapper sUserRoleMapper;

    @Override
    public List<SRole> listByUserId(String userId) {
        return sUserRoleMapper.listByUserId(userId);
    }

    @Override
    public void addUserRole(SUserRole sUserRole) {
        sUserRoleMapper.addUserRole(sUserRole);
    }
}

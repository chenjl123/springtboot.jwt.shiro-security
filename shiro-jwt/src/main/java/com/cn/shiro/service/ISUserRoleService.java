package com.cn.shiro.service;

import com.cn.shiro.model.SRole;
import com.cn.shiro.model.SUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-16
 */
public interface ISUserRoleService extends IService<SUserRole> {
    /**
     * 通过用户id，获取角色列表
     * @return
     */
    public List<SRole> listByUserId(String userId);

    /**
     * 添加用户角色
     */
    public void addUserRole(SUserRole sUserRole);

}

package com.cn.shiro.service;

import com.cn.shiro.model.SRole;
import com.cn.shiro.model.SRoleResource;
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
public interface ISRoleResourceService extends IService<SRoleResource> {
    /**
     * 通过资源id，获取角色列表
     * @param resourceId
     * @return
     */
    public List<SRole> listByResourceId(String resourceId);
}

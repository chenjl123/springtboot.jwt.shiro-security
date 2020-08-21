package com.cn.shiro.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.shiro.mapper.SResourceMapper;
import com.cn.shiro.model.SResource;
import com.cn.shiro.model.SRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 自定义，动态权限验证
 */
@Slf4j
@Service
public class ShiroService {

    @Autowired
    private ISResourceService isResourceService;

    @Autowired
    private ISRoleResourceService isRoleResourceService;

    public Map<String, String> loadFilterChainDefinitionMap() {
        // 权限控制map
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        // 放行 end ----------------------------------------------------------
        // 从数据库或缓存中查取出来的url与resources对应则不会被拦截 放行
        //资源，资源权限关系可以初始化放到redis缓存
        List<SResource> resourceList = isResourceService.list();
        if ( !CollectionUtils.isEmpty( resourceList ) ) {
            resourceList.forEach( e -> {
                if ( StringUtils.isNotBlank( e.getUrl() ) ) {
                    // 根据url查询相关联的角色名,拼接自定义的角色权限
                    List<SRole> roleList = isRoleResourceService.listByResourceId(e.getId() + "");
                    StringJoiner zqRoles = new StringJoiner(",", "zqRoles[", "]");
                    if ( !CollectionUtils.isEmpty( roleList ) ){
                        roleList.forEach( f -> {
                            zqRoles.add( f.getRoleCode());
                        });
                    }
                    log.info("zqRoles====================:"+ e.getUrl()+":"+ zqRoles.toString());
                    // 注意过滤器配置顺序不能颠倒
                    // ① 认证登录
                    // ② 认证自定义的token过滤器 - 判断token是否有效
                    // ③ 角色权限 zqRoles：自定义的只需要满足其中一个角色即可访问  ;  roles[admin,guest] : 默认需要每个参数满足才算通过，相当于hasAllRoles()方法
                    // ④ zqPerms:认证自定义的url过滤器拦截权限  【注：多个过滤器用 , 分割】
//                    filterChainDefinitionMap.put( "/api" + e.getUrl(),"authc,token,roles[admin,guest],zqPerms[" + e.getResources() + "]" );
                    // filterChainDefinitionMap.put(e.getUrl(),  "jwt");
                    filterChainDefinitionMap.put(e.getUrl(),  "jwt,"+zqRoles.toString() );
//                   filterChainDefinitionMap.put("/api/system/user/listPage", "authc,token,zqPerms[user1]"); // 写死的一种用法
                }
            });
        }
        // ⑤ 认证登录  【注：map不能存放相同key】
        //filterChainDefinitionMap.put("/**", "authc");
        // 所有请求通过我们自己的JWT Filter
        //filterChainDefinitionMap.put("/**", "jwt");
        // 访问401和404页面不通过我们的Filter
        filterChainDefinitionMap.put("/auth/**", "anon");
        return filterChainDefinitionMap;
    }
}

package com.cn.config;

import com.cn.service.UserService;
import com.cn.util.CacheData;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 动态权限认证
 */
@Slf4j
@Component("rbacauthorityservice")
public class RbacAuthorityService  {

    @Resource
    private UserService userService;

    /**
     * 判断是否有权限
     * @param request
     * @param authentication
     * @return
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Collection<ConfigAttribute> collection = getAttributes(request);
        log.info("getPrincipal:" + authentication.getPrincipal());
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        }

        if (null == collection || collection.size() <= 0) {
            return true;
        }

        ConfigAttribute configAttribute;
        String needRole;
        for (Iterator<ConfigAttribute> iterator = collection.iterator(); iterator.hasNext(); ) {
            configAttribute = iterator.next();
            needRole = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (needRole.trim().equals(grantedAuthority.getAuthority())) {
                    return true;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    /**
     * 获取url对应的权限
     * @param
     * @return
     * @throws IllegalArgumentException
     */
    private Collection<ConfigAttribute> getAttributes(HttpServletRequest request) throws IllegalArgumentException {
        // 获取 请求 url 地址
        String requestUrl = request.getRequestURI();
        // 如果匹配上就 获取到 所需的角色列表
        //List<Record> roles =  userService.findRoleNameByMenuUrl(requestUrl);
        List<Record> roles = this.roles(requestUrl);
        List<ConfigAttribute> configAttributeList = roles.stream().map(record -> {
            ConfigAttribute configAttribute = new SecurityConfig("ROLE_" + record.getStr("role_id"));
            return configAttribute;
        }).collect(Collectors.toList());
        log.info("地址需要权限：" + configAttributeList.toString() );
        return configAttributeList;
    }

    /**
     * 通过url获取对应角色列表
     * @param url
     * @return
     */
    private List<Record> roles(String url){
        Map<String, List<Record>> urMap = CacheData.urlRolesMap;
        if(urMap.size() == 0){
            //缓存没有数据，查询数据库
            List<Record> urList = userService.findAllRoleResource();
            urMap = new HashMap<>();
            List<Record> temp  = null;
            String rurl = "";
            for(Record red : urList){
                rurl = red.getStr("res_url");
                if(urMap.containsKey(rurl)){
                    //已经存在
                    temp = urMap.get(rurl);
                }else{
                    temp = new ArrayList<>();
                }
                temp.add(red);
                urMap.put(rurl, temp);
            }
            CacheData.urlRolesMap = urMap;
        }
        return urMap.get(url);
    }
}

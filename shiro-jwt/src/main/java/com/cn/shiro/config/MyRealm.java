package com.cn.shiro.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.shiro.model.SRole;
import com.cn.shiro.model.SUser;
import com.cn.shiro.service.ISUserRoleService;
import com.cn.shiro.service.ISUserService;
import com.cn.shiro.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 */
@Slf4j
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private ISUserService isUserService;

    @Autowired
    private ISUserRoleService isUserRoleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    /**
     * 授权模块，获取用户角色和权限
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("doGetAuthorizationInfo################权限认证");
        String username = JwtUtil.getUsername(principalCollection.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 获取用户角色集,表存的是id，需要修改存user_code
        //后期可以放到redis
        List<SRole> roleList = isUserRoleService.listByUserId("1");
        Set<String> roleSet =  new HashSet<>();
        roleList.forEach(role ->{
            roleSet.add(role.getRoleCode());
        });
        simpleAuthorizationInfo.setRoles(roleSet);
        // 获取用户权限集
        //Set<String> permissionSet = null; //userService.getUserPermissions(username);
        //simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * @param authenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 这里的 token是从 JwtFilter 的 executeLogin 方法传递过来的，已经经过了解密
        log.info("doGetAuthenticationInfo=====身份认证");
        String token = (String) authenticationToken.getCredentials();
        String username = JwtUtil.getUsername(token);
        if (StringUtils.isBlank(username)) {
            throw new AuthenticationException("token校验不通过");
        }
        // 如果要实现登出逻辑需要将用户和token存储起来（redis、memcache等）这里校验token是否有效

        // 通过用户名查询用户信息，也可改为接口验证用户名是否存在（即通过登录中心验证的）
        QueryWrapper<SUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_code", username);
        SUser user = isUserService.getOne(wrapper); //userService.getUser(username);
        if (user == null) {
            throw new AuthenticationException("用户名或密码错误");
        }
        /*
         * 注意这里的校验
         * token
         * username 用户名
         * secret 用户的密码
         *
         * 这里要注意secret这个字段，如果本地系统没有用户存储用户密码（即通过登录中心验证的）
         * 可以把这个值写成一个固定值，当然这样有一定的风险，或者根据一定的规则生成假的密码来验证。
         *
         */
        if (!JwtUtil.verify(token, username, user.getPwd())) {
            throw new AuthenticationException("token校验不通过");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}

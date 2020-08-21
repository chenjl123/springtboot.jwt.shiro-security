package com.cn.config;

import com.cn.service.UserService;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义用户认证
 */
@Slf4j
@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SelfUserDetails userInfo = new SelfUserDetails();
        userInfo.setUsername(username); //任意登录用户名

        Record user = userService.findUserByUserCode(username);
        if (user == null) {
            throw new UsernameNotFoundException("User name" + username + "not find!!");
        }
        String password = passwordEncoder.encode(user.getStr("pwd"));
        userInfo.setPassword(password); //从数据库获取密码
        //用户具有的权限
        List<Record> roleList = userService.findRoleNameByUsername(user.getStr("id"));
        List<String> roles = new ArrayList<>();
        roleList.stream().forEach(record -> {
            roles.add("ROLE_" + record.getStr("role_id"));
        });
        log.info("roles:" + roles.toString());
        userInfo.setRoles(roles.toString());
        return userInfo;
    }


}

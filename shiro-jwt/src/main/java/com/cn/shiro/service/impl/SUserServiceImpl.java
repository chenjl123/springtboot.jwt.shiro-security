package com.cn.shiro.service.impl;

import com.cn.shiro.model.SUser;
import com.cn.shiro.mapper.SUserMapper;
import com.cn.shiro.service.ISUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-16
 */
@Service
public class SUserServiceImpl extends ServiceImpl<SUserMapper, SUser> implements ISUserService {

}

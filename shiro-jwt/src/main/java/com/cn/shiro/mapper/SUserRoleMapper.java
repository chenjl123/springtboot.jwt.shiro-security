package com.cn.shiro.mapper;

import com.cn.shiro.model.SRole;
import com.cn.shiro.model.SUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-16
 */
public interface SUserRoleMapper extends BaseMapper<SUserRole> {
    /**
     * 通过用户id，获取角色列表
     * @return
     */
    @Select("select role_code,role_name from s_user_role sr left join s_role r on r.id = sr.role_id where sr.user_id = #{user_id};")
    public List<SRole> listByUserId(String userId);

    /**
     * 添加用户角色
     */
    public void addUserRole(SUserRole sUserRole);
}

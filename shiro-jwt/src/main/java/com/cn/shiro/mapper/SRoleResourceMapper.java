package com.cn.shiro.mapper;

import com.cn.shiro.model.SRole;
import com.cn.shiro.model.SRoleResource;
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
public interface SRoleResourceMapper extends BaseMapper<SRoleResource> {
    /**
     * 通过资源id，获取角色列表
     * @param resourceId
     * @return
     */
    @Select("select role_code,role_name from s_role_resource sr left join s_role r on r.id = sr.role_id where sr.res_url = #{resourceId};")
    public List<SRole> listByResourceId(String resourceId);
}

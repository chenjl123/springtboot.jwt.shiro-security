package com.cn.service;

import com.cn.util.UrlResponse;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户管理
 */
@Slf4j
@Service
public class UserService {


    /**
     * 根据用户名查询密码
     */
    public Record findUserByUserCode(String userCode) {
        Record record = Db.findFirst("select pwd,id from s_user where user_code = ?", userCode);
        return record;
    }


    /**
     * 根据用户获取菜单信息
     */
    public List<Record> findMenuInfoByUsername(String userCode, UrlResponse response) {
        return null;
    }

    /**
     * 根据用户名获得角色名称
     */
    public List<Record> findRoleNameByUsername(String userCode) {
        return Db.find("select role_id from s_user_role where user_id = ?", userCode);
    }

    /**
     * 获得所有的菜单url
     */
    public List<Record> findAllMenuUrl() {
        return  Db.find("select url from s_resource");
    }

    /**
     * 根据菜单url获得需要拥有的角色
     */
    public List<Record> findRoleNameByMenuUrl(String url) {
        return Db.find("select role_id from s_role_resource where res_url = ? ", url);
    }

    /**
     * 获取资源角色列表
     * @return
     */
    public List<Record> findAllRoleResource(){
        return Db.find("select res_url,role_id from s_role_resource");
    }

}

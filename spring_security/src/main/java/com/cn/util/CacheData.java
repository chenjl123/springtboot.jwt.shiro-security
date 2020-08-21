package com.cn.util;

import com.jfinal.plugin.activerecord.Record;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存数据
 */
@Data
public class CacheData {

    //用于存放所有url请求和对应的角色
    public static Map<String, List<Record>>  urlRolesMap = new HashMap<>();
}

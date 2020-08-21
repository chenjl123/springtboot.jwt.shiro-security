package com.cn.shiro.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SUser extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String userCode;

    private String userName;

    /**
     * 0:女，1：男
     */
    private String sex;

    private String pwd;

    /**
     * 0:前台用户，1：后台用户
     */
    private String userType;

    private String headUrl;

    private String weChat;

    private String mobile;

    private String email;

    private String qq;

    private Long createUser;

    private LocalDateTime createTime;


}

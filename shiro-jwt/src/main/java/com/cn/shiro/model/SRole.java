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
public class SRole extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String roleCode;

    private String roleName;

    private String remark;

    private Long createUser;

    private LocalDateTime createTime;


}

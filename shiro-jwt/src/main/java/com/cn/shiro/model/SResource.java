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
public class SResource extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long pid;

    private String name;

    private String url;

    private Integer seq;

    /**
     * 0:菜单，1：按钮
     */
    private String type;

    private Long createUser;

    private LocalDateTime createTime;


}

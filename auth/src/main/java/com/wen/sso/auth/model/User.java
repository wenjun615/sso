package com.wen.sso.auth.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wen.sso.auth.entity.base.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wenjun
 * @since 2021-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("id")
    private String id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}

package com.wen.sso.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.sso.auth.model.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wenjun
 * @since 2021-06-21
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名加载用户详情
     *
     * @param username 用户名
     * @return 用户详情
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    User getByUsername(String username);
}

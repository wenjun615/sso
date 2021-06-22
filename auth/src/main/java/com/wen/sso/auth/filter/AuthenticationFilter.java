package com.wen.sso.auth.filter;

import cn.hutool.core.util.StrUtil;
import com.wen.sso.auth.config.RsaKeyProperties;
import com.wen.sso.auth.entity.bo.UserDetailsBO;
import com.wen.sso.auth.model.User;
import com.wen.sso.common.entity.Payload;
import com.wen.sso.common.util.JwtUtil;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 认证过滤器
 * </p>
 *
 * @author wenjun
 * @since 2021-06-22
 */
public class AuthenticationFilter extends BasicAuthenticationFilter {

    private static final String TOKEN_HEAD = "Bearer ";

    private final RsaKeyProperties rsaKey;

    public AuthenticationFilter(AuthenticationManager authenticationManager, RsaKeyProperties rsaKey) {
        super(authenticationManager);
        this.rsaKey = rsaKey;
    }

    @SneakyThrows
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        String authHeader = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(authHeader) && authHeader.startsWith(TOKEN_HEAD)) {
            String token = authHeader.substring(TOKEN_HEAD.length());
            if (StrUtil.isNotBlank(token)) {
                Payload<User> payload = JwtUtil.getPayload(token, rsaKey.getPublicKey(), User.class);
                UserDetails userDetails = new UserDetailsBO(payload.getUserInfo());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}

package com.wen.sso.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.sso.auth.common.api.CommonResult;
import com.wen.sso.auth.config.RsaKeyProperties;
import com.wen.sso.auth.model.User;
import com.wen.sso.common.util.JwtUtil;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * <p>
 * 登录过滤器
 * </p>
 *
 * @author wenjun
 * @since 2021-06-22
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final RsaKeyProperties rsaKey;

    public LoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties rsaKey) {
        this.authenticationManager = authenticationManager;
        this.rsaKey = rsaKey;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
                user.getPassword());
        return authenticationManager.authenticate(authentication);
    }

    @SneakyThrows
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                         Authentication authResult) {
        User user = User.builder()
                .username(authResult.getName())
                .build();
        String token = JwtUtil.generateToken(user, rsaKey.getPrivateKey(), 24 * 60, JwtUtil.EXPIRATION_TYPE_MINUTE);
        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(CommonResult.successful("认证成功")));
        writer.flush();
        writer.close();
    }
}

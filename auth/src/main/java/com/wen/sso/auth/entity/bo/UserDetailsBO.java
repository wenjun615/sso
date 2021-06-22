package com.wen.sso.auth.entity.bo;

import com.wen.sso.auth.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * <p>
 * Spring Security 用户详情
 * </p>
 *
 * @author wenjun
 * @since 2021/3/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsBO implements UserDetails {

    private static final long serialVersionUID = 6000614294197236782L;

    /**
     * 用户
     */
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

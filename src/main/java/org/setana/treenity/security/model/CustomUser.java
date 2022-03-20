package org.setana.treenity.security.model;

import java.util.Collection;
import java.util.Objects;
import lombok.Data;
import org.setana.treenity.entity.User;
import org.setana.treenity.exception.ErrorCode;
import org.setana.treenity.exception.NotAcceptableException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class CustomUser implements UserDetails {

    private Long userId;
    private String uid;
    private String email;
    private String username;

    public CustomUser(User user) {
        this.userId = user.getId();
        this.uid = user.getUid();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void checkUserId(Long userId) {
        System.out.println("customUserId=" + this.userId + ", userId=" + userId);

        System.out.println(
            "Object.equals(this.userId, userId) = " + Objects.equals(this.userId, userId));

        // TODO: 샘플 유저 userId != 1 조건 제거 필요
        if (userId != 1 && !Objects.equals(this.userId, userId)) {
            throw new NotAcceptableException(ErrorCode.USER_CHECK_FAIL);
        }
    }
}

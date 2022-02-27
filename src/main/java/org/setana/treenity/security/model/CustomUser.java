package org.setana.treenity.security.model;

import java.util.Collection;
import lombok.Data;
import org.setana.treenity.dto.UserFetchDto;
import org.setana.treenity.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class CustomUser implements UserDetails {

    private Long userId;
    private String uid;
    private String email;
    private String username;
    private Integer point;
    private Integer dailyWalks;
    private Integer buckets;

    public CustomUser(UserFetchDto dto) {
        this.userId = dto.getUserId();
        this.uid = dto.getUid();
        this.email = dto.getEmail();
        this.username = dto.getUsername();
        this.point = dto.getPoint();
        this.dailyWalks = dto.getDailyWalks();
        this.buckets = dto.getBuckets();
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
}

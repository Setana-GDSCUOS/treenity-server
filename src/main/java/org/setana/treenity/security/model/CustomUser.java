package org.setana.treenity.security.model;

import java.util.Collection;
import lombok.Data;
import org.setana.treenity.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class CustomUser implements UserDetails {

    private String uid;
    private String email;
    private String username;

    public CustomUser(User user) {
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
}

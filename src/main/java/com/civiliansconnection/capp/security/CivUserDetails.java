package com.civiliansconnection.capp.security;

import com.civiliansconnection.capp.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CivUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean isEnabled;

    public CivUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
//        this.accountNonExpired = user.getAccountNonExpired() == 1;
//        this.accountNonLocked = user.getAccountNonLocked() == 1;
//        this.credentialsNonExpired = user.getCredentialsNonExpired() == 1;
//        this.isEnabled = user.getEnabled() == 1;
//        this.authorities = getAuthorities(user.getRoles());
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
        return this.username;
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

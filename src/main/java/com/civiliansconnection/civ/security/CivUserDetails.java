package com.civiliansconnection.civ.security;

import com.civiliansconnection.civ.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class CivUserDetails implements UserDetails {

    private String username;
    @JsonIgnore
    private String password;
    @Getter
    private Long id;
    @JsonIgnore
    private List<GrantedAuthority> authorities;
    @JsonIgnore
    private boolean accountNonExpired;
    @JsonIgnore
    private boolean accountNonLocked;
    @JsonIgnore
    private boolean credentialsNonExpired;
    @JsonIgnore
    private boolean enabled;

    public CivUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.id = user.getId();
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
        return this.password;
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

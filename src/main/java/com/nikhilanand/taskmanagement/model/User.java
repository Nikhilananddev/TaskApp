package com.nikhilanand.taskmanagement.model;


import com.nikhilanand.taskmanagement.global.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    private UserEntity userEntity;

    public Long getId() {
        return userEntity.getUserId();
    }

    public String getFirstName() {
        return userEntity.getFirstName();
    }

    public String getLastName() {
        return userEntity.getLastName();
    }


    public Role getRole() {
        return userEntity.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userEntity.getRole().name()));
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserName();
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

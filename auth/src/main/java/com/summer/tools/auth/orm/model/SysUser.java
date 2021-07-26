package com.summer.tools.auth.orm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SysUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String realName;

    @Column(unique = true)
    private String username;

    private String password;

    public SysUser(String realName, String username, String password) {
        this.realName = realName;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //1
        return null;
    }

    @Override
    public String getPassword() { //2
        return this.password;
    }

    @Override
    public String getUsername() { //3
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() { //4
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //5
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //6
        return true;
    }

    @Override
    public boolean isEnabled() { //7
        return true;
    }
}

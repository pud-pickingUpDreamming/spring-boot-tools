package com.summer.tools.auth.service;

import com.summer.tools.auth.orm.dao.SysUserRepository;
import com.summer.tools.auth.orm.model.SysUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Resource
    private SysUserRepository sysUserRepository;

//    public CustomUserDetailsService(SysUserRepository sysUserRepository) {
//        this.sysUserRepository = sysUserRepository; //1
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SysUser> sysUserOptional = sysUserRepository.findByUsername(username); //2
        return  sysUserOptional
                .orElseThrow(() -> new UsernameNotFoundException("Username not found")); //3
    }
}


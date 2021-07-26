package com.summer.tools.auth;

import com.summer.tools.auth.orm.dao.SysUserRepository;
import com.summer.tools.auth.orm.model.SysUser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@MapperScan(basePackages = {"com.summer.tools.template.orm.dao"})
@ComponentScan(basePackages = {"com.summer.tools.auth", "com.summer.tools.common"})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    CommandLineRunner createUser(SysUserRepository sysUserRepository, PasswordEncoder passwordEncoder){
        return args -> {
            SysUser user = new SysUser("王丰雷", "john", passwordEncoder.encode("john"));
            sysUserRepository.save(user);
        };
    }
}

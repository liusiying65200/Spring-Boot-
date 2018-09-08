package net.hualisheng.demo.config;

import net.hualisheng.demo.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfigration {
    @Bean
    public User createUser(){
        return new User();
    }
}

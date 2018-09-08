package net.hualisheng.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RunableConfigration {
    @Bean
    public Runnable create() {
        return new Thread();
    }

    @Bean
//    @Primary
    public Runnable createD() {
        return ()-> {};
    }
}

package net.hualisheng.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootConfiguration
public class RunnableConfiguration {
    @Bean(name = "run")
    public Runnable createRunnable(){
        System.err.println(new Date());
        return ()->{};
    }
}

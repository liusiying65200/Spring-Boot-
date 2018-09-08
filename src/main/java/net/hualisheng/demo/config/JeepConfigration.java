package net.hualisheng.demo.config;

import net.hualisheng.demo.model.Jeep;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(name = "net.hualisheng.demo.model.Jeep")
public class JeepConfigration {
    @Bean
    @ConditionalOnMissingBean
    public Jeep jeep(){
        return new Jeep();
    }
}

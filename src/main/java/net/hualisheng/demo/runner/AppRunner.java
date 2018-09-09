package net.hualisheng.demo.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(value = 2)
@Component
public class AppRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.err.println("系统已经进入了AppRunner的run方法了");
        String[] sourceArgs = applicationArguments.getSourceArgs();
        List<String> nonOptionArgs = applicationArguments.getNonOptionArgs();
        System.err.println(nonOptionArgs);
        System.err.println(sourceArgs.toString());
    }
}

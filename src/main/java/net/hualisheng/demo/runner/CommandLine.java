package net.hualisheng.demo.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Order(value = 20)
@Component
public class CommandLine implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        System.err.println("系统已经进入CommandLine的run");
        System.out.println(strings);
    }
}

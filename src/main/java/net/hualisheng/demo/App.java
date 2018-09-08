package net.hualisheng.demo;

import com.google.gson.Gson;
import net.hualisheng.demo.event.AppEvent;
import net.hualisheng.demo.listener.AppListnener;
import net.hualisheng.demo.model.Role;
import net.hualisheng.demo.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;
//
//@EnableAutoConfiguration(excludeName = "net.hualisheng.demo.model.Role")
//@ComponentScan
@SpringBootApplication
@EnableConfigurationProperties
//@EnableAutoConfiguration
public class App {
    //    DelegatingApplicationContextInitializer
//    DelegatingApplicationListener
    private static final ApplicationListener ApplicationListener = new AppListnener();
    private static final ApplicationEvent ApplicationEvent = new AppEvent(new Role());

    @Bean
    @ConditionalOnMissingBean
    public Gson gson2() {
        return new Gson();
    }
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
//        app.addListeners(ApplicationListener);

        ConfigurableApplicationContext context = app.run(args);
//        Runnable bean = context.getBean(Runnable.class);
//        System.out.println(bean);
        Map<String, Runnable> map = context.getBeansOfType(Runnable.class);
        System.out.println(map);
        System.out.println(context.getBean(User.class));
        System.out.println(Role.class);
//        System.out.println(context.getBean(Gson.class));
        System.out.println(context.getBean("gson2"));
        System.out.println(context.getBean("jeep"));
        context.publishEvent(ApplicationEvent);//使用的是这个接口来发布事件的ApplicationEventPublisher
        context.close();
    }
}

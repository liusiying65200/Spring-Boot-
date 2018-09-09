package net.hualisheng.demo;

import com.google.gson.Gson;
import net.hualisheng.config.RunnableConfiguration;
import net.hualisheng.demo.event.AppEvent;
import net.hualisheng.demo.listener.AppListnener;
import net.hualisheng.demo.model.Jeep;
import net.hualisheng.demo.model.Role;
import net.hualisheng.demo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Hashtable;
import java.util.Map;
//
//@EnableAutoConfiguration(excludeName = "net.hualisheng.demo.model.Role")
//@ComponentScan
@SpringBootApplication(scanBasePackages = {"net.hualisheng"})
//@EnableConfigurationProperties
public class App {
    private static final ApplicationListener ApplicationListener = new AppListnener();
    private static final ApplicationEvent ApplicationEvent = new AppEvent(new Role());
    @Value("${host:12.123}")
    private String host;

    @Bean
    @ConditionalOnMissingBean
    public Gson gson2() {
        return new Gson();
    }
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.addListeners(ApplicationListener);
        app.setBannerMode(Banner.Mode.OFF);
        Map<String, Object> defaultProperties=new Hashtable<>();
        defaultProperties.put("port","8080");
        app.setDefaultProperties(defaultProperties);
        ConfigurableApplicationContext context = app.run(args);
        String property = context.getEnvironment().getProperty("host", "ewtrew");
        System.err.println(property);

        Map<String, Runnable> map = context.getBeansOfType(Runnable.class);
        System.out.println(map);
        System.out.println(context.getBean(User.class));

        System.out.println(context.getBean("gson2"));
        System.out.println(context.getBean("jeep"));
        context.publishEvent(ApplicationEvent);//使用的是这个接口来发布事件的ApplicationEventPublisher
        System.err.println(context.getBean("run"));
//        System.err.println( context.getEnvironment().getProperty("host"));
        System.err.println(context.getBean(App.class).host);
        System.err.println(context.getEnvironment().getProperty("port"));
        context.close();

    }
}

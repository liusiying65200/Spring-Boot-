package net.hualisheng.demo;

import com.google.gson.Gson;
import net.hualisheng.demo.event.AppEvent;
import net.hualisheng.demo.listener.AppListnener;
import net.hualisheng.demo.model.AppClassPathXmlContextInitializer;
import net.hualisheng.demo.model.AppConfigurableContextInitializer;
import net.hualisheng.demo.model.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

//
//@EnableAutoConfiguration(excludeName = "net.hualisheng.demo.model.Role")
//@ComponentScan
@SpringBootApplication
@EnableConfigurationProperties
//@EnableAutoConfiguration
public class App {
    private static final ApplicationListener ApplicationListener = new AppListnener();
    private static final ApplicationEvent ApplicationEvent = new AppEvent(new Role());

    @Bean
    @ConditionalOnMissingBean
    public Gson gson2() {
        return new Gson();
    }
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.addListeners(ApplicationListener);
//        ApplicationContextInitializer<ConfigurableApplicationContext>
//        AppClassPathXmlContextInitializer initializer = new AppClassPathXmlContextInitializer();
//        app.addInitializers(initializer);
//        AppConfigurableContextInitializer configurableinitializer=new AppConfigurableContextInitializer();
//        app.addInitializers(configurableinitializer);
        ConfigurableApplicationContext context = app.run(args);
        context.stop();
//        initializer = new AppClassPathXmlContextInitializer();
        context.close();
    }
}

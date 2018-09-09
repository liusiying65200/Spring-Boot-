package net.hualisheng.demo.model;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class AppConfigurableContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    /**
     * Initialize the given application context.
     *
     * @param applicationContext the application to configure
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        int count = applicationContext.getBeanDefinitionCount();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        ConfigurableListableBeanFactory factory = applicationContext.getBeanFactory();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        ApplicationContext parent = applicationContext.getParent();
        String id = applicationContext.getId();
        String displayName = applicationContext.getDisplayName();
        String applicationName = applicationContext.getApplicationName();
        System.err.println(count);
        System.err.println(displayName);
        System.err.println(applicationName);
        System.err.println(id);
        System.err.println(parent);
        for (String name : beanDefinitionNames) {
            System.err.println(name);
        }

    }
}

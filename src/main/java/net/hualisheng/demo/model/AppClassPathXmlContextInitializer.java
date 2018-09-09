package net.hualisheng.demo.model;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class AppClassPathXmlContextInitializer implements ApplicationContextInitializer<ClassPathXmlApplicationContext> {
    /**
     * Initialize the given application context.
     *
     * @param applicationContext the application to configure
     */
    @Override
    public void initialize(ClassPathXmlApplicationContext applicationContext) {
        int count = applicationContext.getBeanDefinitionCount();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        ConfigurableListableBeanFactory factory = applicationContext.getBeanFactory();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        ApplicationContext parent = applicationContext.getParent();
        String id = applicationContext.getId();
        String displayName = applicationContext.getDisplayName();
        String applicationName = applicationContext.getApplicationName();
        System.err.println(count);
    }

    /**
     * Initialize the given application context.
     *
     * @param applicationContext the application to configure
     */


    /**
     * Initialize the given application context.
     *
     * @param applicationContext the application to configure
     */

}

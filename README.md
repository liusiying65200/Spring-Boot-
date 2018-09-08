# Spring Boot
###配置监听器的方法:
```markdown
1.通过使用SpringApplication.addListener配置
2.通过添加@Component注解的形式进行添加
3.使用context.listener.classes进行配置项方式添加
4.使用自定义配置项(AppHanle)在其类的方法上添加@EventListener注解的形式进行
```
```markdown
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.boot.context.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class DelegatingApplicationListener implements ApplicationListener<ApplicationEvent>, Ordered {
    private static final String PROPERTY_NAME = "context.listener.classes";
    private int order = 0;
    private SimpleApplicationEventMulticaster multicaster;

    public DelegatingApplicationListener() {
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            List<ApplicationListener<ApplicationEvent>> delegates = this.getListeners(((ApplicationEnvironmentPreparedEvent)event).getEnvironment());
            if (delegates.isEmpty()) {
                return;
            }

            this.multicaster = new SimpleApplicationEventMulticaster();
            Iterator var3 = delegates.iterator();

            while(var3.hasNext()) {
                ApplicationListener<ApplicationEvent> listener = (ApplicationListener)var3.next();
                this.multicaster.addApplicationListener(listener);
            }
        }

        if (this.multicaster != null) {
            this.multicaster.multicastEvent(event);
        }

    }

    private List<ApplicationListener<ApplicationEvent>> getListeners(ConfigurableEnvironment environment) {
        if (environment == null) {
            return Collections.emptyList();
        } else {
            String classNames = environment.getProperty("context.listener.classes");
            List<ApplicationListener<ApplicationEvent>> listeners = new ArrayList();
            if (StringUtils.hasLength(classNames)) {
                Iterator var4 = StringUtils.commaDelimitedListToSet(classNames).iterator();

                while(var4.hasNext()) {
                    String className = (String)var4.next();

                    try {
                        Class<?> clazz = ClassUtils.forName(className, ClassUtils.getDefaultClassLoader());
                        Assert.isAssignable(ApplicationListener.class, clazz, "class [" + className + "] must implement ApplicationListener");
                        listeners.add((ApplicationListener)BeanUtils.instantiateClass(clazz));
                    } catch (Exception var7) {
                        throw new ApplicationContextException("Failed to load context listener class [" + className + "]", var7);
                    }
                }
            }

            AnnotationAwareOrderComparator.sort(listeners);
            return listeners;
        }
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }
}

```
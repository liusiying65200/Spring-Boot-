# Spring Boot
###一.配置监听器的方法:
####1.通过使用SpringApplication.addListener配置
```markdown
 app.addListeners(ApplicationListener);
 context.publishEvent(ApplicationEvent);//使用的是这个接口来发布事件的ApplicationEventPublisher
```
####2.通过添加@Component注解的形式进行添加
```markdown
package net.hualisheng.demo.listener;

import net.hualisheng.demo.event.AppEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 自定义一个事件监听器
 */
@Component
public class AppListnener implements ApplicationListener<AppEvent> {
    @Override
    public void onApplicationEvent(AppEvent event) {
        System.out.println("接收到事件:"+event.getClass());
    }
}

```
```markdown
 context.publishEvent(ApplicationEvent);//使用的是这个接口来发布事件的ApplicationEventPublisher
```
####3.使用context.listener.classes进行配置项方式添加
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
#####4.使用自定义配置项(AppHanle)在其类的方法上添加@EventListener注解的形式进行
```markdown
/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * Register {@link EventListener} annotated method as individual {@link ApplicationListener}
 * instances.
 *
 * @author Stephane Nicoll
 * @author Juergen Hoeller
 * @since 4.2
 */
public class EventListenerMethodProcessor implements SmartInitializingSingleton, ApplicationContextAware {

	protected final Log logger = LogFactory.getLog(getClass());

	private ConfigurableApplicationContext applicationContext;

	private final EventExpressionEvaluator evaluator = new EventExpressionEvaluator();

	private final Set<Class<?>> nonAnnotatedClasses =
			Collections.newSetFromMap(new ConcurrentHashMap<Class<?>, Boolean>(64));


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Assert.isTrue(applicationContext instanceof ConfigurableApplicationContext,
				"ApplicationContext does not implement ConfigurableApplicationContext");
		this.applicationContext = (ConfigurableApplicationContext) applicationContext;
	}

	@Override
	public void afterSingletonsInstantiated() {
		List<EventListenerFactory> factories = getEventListenerFactories();
		String[] beanNames = this.applicationContext.getBeanNamesForType(Object.class);
		for (String beanName : beanNames) {
			if (!ScopedProxyUtils.isScopedTarget(beanName)) {
				Class<?> type = null;
				try {
					type = AutoProxyUtils.determineTargetClass(this.applicationContext.getBeanFactory(), beanName);
				}
				catch (Throwable ex) {
					// An unresolvable bean type, probably from a lazy bean - let's ignore it.
					if (logger.isDebugEnabled()) {
						logger.debug("Could not resolve target class for bean with name '" + beanName + "'", ex);
					}
				}
				if (type != null) {
					if (ScopedObject.class.isAssignableFrom(type)) {
						try {
							type = AutoProxyUtils.determineTargetClass(this.applicationContext.getBeanFactory(),
									ScopedProxyUtils.getTargetBeanName(beanName));
						}
						catch (Throwable ex) {
							// An invalid scoped proxy arrangement - let's ignore it.
							if (logger.isDebugEnabled()) {
								logger.debug("Could not resolve target bean for scoped proxy '" + beanName + "'", ex);
							}
						}
					}
					try {
						processBean(factories, beanName, type);
					}
					catch (Throwable ex) {
						throw new BeanInitializationException("Failed to process @EventListener " +
								"annotation on bean with name '" + beanName + "'", ex);
					}
				}
			}
		}
	}


	/**
	 * Return the {@link EventListenerFactory} instances to use to handle
	 * {@link EventListener} annotated methods.
	 */
	protected List<EventListenerFactory> getEventListenerFactories() {
		Map<String, EventListenerFactory> beans = this.applicationContext.getBeansOfType(EventListenerFactory.class);
		List<EventListenerFactory> allFactories = new ArrayList<EventListenerFactory>(beans.values());
		AnnotationAwareOrderComparator.sort(allFactories);
		return allFactories;
	}

	protected void processBean(final List<EventListenerFactory> factories, final String beanName, final Class<?> targetType) {
		if (!this.nonAnnotatedClasses.contains(targetType)) {
			Map<Method, EventListener> annotatedMethods = null;
			try {
				annotatedMethods = MethodIntrospector.selectMethods(targetType,
						new MethodIntrospector.MetadataLookup<EventListener>() {
							@Override
							public EventListener inspect(Method method) {
								return AnnotatedElementUtils.findMergedAnnotation(method, EventListener.class);
							}
						});
			}
			catch (Throwable ex) {
				// An unresolvable type in a method signature, probably from a lazy bean - let's ignore it.
				if (logger.isDebugEnabled()) {
					logger.debug("Could not resolve methods for bean with name '" + beanName + "'", ex);
				}
			}
			if (CollectionUtils.isEmpty(annotatedMethods)) {
				this.nonAnnotatedClasses.add(targetType);
				if (logger.isTraceEnabled()) {
					logger.trace("No @EventListener annotations found on bean class: " + targetType.getName());
				}
			}
			else {
				// Non-empty set of methods
				for (Method method : annotatedMethods.keySet()) {
					for (EventListenerFactory factory : factories) {
						if (factory.supportsMethod(method)) {
							Method methodToUse = AopUtils.selectInvocableMethod(
									method, this.applicationContext.getType(beanName));
							ApplicationListener<?> applicationListener =
									factory.createApplicationListener(beanName, targetType, methodToUse);
							if (applicationListener instanceof ApplicationListenerMethodAdapter) {
								((ApplicationListenerMethodAdapter) applicationListener)
										.init(this.applicationContext, this.evaluator);
							}
							this.applicationContext.addApplicationListener(applicationListener);
							break;
						}
					}
				}
				if (logger.isDebugEnabled()) {
					logger.debug(annotatedMethods.size() + " @EventListener methods processed on bean '" +
							beanName + "': " + annotatedMethods);
				}
			}
		}
	}

}

```
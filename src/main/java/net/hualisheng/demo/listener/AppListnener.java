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

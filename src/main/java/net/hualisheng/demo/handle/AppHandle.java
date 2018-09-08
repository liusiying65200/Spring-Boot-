package net.hualisheng.demo.handle;

import net.hualisheng.demo.event.AppEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppHandle {
    /**
     *   #入参一定要是ApplicationEvent或者ApplicationEvent的子类
     *   参数也可以是任意
     *
     * @param event
     */
    @EventListener
    public void onApplicationEvent(AppEvent event) {
        System.out.println("AppHandle接收到事件:" + event.getClass());
    }
}

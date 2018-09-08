package net.hualisheng.demo.handle;

import net.hualisheng.demo.event.AppEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
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
    @EventListener
    public void onContextStopEvent(ContextStoppedEvent stoppedEvent){
        System.out.println("应用停止事件:"+stoppedEvent);
    }
    @EventListener
    public void onContextStartEvent(ContextStartedEvent startedEvent){
        System.out.println("应用启动事件:"+startedEvent);
    }
}

package net.hualisheng.demo.event;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义一个事件
 */
public class AppEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public AppEvent(Object source) {
        super(source);
    }
}

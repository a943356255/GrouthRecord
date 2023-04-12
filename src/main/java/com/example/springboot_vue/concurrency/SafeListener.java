package com.example.springboot_vue.concurrency;

import com.gargoylesoftware.htmlunit.javascript.host.event.EventSource;
import javafx.event.Event;

import java.util.EventListener;

public class SafeListener {

    private final EventListener eventListener;

    public SafeListener() {
        eventListener = new EventListener() {
            public void doEvent(Event e) {
                // doSomething(e);
            }
        };
    }

    public static SafeListener newInstance(EventSource eventSource) {
        SafeListener safeListener = new SafeListener();
//        eventSource.registerListener(safeListener.eventListener);
        return safeListener;
    }

}

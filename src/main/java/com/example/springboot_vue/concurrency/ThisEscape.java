package com.example.springboot_vue.concurrency;

import com.gargoylesoftware.htmlunit.javascript.host.event.EventSource;

import java.util.EventListener;

public class ThisEscape {

    private int num;

    public ThisEscape(EventSource source) {
        new EventListener() {
            public void onEvent(int num) {
                add(num);
            }
        };
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void add(int val) {
        this.num += val;
    }
}

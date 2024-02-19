package com.example.springboot_vue.thread;

import com.example.springboot_vue.pojo.city.City;

public class ThreadLocalDemo implements Runnable {

    public static final ThreadLocal<City> threadLocal = ThreadLocal.withInitial(City::new);

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        City city = new City("name", "1", "2");
        threadLocal.set(city);
        threadLocal.get();
    }
}

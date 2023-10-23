package juc.thread_local;

import com.example.springboot_vue.pojo.city.City;

public class Main {

    /**
     *
     *
     */
    public static void main(String[] args) {
        ThreadLocal<City> threadLocal = new ThreadLocal<>();
        threadLocal.set(new City());
        City city = threadLocal.get();
    }

}

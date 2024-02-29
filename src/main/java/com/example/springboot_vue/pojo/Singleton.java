package com.example.springboot_vue.pojo;

public class Singleton {

    private volatile static Singleton singleton;

    private Singleton() {

    }

    public Singleton getSingleton() {
        // 每个线程都可以到达该位置，判断实例是否存在
        if (singleton == null) {
            // 多个线程到这一步之后，之后一个线程可以获取到锁
            synchronized (Singleton.class) {
                // 第一个线程创建完对象后，释放锁，后续有线程会获取到锁
                // 这里再次判断就是为了防止等待锁的线程获取到锁然后重复创建
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }

        return singleton;
    }

}

package com.example.springboot_vue.concurrency;

import java.util.ArrayList;
import java.util.List;

class A {
    int a;
    int b;

    public A(int a, int b) {
        this.a = a;
        this.b = b;
    }
}

public class Test {

    private static long count = 0;

    private List<Object> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        A a = new A(2, 3);
        A b = new A(2, 3);
        System.out.println(a);
        System.out.println(b);
//        System.out.println(calc());
    }

    private void add10K() {
        int idx = 0;
        while(idx++ < 10000) {
            count += 1;
        }
    }

    public static long calc() throws InterruptedException {

        final Test test = new Test();
        // 创建两个线程，执行add()操作
        Thread th1 = new Thread(test::add10K);
        Thread th2 = new Thread(test::add10K);

        // 启动两个线程
        th1.start();
        th2.start();

        // 等待两个线程执行结束
        th1.join();
        th2.join();

        return count;
    }
}

class Allocator {

    private List<Object> als = new ArrayList<>();

    // 一次性申请所有资源
    synchronized boolean apply(Object from, Object to) {
        if (als.contains(from) || als.contains(to)) {
            return false;
        } else {
            als.add(from);
            als.add(to);
        }
        return true;
    }

    // 归还资源
    synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
    }
}

class Account {
    // actr应该为单例
    private Allocator actr;
    private int balance;

    // 转账
    void transfer(Account target, int amt) {
        // 一次性申请转出账户和转入账户，直到成功
        while (!actr.apply(this, target));
        try {
            // 锁定转出账户
            synchronized(this) {
                // 锁定转入账户
                synchronized(target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            actr.free(this, target);
        }
    }
}
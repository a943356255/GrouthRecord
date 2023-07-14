package juc;

public interface Queue {

    boolean offer(Object obj) throws InterruptedException;

    Object poll() throws InterruptedException;

}

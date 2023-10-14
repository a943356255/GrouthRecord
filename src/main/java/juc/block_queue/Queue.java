package juc.block_queue;

public interface Queue {

    boolean offer(Object obj) throws InterruptedException;

    Object poll() throws InterruptedException;

}

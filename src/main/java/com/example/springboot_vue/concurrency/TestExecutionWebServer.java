package com.example.springboot_vue.concurrency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class TestExecutionWebServer {

    private static final int READS = 100;

    private static final ExecutorService executor = Executors.newFixedThreadPool(READS);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while (!executor.isShutdown()) {
            try {
                final Socket socket = serverSocket.accept();
                Runnable task = () -> handleRequest(socket);
                executor.execute(task);
            } catch (RejectedExecutionException e) {
                if (!executor.isShutdown()) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        executor.shutdown();
    }

    static void handleRequest(Socket socket) {
        System.out.println("do something with socket");
    }
}

package com.example.springboot_vue.socket.socket.server;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot_vue.socket.socket.operation.Operation;
import com.example.springboot_vue.socket.utils.FileUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static int port = 55533;

    public static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {

        serverSocket = new ServerSocket(port);

        while (true) {
            Socket socket = serverSocket.accept();
            server(socket);
        }

    }

    public static void server(Socket socket) {

        FileUtils fileUtils = new FileUtils();
        Operation operation = new Operation();

        // 开启一个线程来操作当前建立的连接
        new Thread(() -> {
            try {
                InputStream inputStream = socket.getInputStream();
                // 输入流
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                // 带缓冲的
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                // 输出流
                OutputStream outputStream = socket.getOutputStream();
//                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
//                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                String mes = "";
                mes = bufferedReader.readLine();
                // use jsonObject to get the data
                JSONObject jsonObject = JSONObject.parseObject(mes);
                String account = (String) jsonObject.get("account");
                String password = (String) jsonObject.get("password");
                String DBName = (String) jsonObject.get("DBName");

                if (account.equals("root") & password.equals("root") & fileUtils.isDir(DBName)) {
                    outputStream.write("success\n".getBytes());
                    System.out.println("connect successfully");
                } else {
                    outputStream.write("error\n".getBytes());
                    System.out.println("connect failed");
                }

                // 获取到执行的sql
                String sql = bufferedReader.readLine();
                // 根据操作结果返回执行是否成功
                if (operation.operation(sql, DBName)) {
                    System.out.println("sql is right");
                    outputStream.write("true\n".getBytes());
                } else {
                    System.out.println("sql is wrong");
                    outputStream.write("false\n".getBytes());
                }

                bufferedReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }
}

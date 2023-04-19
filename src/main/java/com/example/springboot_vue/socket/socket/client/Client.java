package com.example.springboot_vue.socket.socket.client;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable {

    public static void main(String[] args) {
        new Client().run();
    }

    @Override
    public void run() {
        try {
            // 封装访问服务端的信息
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account", "root");
            jsonObject.put("password", "root");
            jsonObject.put("DBName", "mysql");

            // 将jsonObject类型的转换为string类型
            String mes = jsonObject.toJSONString();

            // get local ip
            InetAddress address = InetAddress.getLocalHost();
            // create the socket by address and ip
            Socket socket = new Socket(address, 55533);

            // 向服务器发送消息
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write((mes+"\n").getBytes());

//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
//            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
//            bufferedWriter.write(Arrays.toString((mes + "\n").getBytes()));
            // 只能写入string类型
//            outputStreamWriter.write(mes);
//            outputStreamWriter.close();

            // 用于接受服务器的返回消息
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String returnMes = bufferedReader.readLine();

            if (returnMes.equals("success")) {
                System.out.println("connect successfully");

                String sql = "select name, age from first";
                outputStream.write((sql+"\n").getBytes());
            } else {
                System.out.println("account or password is wrong or there is no such database");
                return;
            }

            // 用于接受传过去sql语句执行结果
            String sqlMes = bufferedReader.readLine();
            if (sqlMes.equals("true")) {
                System.out.println("execute sql successfully");
            } else {
                System.out.println("there is an error in your sql");
            }

            outputStream.close();

            inputStreamReader.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeBack(String sql, OutputStream outputStream) {
        try {
            outputStream.write((sql+"\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package netty.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class IOClient {

    public static void main(String[] args) {
        // 我自己运行时，这里只创建了一次连接，然后没3s发送一次数据
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    // 网络传输都是传输的字节
                    socket.getOutputStream().write((new Date() + " hello world").getBytes());
                    Thread.sleep(3000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}

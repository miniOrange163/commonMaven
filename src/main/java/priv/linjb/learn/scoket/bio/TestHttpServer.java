package priv.linjb.learn.scoket.bio;

import org.apache.http.protocol.HttpRequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: Linjb
 *
 * @Date: 2019/3/17
 *
 * @name: 模拟一个简单的http服务器，返回一串html文本
 *
 * @Description:
 */
public class TestHttpServer {

    public static void main(String[] args) {
        ServerListeningThread thread = new ServerListeningThread(8222);
        thread.start();
    }


    static class ServerListeningThread extends Thread {

        private int bindPort;
        private ServerSocket serverSocket;

        public ServerListeningThread(int port) {
            this.bindPort = port;
        }

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(bindPort);
                while (true) {
                    Socket rcvSocket = serverSocket.accept();

                    //单独写一个类，处理接收的Socket，类的定义在下面
                    HttpRequestHandler request = new HttpRequestHandler(rcvSocket);
                    request.handle();

                    rcvSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //最后要确保以下把ServerSocket关闭掉
                if (serverSocket != null && !serverSocket.isClosed()) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class HttpRequestHandler {
        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        public void handle() throws IOException {


            //获取输入流，读取数据
            StringBuilder builder = new StringBuilder();
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            char[] charBuf = new char[1024];
            int mark;
            while ((mark = isr.read(charBuf)) != -1) {
                builder.append(charBuf, 0, mark);
                if (mark < charBuf.length) {
                    break;
                }
            }
            System.out.println(builder.toString());


            socket.getOutputStream().
                    write(("HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/html; charset=utf-8\r\n" +
                            "\r\n" +
                            "<h1>这是响应报文</h1>\r\n").getBytes());
        }
    }

}

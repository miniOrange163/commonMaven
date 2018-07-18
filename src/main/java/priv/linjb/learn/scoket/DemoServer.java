package priv.linjb.learn.scoket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/7/14
 *  
 * @name: 简单的Socket测试，客户端连接服务端，服务端输出字符串
 *
 * @Description: 
 */
public class DemoServer extends Thread {

    private ServerSocket serverSocket;

    public static void main(String[] args) {
        DemoServer demoServer = new DemoServer();
        demoServer.start();

        long start = System.currentTimeMillis();

        for (int i = 0; i < 8000; i++) {
            try (Socket client = new Socket(InetAddress.getLocalHost(), demoServer.getPort())) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                reader.lines().forEach(s-> System.out.println(s));

                reader.close();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        long end = System.currentTimeMillis();
        double length = new BigDecimal(end - start).divide(new BigDecimal(1000)).doubleValue();
        System.out.println("调用时长:" + length + "秒");


    }
    public int getPort(){
        return serverSocket.getLocalPort();
    }

    public void run(){

        try {
            serverSocket = new ServerSocket(0);
//            ExecutorService executorService = Executors.newFixedThreadPool(50);
            while (true) {
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
//                executorService.execute(requestHandler);
                requestHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class RequestHandler extends Thread {
        private Socket socket;

        RequestHandler(Socket socket) {
            this.socket = socket;
        }

        public void run(){
            try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {

                out.print("hello world!");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

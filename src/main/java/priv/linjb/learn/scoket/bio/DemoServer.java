package priv.linjb.learn.scoket.bio;

import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
        // 启动服务端
        DemoServer demoServer = new DemoServer();
        demoServer.start();

        long start = System.currentTimeMillis();
        // 循环创建多个客户端进行连接
        for (int i = 0; i < 1; i++) {
            try (Socket client = new Socket(InetAddress.getLocalHost(), demoServer.getPort());
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));) {

                out.write("this is client");
                out.flush();
                client.shutdownOutput();
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
            serverSocket = new ServerSocket(8123);
//            ExecutorService executorService = Executors.newFixedThreadPool(50);
            while (true) {
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
                requestHandler.start();

//                executorService.execute(requestHandler);
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
            System.out.println(123);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                ) {

                String info =null;
                while((info=br.readLine())!=null){
                    System.out.println("我是服务器，客户端说："+info);
                }

                out.write("hello world!");
                out.flush();
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}

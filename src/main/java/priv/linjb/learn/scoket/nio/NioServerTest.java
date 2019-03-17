package priv.linjb.learn.scoket.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/7/14
 *  
 * @name: 使用NIO实现与DemoServer同样的示例
 *
 * @Description: 
 */
public class NioServerTest extends Thread{

    public static void main(String[] args) {
        NioServerTest server = new NioServerTest();
        server.start();

        long start = System.currentTimeMillis();

        for (int i = 0; i < 5000; i++) {
            try (Socket client = new Socket(InetAddress.getLocalHost(), server.getPort())) {

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

    private int port = 8888;
    private int getPort() {
        return port;
    }

    public void run(){

        try(Selector selector = Selector.open();
            ServerSocketChannel serverSocket = ServerSocketChannel.open();){

            serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), port));
            serverSocket.configureBlocking(false);
            // 注册到Selector ，并说明关注点
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select(); // 阻塞等待就绪的Channel,这是关键点之一
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectionKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    // 生产系统中一般会额外进行就绪状态检查
                   sayHello(serverSocket);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sayHello(ServerSocketChannel serverSocket){

        try(SocketChannel client = serverSocket.accept();) {

            client.write(Charset.defaultCharset().encode("hello world"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

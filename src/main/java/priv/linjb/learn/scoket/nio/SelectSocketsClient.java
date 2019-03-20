package priv.linjb.learn.scoket.nio;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2019/3/20
 *  
 * @name: 
 *
 * @Description: 
 */
public class SelectSocketsClient {

    public static void main(String[] args) {

        try (Socket client = new Socket(InetAddress.getLocalHost(), 1234);
             OutputStream outputStream = client.getOutputStream();
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));) {

            outputStream.write(256);
//            outputStream.write(2);
//            outputStream.write(3);
//            outputStream.write(4);


//            byte[] bytes = "s".getBytes();
//            for (byte aByte : bytes) {
//                System.out.println(aByte);
//                outputStream.write(aByte);
//
//            }

//            outputStream.write("str".getBytes());

            outputStream.flush();
            client.shutdownOutput();
            reader.lines().forEach(s-> System.out.println(s));
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

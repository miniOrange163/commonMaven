package priv.linjb.learn.draw;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.junit.Test;
import priv.linjb.common.util.file.FileOperationUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/7/30
 *  
 * @name: 画图的示例，黑底白字
 *
 * @Description: 
 */
public class DrawBlack {

    @Test
    public void black(){

        try(OutputStream  outImage = new FileOutputStream("d:/black.jpg");) {
            int width = 3840;
            int height = 240;
            BufferedImage biNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//在内存中创建新图象
            Graphics2D gNew = (Graphics2D) biNew.getGraphics();// 获取图形上下文
            gNew.setBackground(Color.BLACK);
            Font font = new Font("Microsoft YaHei UI", Font.PLAIN, 40);
            gNew.setFont(font);
            gNew.drawString("你好" , 10, 30);


            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(outImage);
            enc.encode(biNew);
            // 图像生效
            gNew.dispose();
            outImage.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public  void test(){

    }


}

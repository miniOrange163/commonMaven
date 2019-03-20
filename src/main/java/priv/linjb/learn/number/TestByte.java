package priv.linjb.learn.number;

import org.junit.Test;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2019/3/18
 *  
 * @name: 测试byte操作的示例
 *
 * @Description: 
 */
public class TestByte {

    /**
     * 4个byte 转 1个int
     */
    @Test
    public void byte2Int(){
        byte b1 = 0;
        byte b2 = 0;
        byte b3 = 1;
        byte b4 = 10;

        int var = (((b1       ) << 24) |
                   ((b2 & 0xff) << 16) |
                   ((b3 & 0xff) <<  8) |
                   ((b4 & 0xff)      ));
        /**
         * 原理：
         * 1. 第一个byte左移24位即3个字节
         * 2. 第二个byte左移16位即2个字节
         * 3. 第三个byte左移8位即1个字节
         * 4. 第四个byte不变
         * 5. 四个byte同时做异或操作，将每个byte的值映射到int的对应位置
         * 6. 计算出int的最终值
         */

        System.out.println(var);
    }

}

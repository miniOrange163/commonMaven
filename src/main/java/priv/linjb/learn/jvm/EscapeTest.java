package priv.linjb.learn.jvm;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/9/26
 *  
 * @name: 示例 - 逃逸分析
 *
 * @Description: Run with : -XX:+PrintGCDetails -XX:-DoEscapeAnalysis
 */
public class EscapeTest {


    public static void forEach(ArrayList<Object> list, Consumer<Object> f) {
        for (Object obj : list) {

            f.accept(obj);

        }
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();


        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);

        }
        for (int i = 0; i < 400_000_000; i++) {
            forEach(list,obj->{});
        }

        long end = System.currentTimeMillis();
        double length = (end - start) / (double)1000;
        System.out.println("调用时长:" + length + "秒");
    }

}

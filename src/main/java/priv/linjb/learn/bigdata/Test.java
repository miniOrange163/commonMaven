package priv.linjb.learn.bigdata;

import priv.linjb.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Linjb on 2018/1/26.
 *
 * 海量数据处理的测试
 */
public class Test {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            User user = new User("性能"+i,Double.valueOf(Math.random()*10000).intValue());
            list.add(user);

        }
        //设置parallelStream并行流的线程数
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "" + 99);
        List<String> newList = list.parallelStream().filter(u->u.getAge()!=0&&u.getAge()%5000 == 0).map(u->{
            sum(u.getAge());
            return u.getName();
        }).collect(Collectors.toList());

       /* list.sort((u1,u2)->u2.getAge() - u1.getAge());*/

        List subList = newList.stream().limit(10).collect(Collectors.toList());

        subList.stream().forEach(u->{
            System.out.println(u.toString());
        });



        long end = System.currentTimeMillis();

        double length = new BigDecimal(end - start).divide(new BigDecimal(1000)).doubleValue();

        System.out.println("消耗时间:"+length+"秒");

    }

    private static int sum(int n){
        if(n == 1){
            return 1;
        }
        else{
            return sum(n-1) * n;
        }
    }

}

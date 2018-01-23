package priv.linjb.learn.lambda;

import priv.linjb.learn.lambda.util.MyCollectors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Admin on 2017/10/13.
 */
public class ThirdTest {

    /**
     * collect
     *
     * list 按一定规则 转为Map
     */
    public static void listToMap(){

        List<User> list = ListUtil.getUserList();
        /**
         * 接收2个参数
         * 第一个：map的key
         * 第二个: map的value
         *
         * 备注：使用此方法，当value有重复时，会报IllegalStateException异常
         *       当value为null时会报空指针异常
         *
         */
        //Map<String,User> map = list.stream().filter(user->user!=null).collect(Collectors.toMap(user->user.getName(), user->user));
        /**
         * 接收3个参数
         * 第三个:当有重复的value时，如何操作的函数
         *
         * 备注：当value为null时会报空指针异常
         */
        Map<String,User> map2 = list.stream().filter(user->user!=null).collect(Collectors.toMap(user->user.getName(), user->user,(oldValue,newValue) -> newValue));
        /**
         * 接收4个参数
         * 第四个:使用哪个Map的实现类
         *
         * 备注：当value为null时会报空指针异常
         */
        Map<String,User> map3 = list.stream().filter(user->user!=null).collect(Collectors.toMap(user->user.getName(), user->user,(oldValue,newValue) -> newValue,()-> new HashMap()));
        /**
         * 继承了Collectors类并重写了对应的方法
         * 使其允许value为null ，且允许重复的value
         *
         */
        Map<String,User> map4 = list.stream().filter(user->user!=null).collect(MyCollectors.toMap(user->user.getName(), user->user));


        map4.forEach((k,v)->System.out.println("k:" + k + "v:" + v));
    }

    public static void main(String[] args) {

        listToMap();
    }
}

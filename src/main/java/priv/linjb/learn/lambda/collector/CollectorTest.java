package priv.linjb.learn.lambda.collector;

import priv.linjb.entity.User;
import priv.linjb.learn.lambda.ListUtil;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2020/3/20
 *  
 * @name: 
 *
 * @Description: 
 */
public class CollectorTest {

    static final Set<Collector.Characteristics> CH_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

    public static void main(String[] args) {

        final List<User> userList = ListUtil.getUserList();

        Collectors.toList();
        final Map<String, User> collect = userList.stream().collect(Collectors.groupingBy(o -> o.getName(), toList()));

        System.out.println(123);
    }


    public static <T> Collector<T, ?, T> toList() {

        Supplier<T> supplier = ()->{Object o = new Object();return (T) o;};

        return new MyCollectorImpl<>(supplier,
                (left,right)->{
                    left = right;
                },
                                (left, right) -> {
            return left;

            },
                                new HashSet<>()
                            );
    }



}

package priv.linjb.learn.lambda.parallelStream;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Linjb on 2018/1/27.
 * 测试parallelStream在不同的使用方式下得到结果的异同
 */
public class ParallelStreamTestSecond {

    private static final int COUNT = 2000;
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        List<RiderDto> orilist=new ArrayList<RiderDto>();
        for(int i=0;i<COUNT;i++){
            orilist.add(init());
        }

        test1(orilist);
        test2(orilist);

        long end = System.currentTimeMillis();

        double length = new BigDecimal(end - start).divide(new BigDecimal(1000)).doubleValue();

        System.out.println("消耗时间:"+length+"秒");
    }

    /**
     * 在parallelStream并行流的情况下使用forEach在方法内给新的LIST添加元素，
     * 可能会导致新LIST长度与原List长度不一致的情况，
     * 甚至有可能抛出异常
     * @param orilist
     */
    private static void test1(List<RiderDto> orilist){
        System.out.println("第一次测试,使用forEach:");
        final List<RiderDto> copeList=new ArrayList<RiderDto>();
        orilist.parallelStream().forEach(rider -> {
            RiderDto t = new RiderDto();
            t.setId(rider.getId());
            t.setCityId(rider.getCityId());
            copeList.add(t);
        });
        System.out.println("orilist size:"+orilist.size());
        System.out.println("copeList size:"+copeList.size());
        System.out.println("compare copeList and list,result:"+(copeList.size()==orilist.size()));
    }

    /**
     * 使用map生成新的List则会避免发生测试一的情况
     * @param orilist
     */
    private static void test2(List<RiderDto> orilist){

        System.out.println("第二次测试,使用map:");
        final List<RiderDto> copeList = orilist.parallelStream().map(rider -> {
            RiderDto t = new RiderDto();
            t.setId(rider.getId());
            t.setCityId(rider.getCityId());
            return t;
        }).collect(Collectors.toList());

        System.out.println("orilist size:"+orilist.size());
        System.out.println("copeList size:"+copeList.size());
        System.out.println("compare copeList and list,result:"+(copeList.size()==orilist.size()));
    }
    private static RiderDto init() {
        RiderDto t = new RiderDto();
        Random random = new Random();
        t.setId(random.nextInt(2 ^ 20));
        t.setCityId(random.nextInt(1000));
        return t;
    }
    static class RiderDto implements Serializable {
        private static final long serialVersionUID = 1;
        //城市Id
        private Integer cityId;
        //骑手Id
        private Integer id;

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}

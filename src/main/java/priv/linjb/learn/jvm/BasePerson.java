package priv.linjb.learn.jvm;
/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/8/13
 *  
 * @name: 
 *
 * @Description: -XX:CompileCommand=dontinline,*.out 禁用方法内联
 */
public abstract class BasePerson {
    /**
     * 出境
     */
    abstract void out();

    public static void main(String[] args) {
        BasePerson a = new China();
        BasePerson b = new Foreign();
        long current = System.currentTimeMillis();
        int length = 2_000_000_000;
        for (int i = 0; i < length; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }
            BasePerson c = (i < 1_000_000_000) ? a: b;
            if(i == 1_000_000_000){
                System.out.println("change to Foreign");
            }
            c.out();

        }
    }

}
class China extends BasePerson {

    @Override
    void out() {
    }
}
class Foreign extends BasePerson {

    @Override
    void out() {

    }
}
package priv.linjb.learn.jvm.jit;
/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/9/21
 *  
 * @name: 即时编译 - 方法内联 - 示例
 *
 * @Description: run with java:   -XX:CompileCommand=dontinline,JITTest.test  -XX:+PrintCompilation
 *              dontinline：禁用方法内联
 *              PrintCompilation：打印编译信息
 *
 */
public class JITTest {

    static abstract class BinaryOp{
        public abstract int apply(int a, int b);
    }

    static class Add extends BinaryOp {

        @Override
        public int apply(int a, int b) {
            return a + b;
        }


    }

    static class Sub extends BinaryOp {
        @Override
        public int apply(int a, int b) {
            return a - b;
        }
    }


    public static int test(BinaryOp op) {
        return op.apply(2, 1);
    }


    public static void main(String[] args) throws InterruptedException {
        Add add = new Add();
        for (int i = 0; i < 400_000; i++) {
            test(add);
        }

        Thread.sleep(2000);

        System.out.println("Loading Sub");

        Sub[] array = new Sub[0];//Load class Sub
        // Expect output: "JITTest::test(7 bytes) made not entrant"
        Thread.sleep(2000);

    }



}

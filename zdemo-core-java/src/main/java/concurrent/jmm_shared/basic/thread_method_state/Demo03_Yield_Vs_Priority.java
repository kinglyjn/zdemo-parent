package concurrent.jmm_shared.basic.thread_method_state;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Kingly
 * @Date 2020/2/17
 * @Description
 *
 * 分别对 priority 和 yield 两种方式让出时间片的效果做测试
 *
 * 线程优先级会提示（hint）调度器优先调度该线程，但它仅仅是一个提示，调度器可以忽略它
 * 如果 cpu 比较忙，那么优先级高的线程会获得更多的时间片，但 cpu 闲时，优先级几乎没作用
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_method_state.Demo03_Yield_Vs_Priority")

public class Demo03_Yield_Vs_Priority {

    public static void main(String[] args) {
        // testPriority();
        testYield();
    }


    public static void testPriority() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                for(;;) {
                    log.debug("{}", i++);
                }
            }
        };

        Thread t1 = new Thread(task, "t1");
        Thread t2 = new Thread(task, "t2");
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
    }


    public static void testYield() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                for (;;) {
                    log.debug("{}", i++);
                    Thread.yield();
                }
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                for (;;) {
                    log.debug("{}", i++);
                }
            }
        }, "t2");
        t1.start();
        t2.start();
    }

}

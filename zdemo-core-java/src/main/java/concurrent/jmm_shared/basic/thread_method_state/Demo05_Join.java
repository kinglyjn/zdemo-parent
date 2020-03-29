package concurrent.jmm_shared.basic.thread_method_state;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Kingly
 * @Date 2020/2/17
 * @Description
 *
 * join用来在一个线程中同步等待另一个线程，底层使用wait实现
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_method_state.Demo05_Join")
public class Demo05_Join {
    private static int i = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i = 10;
            }
        }, "t1");
        t1.start();

        // 主线程在"同步"等待t1线程（同步的概念）
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("{}", i);
    }

}

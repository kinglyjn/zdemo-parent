package concurrent.jmm_shared.basic.thread_method_state;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Kingly
 * @Date 2020/2/17
 * @Description
 *
 * 向调度程序提示当前线程愿意产生当前处理器的使用。计划程序可以自由忽略此提示。
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_method_state.Demo02_Yield")
public class Demo02_Yield {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.debug("{}", Thread.currentThread().getState());
                Thread.yield();
                log.debug("{}", Thread.currentThread().getState());
            }
        };
        t1.start();
    }
}

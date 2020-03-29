package concurrent.jmm_shared.basic.thread_method_state;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * park & unpark 底层原理：
 *
 * 每个线程都有自己的一一个 Pafker对象，由三部分组成 _counter、_cond 和 _mutex，这是底层虚拟机C语言的实现
 * 打个比喻，线程就像一个旅人，Parker 就像他随身携带的背包，条件变量就好比背包中的帐篷。counter 就好比背包中的备用干粮（0 为耗尽，1为充足）
 * 调用park就是要看需不需要停下来歇息
 *    如果备用干粮耗尽，那么钻进帐篷歇息
 *    如果备用干粮充足，那么不需停留，继续前进
 * 调用unpark，就好比令干粮充足
 *    如果这时线程还在帐篷，就唤醒上他继续前进
 *    如果这时线程还在运行，那么下次他调用park时，仅是消耗掉备用干粮，不需停留继续前进
 *    因为背包空间有限，多次调用unpark仅会补充一份备用干粮
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_method_state.Demo07_Park")
public class Demo07_Park {

    public static void main(String[] args) throws InterruptedException {
        // testParkAndInterrupt();
        testParkAndUnpark();
    }

    /**
     * 测试park方法和打断park线程
     *
     */
    public static void testParkAndInterrupt() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("parking...");

                // 如果没有被打断，则一直等（注意如果线程的打断标记为true，那么park方法就会失效）
                LockSupport.park();

                log.debug("unpark");
                log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        t1.interrupt();
    }

    /**
     * 测试park和unpark
     *
     * 和Object的wait和notify相比：
     * 1. wait、notify&notifyAll必须配合Object Monitor一起使用，而park、unpark不必
     * 2. park、unpark是以线程为单位来阻塞和唤醒线程，而notify只能随机唤醒一个等待线程，notifyAll只能唤醒所有等待线程，就不那么精确
     * 3. park、unpark可以先unpark，而wait、notify不能先notify
     */
    public static void testParkAndUnpark() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("parking");
                LockSupport.park();

                log.debug("unpark");
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        LockSupport.unpark(t1);
    }
}

package concurrent.jmm_shared.basic.thread_method_state;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Kingly
 * @Date 2020/2/17
 * @Description
 *
 * 调用 sleep 会让当前线程从 Runnable 进入 Timed Waiting 状态
 *
 *  关于打断标记 isInterrupted()：
 * 1. sleep，park，wait，join这几个方法都会让线程进入非运行状态，打断这样的线程，这个打断的线程会以异常的形式表示被打算了，而且被打断的线程会清空打断状态（打断标记为false）
 * 2. 打断正常运行的线程，这个线程还会正常运行，只是打算标记会被置为true
 * 3. 可以利用打断标记来判断线程被打算后，是继续运行，还是就此终止
 *
 * 关于 isInterrupted() 和 interrupted() 两个方法的区别：
 * isInterrupted 调用之后不会清空打断标记，而interrupted 调用之后将会清空打断标记
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_method_state.Demo01_Sleep_Interrupt")
public class Demo01_Sleep_Interrupt {

    public static void main(String[] args) throws InterruptedException {
        //testInterruptSleep();
        testInterruptNormal();
    }

    /**
     * 测试打断sleep状态的线程
     *
     */
    public static void testInterruptSleep() throws InterruptedException {
        // t1
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.debug("线程睡眠时被打断");
                    log.debug("t1线程的打断标记为{}", Thread.currentThread().isInterrupted()); // false
                }
            }
        }, "t1");
        t1.start();

        // main
        Thread.sleep(1000);
        t1.interrupt();
    }

    /**
     * 测试打断正常状态的线程
     * 这里interrupt为线程的结束提供了一种比较优雅的方式
     *
     */
    public static void testInterruptNormal() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    boolean isInterrupted = Thread.currentThread().isInterrupted();
                    if (isInterrupted) {
                        log.debug("被打断了，退出循环");
                        log.debug("打断标记为{}", Thread.currentThread().isInterrupted()); // true
                        break;
                    }
                }
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        t1.interrupt();
    }

}

package concurrent.jmm_shared.basic.thread_create;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author Kingly
 * @Date 2020/1/19
 * @Description
 *
 * DEMO: 直接启动线程
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_create.Demo01_Thread")
public class Demo01_Thread {

    /**
     * 线程类
     *
     */
    static class T extends Thread {
        private volatile boolean isTerminated = false;
        public synchronized void terminate() {
            this.isTerminated = true;
        }

        @Override
        public void run() {
            while (!isTerminated) {
                log.debug(Thread.currentThread().getName());
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        T t = new T();
        //t.setDaemon(true);
        t.start();

        log.debug("睡5s开始");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("睡5s结束");

        log.debug("杀死" + t.getName() + "线程");
        t.terminate();
    }

}

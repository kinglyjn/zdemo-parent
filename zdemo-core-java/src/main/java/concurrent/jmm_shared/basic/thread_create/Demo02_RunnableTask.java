package concurrent.jmm_shared.basic.thread_create;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author Kingly
 * @Date 2020/1/19
 * @Description
 *
 * DEMO: 通过线程任务启动线程
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_create.Demo02_RunnableTask")
public class Demo02_RunnableTask {

    /**
     * 线程任务
     *
     */
    static class R implements Runnable {
        @Override
        public void run() {
            while (true) {
                log.debug(Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new R()).start();
    }
}

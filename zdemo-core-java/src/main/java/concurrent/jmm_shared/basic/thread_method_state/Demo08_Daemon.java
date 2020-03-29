package concurrent.jmm_shared.basic.thread_method_state;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试守候线程
 *
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_method_state.Demo08_Daemon")
public class Demo08_Daemon {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                }
                log.debug("结束");
            }
        }, "t1");
        t1.setDaemon(true);
        t1.start();

        Thread.sleep(3000);
        log.debug("结束");
    }

}

package concurrent.jmm_shared.basic.thread_create;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Kingly
 * @Date 2020/2/2
 * @Description
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_create.Demo05_ExecutorTask")
public class Demo05_ExecutorTask {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        for (int i=0; i<15; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    log.debug(Thread.currentThread().getName());
                }
            });
        }
        
        // shutdown
        //pool.shutdown();
    }

}

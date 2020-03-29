package concurrent.jmm_shared.basic.thread_create;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author Kingly
 * @Date 2020/1/20
 * @Description
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_create.Demo03_FutureTask")
public class Demo03_FutureTask {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // futureTask and callable
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("进行紧张的计算中。。。");
                Thread.sleep(2000);
                return 1;
            }
        });

        new Thread(task).start();
        log.debug("计算结果：{}", task.get());

    }
    
}



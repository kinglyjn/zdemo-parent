package concurrent.jmm_shared.basic.thread_create;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author Kingly
 * @Date 2020/2/2
 * @Description
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_create.Demo04_TimerTask")
public class Demo04_TimerTask {

    public static void main(String[] args) throws InterruptedException {
        // timer task
        Timer timer = new Timer();

        //
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.debug("{}", new Date().getTime());
            }
        }, 2000, 1000);
        Thread.sleep(10000);

        //
        timer.cancel();

    }

}

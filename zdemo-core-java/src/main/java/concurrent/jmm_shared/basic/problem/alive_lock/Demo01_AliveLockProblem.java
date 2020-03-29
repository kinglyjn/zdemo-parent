package concurrent.jmm_shared.basic.problem.alive_lock;

import lombok.extern.slf4j.Slf4j;

/**
 * 活锁问题演示01
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.shared_data_security.Demo01_AliveLockProblem")
public class Demo01_AliveLockProblem {
    private static volatile int count = 10;

    public static void main(String[] args) {
        new Thread("t1"){
            @Override
            public void run() {
                while (count > 0) {
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count--;
                    log.debug("count={}", count);
                }
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                while (count < 20) {
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    log.debug("count={}", count);
                }
            }
        }.start();
    }
}

package concurrent.jmm_shared.basic.thread_method_state;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Kingly
 * @Date 2020/2/19
 * @Description
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_method_state.Demo09_State")
public class Demo09_State {
    public static void main(String[] args) {
        // t1
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running");
            }
        };

        // t2
        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                while (true) {
                    // TODO
                }
            }
        };
        t2.start();

        // t3
        Thread t3 = new Thread("t3") {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t3.start();

        // t4
        Thread t4 = new Thread("t4") {
            @Override
            public void run() {
                synchronized (Demo09_State.class) {
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t4.start();

        // t5
        Thread t5 = new Thread("t5") {
            @Override
            public void run() {
                synchronized (Demo09_State.class) {
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t5.start();

        // t6
        Thread t6 = new Thread("t6") {
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t6.start();

        log.debug("t1 state {}", t1.getState()); // NEW
        log.debug("t2 state {}", t2.getState()); // RUNNABLE
        log.debug("t3 state {}", t3.getState()); // TERMINATED
        log.debug("t4 state {}", t4.getState()); // TIMED_WAITING
        log.debug("t5 state {}", t5.getState()); // BLOCKED
        log.debug("t6 state {}", t6.getState()); // WAITING
    }
}

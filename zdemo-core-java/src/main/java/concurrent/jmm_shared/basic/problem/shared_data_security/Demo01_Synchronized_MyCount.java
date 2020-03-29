package concurrent.jmm_shared.basic.problem.shared_data_security;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.shared_data_security.Demo01_Synchronized_MyCount")
public class Demo01_Synchronized_MyCount {

    public static void main(String[] args) throws InterruptedException {
        // testSharedDataSecurityProblem();
        testSolveSharedDataSecurityProblem();
    }

    /**
     * 复现线程安全性问题
     */
    public static void testSharedDataSecurityProblem() throws InterruptedException {
        class MyCount {
            private int i = 0;
            public MyCount(int i) {
                this.i = i;
            }
            public void increment() {
                this.i++;
            }
            public void decrement() {
                this.i--;
            }
            public int value() {
                return this.i;
            }
        }
        MyCount count = new MyCount(0);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5000; i++) {
                    count.increment();
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5000; i++) {
                    count.decrement();
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("公共资源count为：{}", count.value());
    }


    /**
     * 加锁解决线程安全性问题
     * synchronized 实际是用对象锁保证了临界区内代码的原子性，临界区内的代码对外是不可分割的，不会被线程切 换所打断。
     */
    public static void testSolveSharedDataSecurityProblem() throws InterruptedException {
        class MyCount {
            private int i = 0;
            public MyCount(int i) {
                this.i = i;
            }
            public synchronized void increment() {
                this.i++;
            }
            public synchronized void decrement() {
                this.i--;
            }
            public synchronized int value() {
                return this.i;
            }
        }
        MyCount count = new MyCount(0x0);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5000; i++) {
                    count.increment();
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5000; i++) {
                    count.decrement();
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("公共资源count为：{}", count.value());
    }

}

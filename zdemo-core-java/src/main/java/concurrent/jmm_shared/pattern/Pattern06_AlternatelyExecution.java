package concurrent.jmm_shared.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交替执行的几种实现方式
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.pattern.Pattern06_AlternatelyExecution")
public class Pattern06_AlternatelyExecution {
    public static void main(String[] args) throws InterruptedException {
        // testWaitNotifyAll();
        // testReentrantLock();
        testPark();
    }

    /**
     * WaitNotifyAll实现
     */
    public static void testWaitNotifyAll() {
        class AlternatelyPrinter {
            private int loopNum;
            private int flag;

            public AlternatelyPrinter(int flag, int loopNum) {
                this.flag = flag;
                this.loopNum = loopNum;
            }

            public void print(String content, int currentFlag, int nextFlag) {
                for (int i=0; i<loopNum; i++) {
                    synchronized (this) {
                        while (flag != currentFlag) {
                            try {
                                this.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        // wait aside code
                        log.debug("{}", content);
                        flag = nextFlag;
                        this.notifyAll();
                    }
                }
            }
        }
        AlternatelyPrinter printer = new AlternatelyPrinter(1, 5);

        new Thread("t1"){
            @Override
            public void run() {
                printer.print("A", 1, 2);
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                printer.print("B", 2, 3);
            }
        }.start();

        new Thread("t3"){
            @Override
            public void run() {
                printer.print("C", 3, 1);
            }
        }.start();
    }

    /**
     * ReentrantLock实现
     */
    public static void testReentrantLock() {
        class AlternatelyPrinter extends ReentrantLock {
            private int loopNum = 5;
            public AlternatelyPrinter(int loopNum) {
                this.loopNum = loopNum;
            }

            public void print(String content, Condition currentCondition, Condition nextCondition) {
                for (int i=0; i<loopNum; i++) {
                    lock();
                    try {
                        // rLock代码区
                        try {
                            currentCondition.await(); // 此处没有使用标准的while自旋方式进行等待，而是在其他线程启动某个等待的线程
                            log.debug("{}", content);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                        // wait aside code
                        nextCondition.signal();
                    } finally {
                        unlock();
                    }
                }

            }
        }
        AlternatelyPrinter printer = new AlternatelyPrinter(5);
        Condition condition1 = printer.newCondition();
        Condition condition2 = printer.newCondition();
        Condition condition3 = printer.newCondition();

        new Thread("t1"){
            @Override
            public void run() {
                printer.print("A", condition1, condition2);
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                printer.print("B", condition2, condition3);
            }
        }.start();

        new Thread("t3"){
            @Override
            public void run() {
                printer.print("C", condition3, condition1);
            }
        }.start();

        // 启动
        printer.lock();
        try {
            condition1.signal();
        } finally {
            printer.unlock();
        }
    }


    /**
     * park-unpark实现
     */
    static Thread t1;
    static Thread t2;
    static Thread t3;
    public static void testPark() throws InterruptedException {
        class AlternatelyPrinter {
            private int loopNum = 5;
            public AlternatelyPrinter(int loopNum) {
                this.loopNum = loopNum;
            }
            public void print(String content, Thread nextToBeWaked) {
                for (int i=0; i<loopNum; i++) {
                    LockSupport.park();
                    log.debug("{}", content);
                    LockSupport.unpark(nextToBeWaked);
                }
            }
        }
        AlternatelyPrinter printer = new AlternatelyPrinter(5);

        t1 = new Thread("t1"){
            @Override
            public void run() {
                printer.print("A", t2);
            }
        };
        t2 = new Thread("t2"){
            @Override
            public void run() {
                printer.print("B", t3);
            }
        };
        t3 = new Thread("t3"){
            @Override
            public void run() {
                printer.print("C", t1);
            }
        };
        t1.start();
        t2.start();
        t3.start();

        // 启动
        Thread.sleep(1000);
        LockSupport.unpark(t1);
    }
}

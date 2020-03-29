package concurrent.jmm_shared.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步模式实现的几种方式（同步就是保证t1先于t2执行）
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.pattern.Pattern05_SyncMode")
public class Pattern05_SyncMode {
    private static boolean executable = false; // 用于线程自旋判断，只要还是不可执行状态，就一直自旋
    private static Object oLock = new Object();
    private static ReentrantLock rLock = new ReentrantLock();
    private static Condition rLockCondition = rLock.newCondition();

    public static void main(String[] args) {
        // testAsyncMode();
        // testWaitNotifyAllSyncMode();
        // testReentrantLockSyncMode();
        // testParkSyncMode();
    }

    /**
     * 首先我们先展示异步执行的情况，这里不能保证是t1开始执行还是t2开始执行
     */
    public static void testAsyncMode() {
        new Thread("t1"){
            @Override
            public void run() {
                log.debug("running");
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                log.debug("running");
            }
        }.start();
    }


    /**
     * wait-notifyAll方式实现同步
     * 更简单的方式是使用join，其底层实现也就是wait-notifyAll
     */
    public static void testWaitNotifyAllSyncMode() {
        new Thread("t2") {
            @Override
            public void run() {
                synchronized (oLock) {
                    while (!executable) {
                        try {
                            oLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    // wait aside 区
                    log.debug("running");
                }
            }
        }.start();

        new Thread("t1") {
            @Override
            public void run() {
                synchronized (oLock) {
                    log.debug("running");
                    oLock.notifyAll();
                    executable = true;
                }
            }
        }.start();
    }


    /**
     * ReentrantLock方式实现同步
     */
    public static void testReentrantLockSyncMode() {
        new Thread("t2"){
            @Override
            public void run() {
                rLock.lock();
                try {
                    while (!executable) {
                        try {
                            rLockCondition.await();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    // wait aside 区
                    log.debug("running");
                } finally {
                    rLock.unlock();
                }
            }
        }.start();

        new Thread("t1"){
            @Override
            public void run() {
                rLock.lock();
                try {
                    log.debug("running");
                    rLockCondition.signal();
                    executable = true;
                } finally {
                    rLock.unlock();
                }
            }
        }.start();
    }

    /**
     * park-unpark方式实现同步
     */
    public static void testParkSyncMode() {
        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                LockSupport.park();
                log.debug("running");
            }
        };
        t2.start();

        new Thread("t1") {
            @Override
            public void run() {
                log.debug("running");
                LockSupport.unpark(t2);
            }
        }.start();
    }

}

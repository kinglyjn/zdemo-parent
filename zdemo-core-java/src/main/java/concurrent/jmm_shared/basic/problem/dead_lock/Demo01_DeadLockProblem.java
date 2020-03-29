package concurrent.jmm_shared.basic.problem.dead_lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 死锁问题演示01
 * 解决死锁问题可以使用 ReentrantLock.tryLock（尝试获取锁，如果没有获取到就暂时释放锁）
 *
 */
public class Demo01_DeadLockProblem {


    public static void main(String[] args) {
        // testDeadLockProblem();
        testSolveDeadLockProblem();
    }

    /**
     * 重现死锁问题
     */
    public static void testDeadLockProblem() {
        Object lock1 = new Object();
        Object lock2 = new Object();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println("[t1] 获取lock1");
                    sleep(1000);
                    synchronized (lock2) {
                        System.out.println("[t1] 获取lock2");
                    }
                }
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock2) {
                    System.out.println("[t2] 获取lock2");
                    sleep(1000);
                    synchronized (lock1) {
                        System.out.println("[t2] 获取lock1");
                    }
                }
            }
        }, "t2").start();
    }

    /**
     * 解决死锁问题
     */
    public static void testSolveDeadLockProblem() {
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    boolean isLock1Acquired = lock1.tryLock();
                    if (isLock1Acquired) {
                        try {
                            // lock1代码区
                            System.out.println("[t1] 获取lock1");
                            while (true) {
                                boolean isLock2Acquired = lock2.tryLock();
                                if (isLock2Acquired) {
                                    try {
                                        // lock2代码区
                                        System.out.println("[t1] 获取lock2");
                                    } finally {
                                        lock2.unlock();
                                        break;
                                    }
                                }
                            }
                        } finally {
                            lock1.unlock();
                            break;
                        }
                    }
                    sleep(50);
                }
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    boolean isLock2Acquired = lock2.tryLock();
                    if (isLock2Acquired) {
                        try {
                            // lock2代码区
                            System.out.println("[t2] 获取lock2");
                            while (true) {
                                boolean isLock1Acquired = lock1.tryLock();
                                if (isLock1Acquired) {
                                    try {
                                        // lock1代码区
                                        System.out.println("[t2] 获取lock1");
                                    } finally {
                                        lock1.unlock();
                                        break;
                                    }
                                }
                            }
                        } finally {
                            lock2.unlock();
                            break;
                        }
                    }
                    sleep(50);
                }
            }
        }, "t2").start();

    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

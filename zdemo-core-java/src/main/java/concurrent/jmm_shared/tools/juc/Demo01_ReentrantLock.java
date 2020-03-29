package concurrent.jmm_shared.tools.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁测试
 *
 * ReentrantLock与synchronized都支持可重入特性，但相对于synchronized它还具有如下特点：
 * 1. 可打断
 * 2. 可以设置超时时间
 * 3. 可以设置为公平锁
 * 4. 支持多个变量条件
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.tools.juc.Demo01_ReentrantLock")
public class Demo01_ReentrantLock {

    public static void main(String[] args) throws InterruptedException {
        // testBasicUsage();
        // testReentrant();
        // testInterrupt();
        // testTryLock();
        // testFair();
        testCondition();
    }

    /**
     * 基本用法测试
     *
     */
    public static void testBasicUsage() throws InterruptedException {
        class Counter {
            int i = 0;
            ReentrantLock lock = new ReentrantLock();

            void increment() {
                lock.lock();
                try {
                    // 临界区1
                    i++;
                } finally {
                    lock.unlock();
                }
            }
            void decrement() {
                lock.lock();
                try {
                    // 临界区2
                    i--;
                } finally {
                    lock.unlock();
                }
            }

            public int get() {
                return i;
            }
        }
        Counter counter = new Counter();

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    counter.increment();
                }
            }
        };

        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    counter.decrement();
                }
            }
        };

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("count={}", counter.get());
    }


    /**
     * 测试可重入性
     */
    public static void testReentrant() {
        ReentrantLock lock = new ReentrantLock();

        class A {
            void method1() {
                lock.lock();
                try {
                    System.out.println("A.method1");
                } finally {
                    lock.unlock();
                }
            }
        }
        class B {
            void method2() {
                lock.lock();
                try {
                    System.out.println("B.method2");
                    new A().method1();
                } finally {
                    lock.unlock();
                }
            }
        }
        new B().method2();
    }

    /**
     * 测试可打断性（可以用打断机制来避免死锁问题，但是是被动地避免死等）
     */
    public static void testInterrupt() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    log.debug("尝试获取锁");
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    log.debug("尝试获取锁时被打断，需返回");
                    return;
                }

                try {
                    log.debug("获取到了锁");
                } finally {
                    lock.unlock();
                }
            }
        };

        lock.lock();
        t1.start();

        Thread.sleep(3000);
        log.debug("打断t1线程");
        t1.interrupt();
    }

    /**
     * 测试锁超时（可以主动地避免线程因没有获取到锁而死等）
     */
    public static void testTryLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    log.debug("在2s内尝试获取锁");
                    boolean isAcquired = lock.tryLock(2, TimeUnit.SECONDS);
                    if (!isAcquired) {
                        log.debug("没有获取到锁，代码返回");
                        return;
                    }
                } catch (InterruptedException e) {
                    log.debug("尝试获取锁时被打断，代码返回");
                    return;
                }

                try {
                    log.debug("获取到了锁");
                } finally {
                    lock.unlock();
                }
            }
        };

        lock.lock();
        t1.start();
        Thread.sleep(1000);
        lock.unlock();
    }

    /**
     * 测试重入锁的公平和非公平性
     * 1. 公平锁保证了先进入等待队列的线程可以先执行，本意是解决线程的饥饿问题
     * 2. 如果没有特殊需要可以默认使用ReentrantLock的非公平锁，因为公平锁会降低并发度
     */
    public static void testFair() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(false); // 默认为非公平锁
        for (int i = 0; i < 500; i++) {
            new Thread("t"+i) {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        // lock代码区
                        log.debug("running");
                    } finally {
                        lock.unlock();
                    }
                }
            }.start();
        }

        // 1s后主线程跟上述线程争抢锁
        Thread.sleep(1000);
        new Thread("tx") {
            @Override
            public void run() {
                lock.lock();
                try {
                    // lock代码区
                    log.debug("running");
                } finally {
                    lock.unlock();
                }
            }
        }.start();
    }

    /**
     * 重入锁的条件变量：
     * synchronized中也有条件变量，就是我们讲原理时那个waitSet休息室，当条件不满足时进入waitSet
     * 等待ReentrantL ock的条件变量比synchronized强大之处在于，它是支持多个条件变量的，这就好比
     * synchronized 是那些不满足条件的线程都在一间休息室等消息。而ReentrantLock支持多间休息室，
     * 有专门等烟的休息室、专门等早餐的休息室、唤醒时也是按休息室来唤醒。
     *
     * 使用流程：
     * 1. await前需要获得锁
     * 2. await 执行后，会释放锁，进入conditionObject 等待
     * 3. await 的线程被唤醒（或打断、或超时）取重新竞争lock锁
     * 4. 竞争lock锁成功后，从await后继续执行
     */
    static ReentrantLock lock = new ReentrantLock();
    static Condition hasCigaretteCondition = lock.newCondition();
    static Condition hasTakeoutCondition = lock.newCondition();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;
    public static void testCondition() throws InterruptedException {
        new Thread("张三") {
            @Override
            public void run() {
                lock.lock();
                try {
                    // lock代码区
                    while (true) {
                        if (hasCigarette) {
                            break;
                        }
                        try {
                            hasCigaretteCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    log.debug("有烟了，干活");
                } finally {
                    lock.unlock();
                }
            }
        }.start();

        new Thread("小娟") {
            @Override
            public void run() {
                lock.lock();
                try {
                    // lock代码区
                    while (!hasTakeout) {
                        try {
                            hasTakeoutCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    log.debug("有外卖了，干活");
                } finally {
                    lock.unlock();
                }
            }
        }.start();

        new Thread("送烟的"){
            @Override
            public void run() {
                log.debug("过了1s，送烟的来了");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lock.lock();
                try {
                    hasCigaretteCondition.signal();
                    hasCigarette = true;
                } finally {
                    lock.unlock();
                }
            }
        }.start();

        new Thread("送外卖的"){
            @Override
            public void run() {
                log.debug("过了2s，送外卖的来了");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lock.lock();
                try {
                    hasTakeoutCondition.signal();
                    hasTakeout = true;
                } finally {
                    lock.unlock();
                }
            }
        }.start();
    }


}

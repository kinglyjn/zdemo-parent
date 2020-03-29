package concurrent.jmm_shared.basic.problem.low_concurrent_level;

import lombok.extern.slf4j.Slf4j;

/**
 * 并发度低的问题
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.problem.low_concurrent_level.Demo01_LowConcurrentLevelProblem")
public class Demo01_LowConcurrentLevelProblem {

    public static void main(String[] args) {
        // testLowConcurrentLevelProblem();
        testSolveProblemByUsingMultiLocks();
    }

    /**
     * 一间大屋子有两个功能:睡觉、学习，互不相干。
     * 现在张三要学习，小娟要睡觉，但如果只用一间屋子(一个对象锁)的话，那么并发度很低
     */
    public static void testLowConcurrentLevelProblem() {
        class BigRoom {
            public void sleep() {
                synchronized (this) {
                    log.debug("睡觉2s开始");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.debug("睡觉2s结束");
                }
            }
            public void study() {
                synchronized (this) {
                    log.debug("学习1s开始");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.debug("学习1s结束");
                }
            }
        }

        BigRoom bigRoom = new BigRoom();
        new Thread("张三"){
            @Override
            public void run() {
                bigRoom.study();
            }
        }.start();
        new Thread("小娟"){
            @Override
            public void run() {
                bigRoom.sleep();
            }
        }.start();
    }


    /**
     * 解决方法1:
     * 准备多个房间(多个对象锁)
     *
     * 好处：是可以增强并发度
     * 坏处：如果一个线程需要同时获得多把锁，就容易发生死锁
     */
    public static void testSolveProblemByUsingMultiLocks() {
        class BigRoom {
            private Object studyRoom = new Object();
            private Object bedRoom = new Object();
            public void sleep() {
                synchronized (bedRoom) {
                    log.debug("睡觉2s开始");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.debug("睡觉2s结束");
                }
            }
            public void study() {
                synchronized (studyRoom) {
                    log.debug("学习1s开始");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.debug("学习1s结束");
                }
            }
        }

        BigRoom bigRoom = new BigRoom();
        new Thread("张三"){
            @Override
            public void run() {
                bigRoom.study();
            }
        }.start();
        new Thread("小娟"){
            @Override
            public void run() {
                bigRoom.sleep();
            }
        }.start();
    }



}

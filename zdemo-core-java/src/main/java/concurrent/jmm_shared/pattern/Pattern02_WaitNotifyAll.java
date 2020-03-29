package concurrent.jmm_shared.pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 等待-唤醒的正确用法
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.pattern.Pattern02_WaitNotifyAll")
public class Pattern02_WaitNotifyAll {

    static class MyLock {
        public volatile boolean hasCigarette = false; // 有烟吗
        public volatile boolean hasTakeout = false;   // 有外卖吗
    }
    private static MyLock myLock = new MyLock();


    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() { // 张三没烟干不了活
            @Override
            public void run() {
               synchronized (myLock) {
                   while (!myLock.hasCigarette) {
                       try {
                           myLock.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                           return;
                       }
                   }
                   log.debug("有烟了，干活^_^");
               }
            }
        }, "张三");
        t1.start();

        Thread t2 = new Thread(new Runnable() { // 小娟没外卖干不了活
            @Override
            public void run() {
                synchronized (myLock) {
                    while (!myLock.hasTakeout) {
                        try {
                            myLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    log.debug("有外卖了，干活^_^");
                }
            }
        }, "小娟");
        t2.start();

        Thread t3 = new Thread("李四") { // 唤醒线程
            @Override
            public void run() {
                synchronized (myLock) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 送烟喽
                    myLock.hasCigarette = true;
                    // 唤醒所有等待线程
                    myLock.notifyAll();
                }
            }
        };
        t3.start();
    }

}

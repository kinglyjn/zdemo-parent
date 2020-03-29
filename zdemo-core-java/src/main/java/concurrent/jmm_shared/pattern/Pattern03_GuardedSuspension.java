package concurrent.jmm_shared.pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 保护性暂停模式：
 * 用于在一个线程中等待另一个线程的执行结果
 *
 * 要点：
 * 1. 有一个结果需要从一个线程传递到另外一个线程，让他们关联到同一个GuardedObject
 * 2. 如果有结果不断从一个线程传递到另外一个线程，那么就可以使用消息队列（见生产者-消费者模式）
 * 3. JDK中join、Future的实现，采用的都是此模式
 * 4. 因为要等待另一方的执行结果，因此将其归类到同步模式
 *
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.pattern.Pattern03_GuardedSuspension")
public class Pattern03_GuardedSuspension {

    /**
     * 模拟 线程1 等待 线程2 的下载结果
     *
     */
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Object response = guardedObject.get(6000);
                log.debug("response={}", response);
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("执行下载");
                try {
                    Thread.sleep(5000);
                    guardedObject.complete("《这是下载结果》");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("下载完成");
            }
        }, "t2").start();

    }


    static class GuardedObject {
        // 结果
        private Object response;

        // 获取结果
        public Object get() {
            synchronized (this) {
                while (response==null) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response;
        }

        // 获取结果（可设置超时时间）
        public Object get(long timeout) {
            synchronized (this) {
                if (timeout < 0) {
                    throw new IllegalArgumentException("非法参数");
                }
                long startTime = System.currentTimeMillis();
                long passedTime = 0;
                long needWaitTime;
                while (response==null) {
                    needWaitTime = timeout - passedTime;
                    if (needWaitTime <= 0) {
                        break;
                    }
                    //
                    try {
                        this.wait(needWaitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //
                    passedTime = System.currentTimeMillis() - startTime;
                }
            }
            return response;
        }

        // 产生结果
        public void complete(Object response) {
            synchronized (this) {
                this.response = response;
                this.notifyAll();
            }
        }
    }
}

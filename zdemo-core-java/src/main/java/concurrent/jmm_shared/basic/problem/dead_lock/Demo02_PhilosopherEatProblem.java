package concurrent.jmm_shared.basic.problem.dead_lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 哲学家就餐问题（也是一个著名的死锁例子）
 * 解决死锁问题使用 ReentrantLock.tryLock，这样也不会出现 线程的公平性问题（饥饿问题）
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.shared_data_security.Demo02_PhilosopherEatProblem")
public class Demo02_PhilosopherEatProblem {

    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");

        new Philosopher("苏格拉底", c1, c2).start();
        new Philosopher("柏拉图", c2, c3).start();
        new Philosopher("亚里士多德", c3, c4).start();
        new Philosopher("赫拉克利特", c4, c5).start();
        new Philosopher("阿基米德", c5, c1).start();
    }

    static class Philosopher extends Thread {
        private Chopstick left;  //左手筷子
        private Chopstick right; //右手筷子

        public Philosopher(String name, Chopstick left, Chopstick right) {
            super(name);
            this.left = left;
            this.right = right;
        }

        public void eat() {
            log.debug("吃饭");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            /*
            // 会导致死锁问题
            while (true) {
                synchronized (left) {
                    synchronized (right) {
                        eat();
                    }
                }
            }
            */

            // 不会导致死锁问题
            while (true) {
                boolean isLeftAcquired = left.tryLock();
                if (isLeftAcquired) {
                    try {
                        // left代码区
                        boolean isRightAcquired = right.tryLock();
                        if (isRightAcquired) {
                            try {
                                // right代码区
                                eat();
                            } finally {
                                right.unlock();
                            }
                        }
                    } finally {
                        left.unlock();
                    }
                }
            }
        }
    }

    static class Chopstick extends ReentrantLock {
        private String name;
        public Chopstick(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }
}

package concurrent.jmm_shared.basic.problem.variable_visibility;

import lombok.extern.slf4j.Slf4j;

/**
 * Java内存模型：
 * 主存 - 工作内存(注意不要把工作内存理解成线程的那些栈帧空间)
 *
 * 可见性问题：
 * 本示例体现的实际就是可见性，它保证的是在多个线程之间，一个
 * 线程对volatile变量的修改对另一个线程可见，不能保证原子性
 *
 * 注意synchronized语句块既可以保证代码块的原子性，也同时保
 * 证代码块内变量的可见性。但缺点是 synchronized 是属于重量
 * 级操作，性能相对更低。
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.problem.variable_visibility.Demo01_Volatile_And_Synchronized")
public class Demo01_Volatile_And_Synchronized {

    public static void main(String[] args) throws InterruptedException {
        // testVariableVisibilityProblem();
        // testSovleProblemByVolitale();
        testSovleProblemBySynchronized();
    }


    /**
     * 复现问题
     * 此处不能正常停止t线程
     */
    public static void testVariableVisibilityProblem() throws InterruptedException {
        class T extends Thread {
            boolean isTerminated = false;
            public void setTerminated(boolean terminated) {
                isTerminated = terminated;
            }
            @Override
            public void run() {
                while (!isTerminated) {}
            }
        }

        T t = new T();
        t.start();

        Thread.sleep(1000);
        t.setTerminated(true);
    }


    /**
     * 轻量级的解决方法：使用volatile关键字
     * 它可以用来修饰成员变量和静态成员变量，他可以避免线程从自己的工作缓存中查找
     * 变量的值，必须到主存中获取 它的值，线程操作 volatile 变量都是直接操作主存
     */
    public static void testSovleProblemByVolitale() throws InterruptedException {
        class T extends Thread {
            volatile boolean isTerminated = false;
            public void setTerminated(boolean terminated) {
                isTerminated = terminated;
            }
            @Override
            public void run() {
                for(;;) {
                    if (isTerminated) {
                        break;
                    }
                }
            }
        }

        T t = new T();
        t.start();

        Thread.sleep(1000);
        t.setTerminated(true);
    }


    /**
     * 相比较重量级的解决方法：使用synchronized关键字
     */
    public static void testSovleProblemBySynchronized() throws InterruptedException {
        class T extends Thread {
            boolean isTerminated = false;
            public void setTerminated(boolean terminated) {
                synchronized (this) {
                    isTerminated = terminated;
                }
            }
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        if (isTerminated) {
                            break;
                        }
                    }
                }
            }
        }

        T t = new T();
        t.start();

        Thread.sleep(1000);
        t.setTerminated(true);
    }
}

package concurrent.jmm_shared.basic.thread_method_state;

import lombok.extern.slf4j.Slf4j;

/**
 * 在线程t1中如何优雅地终止线程t2（这里的优雅是指给线程t2一个料理后事的机会）
 *
 * 错误的做法：
 * 1. 使用线程对象的stop方法（被废弃）：
 * stop方法会真正地杀死线程，如果这时候线程用锁占用了共享资源，那么他被杀死后就再也没有机会释放锁，其他线程也没有机会获取锁
 * 2. 使用System.exit(int) 方法停止锁：
 * 目的是停止一个线程，但这种做法会让终止程序都终止
 *
 * 正确的做法：
 * 两阶段终止模式
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_method_state.Demo06_TwoPhaseTermination")
public class Demo06_TwoPhaseTermination {

    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        monitor.start();

        Thread.sleep(5000);
        monitor.stop();
    }

    static class Monitor {
        private Thread t;

        // 启动监控
        public void start() {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (Thread.currentThread().isInterrupted()) {
                            log.debug("线程被打断，料理后事");
                            break;
                        }
                        try {
                            Thread.sleep(1000); // 被打断情况一
                            log.debug("打印监控日志");  // 被打断情况二
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt(); // 因为sleep出现异常后会清除打断标记，所以打断标记需要重置
                        }
                    }
                }
            }, "t1");
            t.start();
        }

        // 终止监控
        public void stop() {
            t.interrupt();
        }
    }
}


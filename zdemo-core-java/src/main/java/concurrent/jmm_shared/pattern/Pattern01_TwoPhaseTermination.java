package concurrent.jmm_shared.pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 两阶段终止模式：
 * 用在在线程t1中优雅地终止线程t2（这里的优雅是指给线程t2一个料理后事的机会）
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.pattern.Pattern01_TwoPhaseTermination")
public class Pattern01_TwoPhaseTermination {
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
            t = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        if (Thread.currentThread().isInterrupted()) {
                            log.debug("料理后事");
                            break;
                        }
                        try {
                            Thread.sleep(1000); // 在此被打断
                            log.debug("监控日志");     // 在此被打断
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt(); // 因为sleep出现异常后会清除打断标记，所以打断标记需要重置
                        }
                    }
                }
            };
            t.start();
        }

        // 终止监控
        public void stop() {
            t.interrupt();
        }
    }
}

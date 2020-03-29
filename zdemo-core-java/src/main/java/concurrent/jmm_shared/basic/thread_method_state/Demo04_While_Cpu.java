package concurrent.jmm_shared.basic.thread_method_state;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Kingly
 * @Date 2020/2/17
 * @Description
 *
 * yield 适用于多线程的环境中，效果比较明显；如果在单线程中，while-yield 对 cpu 的占用任然很高
 * sleep 方法相比较来说适用于单线程的环境中，如果在多线程环境使用sleep，就会造成无谓的空等
 *
 * 可以使用wait或者条件变量达到类似的效果，不同的是后两种都需要加锁，并且需要相应的唤醒操作，一般适用于需要同步的场景
 * sleep比较适合无需锁同步的场景
 *
 */
@Slf4j(topic = "c.concurrent.jmm_shared.basic.thread_method_state.Demo04_While_Cpu")
public class Demo04_While_Cpu {

    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    // try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace();}
                    // yield();
                }
            }
        };
        t1.start();
    }

}

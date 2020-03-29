package design.pattern.singleton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS式单例
 * CAS 即 compare and swap，是一种乐观的加锁策略
 * 核心思想就是 假设当前线程在访问资源的时候不会出现冲突，如果出现冲突的话就重试当前的操作，直到没有冲突为止
 *
 */
public class Demo06_Singleton_CAS {
    private static final AtomicReference<Demo06_Singleton_CAS> instance = new AtomicReference<>();
    private Demo06_Singleton_CAS() {}

    public static Demo06_Singleton_CAS getInstance() {
        for(;;) {
            Demo06_Singleton_CAS currentObj = instance.get();
            if (currentObj != null) {
                return currentObj;
            }

            currentObj = new Demo06_Singleton_CAS(); // 多线程下可能会产生垃圾对象，但是返回的对象总是一个
            if (instance.compareAndSet(null, currentObj)) { // 如果instance指向对象是null，则将它指向的对象替换为currentObj
                return currentObj;
            }
        }
    }
}

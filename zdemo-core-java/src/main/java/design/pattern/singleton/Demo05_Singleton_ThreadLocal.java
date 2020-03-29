package design.pattern.singleton;

/**
 * 本地线程变量
 * 单线程下可以获取同一个单例对象
 * 多线程下每个线程拥有各自的单例对象
 *
 */
public class Demo05_Singleton_ThreadLocal {
    private static Demo05_Singleton_ThreadLocal instance = null;
    private Demo05_Singleton_ThreadLocal() {}

    private static final ThreadLocal<Demo05_Singleton_ThreadLocal> TL = new ThreadLocal<Demo05_Singleton_ThreadLocal>() {
        @Override
        protected Demo05_Singleton_ThreadLocal initialValue() {
            return new Demo05_Singleton_ThreadLocal();
        }
    };

    public static Demo05_Singleton_ThreadLocal getInstance() {
        return TL.get();
    }
}

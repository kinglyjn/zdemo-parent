package design.pattern.singleton;

/**
 * 懒汉双检锁式
 *
 */
public class Demo04_Singleton_Lanhan {
    private static volatile Demo04_Singleton_Lanhan instance = null;
    private Demo04_Singleton_Lanhan() {}

    public static Demo04_Singleton_Lanhan getInstance() {
        if (instance==null) {
            synchronized (Demo04_Singleton_Lanhan.class) {
                if (instance==null) {
                    instance = new Demo04_Singleton_Lanhan();
                }
            }
        }
        return instance;
    }
}

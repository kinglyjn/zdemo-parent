package design.pattern.singleton;

import java.io.Serializable;

/**
 * 饿汉式
 *
 *
 */
public class Demo01_Singleton_Ehan implements Serializable {
    private static Demo01_Singleton_Ehan instance = new Demo01_Singleton_Ehan();
    private Demo01_Singleton_Ehan() {}

    public static Demo01_Singleton_Ehan getInstance() {
        return instance;
    }

    private Object readResole() { // 防止反序列化到的不是一个对象
        return instance;
    }

}

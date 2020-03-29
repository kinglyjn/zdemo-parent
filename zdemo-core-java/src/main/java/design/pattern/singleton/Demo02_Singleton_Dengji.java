package design.pattern.singleton;

import java.io.Serializable;

/**
 * 登记式（加强版的饿汉式）
 *
 *
 */
public class Demo02_Singleton_Dengji implements Serializable {
    private static class SingletonHolder {
        private static Demo02_Singleton_Dengji instance = new Demo02_Singleton_Dengji(); // 静态变量，类加载就会创建
    }
    private Demo02_Singleton_Dengji() {
        if (SingletonHolder.instance != null) {
            throw new IllegalThreadStateException("非法状态"); // 防止反射攻击
        }
    }

    public static Demo02_Singleton_Dengji getInstance() {
        return SingletonHolder.instance;
    }

    private Object readResole() { // 防止反序列化到的不是一个对象
        return SingletonHolder.instance;
    }
}

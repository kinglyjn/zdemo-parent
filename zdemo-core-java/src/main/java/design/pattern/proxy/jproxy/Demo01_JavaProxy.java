package design.pattern.proxy.jproxy;

import org.junit.Test;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 测试 Java接口的动态代理
 * 约束条件：代理类和被代理类必须公用一个接口
 *
 */
public class Demo01_JavaProxy {

    /**
     * 计算器功能接口
     *
     */
    static interface Caculator {
        int add(int i, int j);
        int minus(int i, int j);
        int multi(int i, int j);
        int div(int i, int j);
    }
    /**
     * 计算器功能实现类
     *
     */
    static class CaculatorImpl implements Caculator {
        @Override
        public int add(int i, int j) {
            return i+j;
        }
        @Override
        public int minus(int i, int j) {
            return i-j;
        }
        @Override
        public int multi(int i, int j) {
            return i*j;
        }
        @Override
        public int div(int i, int j) {
            return i/j;
        }
    }


    @Test
    public void test01() {
        Caculator caculator = (Caculator) Proxy.newProxyInstance(
                Demo01_JavaProxy.class.getClassLoader(),
                CaculatorImpl.class.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        // proxy: 正在返回的那个代理对象， 一般情况下invoke方法中都不使用该对象
                        // method: 正在被调用的方法
                        // args: 正在被调用方法的参数列表
                        Caculator target = new CaculatorImpl();
                        Object result = null;
                        try {
                            System.out.println("before（前置通知）: " + method.getName() + " with args " + Arrays.asList(args));
                            result = method.invoke(target, args);
                            System.out.println("after returning（返回通知）: " + method.getName() + " with result " + result);
                        } catch (Exception e) {
                            System.out.println("after throwing（异常通知）: " + method.getName() + " occurs exception " + e.toString());
                            throw e;
                        } finally {
                            System.out.println("after（后置通知）: " + method.getName() + " ends");
                        }
                        return result;
                    }
                });
        int div = caculator.div(1, 0);
        System.out.println(div);
    }
}

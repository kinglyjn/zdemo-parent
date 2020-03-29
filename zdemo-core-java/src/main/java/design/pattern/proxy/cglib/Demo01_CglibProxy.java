package design.pattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 测试cglib动态代理
 * 约束条件：基本没有限制
 *
 */
public class Demo01_CglibProxy {

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
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CaculatorImpl.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args,
                                    MethodProxy methodProxy) throws Throwable {
                // obj: 动态生成的代理对象
                // method: 实际调用的方法
                // method: 调用方法入参列表
                // proxy: method类的代理类，可以实现委托类对象的方法的调用，常用invokeSuper方法，在拦截方法内可以调用多次
                Object result = null;
                try {
                    System.out.println("before（前置通知）: " + method.getName() + " with args " + Arrays.asList(args));
                    result = methodProxy.invokeSuper(obj, args);
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
        Caculator caculator = (Caculator) enhancer.create();
        int result = caculator.div(1, 2);
        System.out.println(result);
    }
}

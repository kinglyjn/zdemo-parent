package test03.annotation_aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;


/**
 * Caculator的cglib动态代理类
 * 在spring的AOP中，就是使用到java的动态代理和CGLib动态代理
 * java的动态代理是必须基于接口的，而CGLib基于字节码创建代理类对象，不需要基于接口就可以直接创建代理类
 * @author zhangqingli
 *
 */
public class CaculatorCglibProxy {
	
	 /*
	 * main
	 * 
	 */
	public static void main(String[] args) {
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
		caculator.div(1, 2);
	}
}

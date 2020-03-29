package test03.annotation_aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Caculator的java动态代理类
 * @author zhangqingli
 *
 */
public class CaculatorJavaProxy {
	
	 /*
	 * 测试
	 * 
	 */
	public static void main(String[] args) {
		Caculator caculator = (Caculator) Proxy.newProxyInstance(
				CaculatorJavaProxy.class.getClassLoader(), 
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
		int result = caculator.div(1, 2);
		System.out.println(result);
	}
}

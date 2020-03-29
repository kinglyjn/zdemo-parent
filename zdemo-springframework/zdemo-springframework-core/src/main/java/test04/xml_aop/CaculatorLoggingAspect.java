package test04.xml_aop;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Caculator日志切面
 * @author zhangqingli
 *
 */
public class CaculatorLoggingAspect {
	
	/**
	 * 前置通知（在方法执行之前执行）
	 * 
	 */
	public void befreMethod(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName(); //方法名
		Object[] args = joinPoint.getArgs(); 					//方法参数列表
		System.out.println("日志切面#前置通知: 方法名称 " + methodName + "，方法参数 " + Arrays.asList(args));
	}
	
	/**
	 * 返回通知（在方法正常结束后执行，返回通知可以访问到方法的返回值）
	 * 
	 */
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("日志切面#返回通知: 方法名称 " + methodName + "，返回结果 " + result);
	}
	
	/**
	 * 后置通知（在目标方法执行之后无论是否异常都执行，相当于finally代码块）
	 * 
	 */
	public void afterMethod(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("日志切面#后置通知: 方法名称 " + methodName);
	}
	
	/**
	 * 异常通知（在目标方法出现异常时才执行，可以访问到异常对象，并且可以指定在出现特定异常才执行异常通知）
	 * 
	 */
	public void afterThrowing(JoinPoint joinPoint, Exception ex) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("日志切面#异常通知: 方法名称 " + methodName + "，抛出异常 " + ex);
	}
	
	
	/**
	 * 环绕通知（相当于动态代理的全过程，包含了前置通知、后置通知、返回通知和异常通知，并不常用）
	 * 
	 */
	public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		String methodName = joinPoint.getSignature().getName(); //方法名
		Object[] args = joinPoint.getArgs(); //方法参数列表
		
		try {
			//前置通知
			System.out.println("before: " + methodName + " with args " + Arrays.asList(args));
			
			result = joinPoint.proceed(); //方法执行
			
			//返回通知
			System.out.println("after retrurn: " + methodName + " with result " + result);
		} catch (Throwable e) {
			//异常通知
			System.out.println("after throwing: " + methodName + " occurs exception " + e);
			throw e;
		} finally {
			//后置通知
			System.out.println("after: " + methodName);
		}
		return result;
	}
}

package test04.xml_aop;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;

/**
 * Caculator参数验证切面
 * @author zhangqingli
 *
 */
public class CaculatorValidateAspect {
	
	public void beforeMethod(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName(); //方法名
		Object[] args = joinPoint.getArgs(); 					//方法参数列表
		System.out.println("参数验证切面#前置通知: 方法名称 " + methodName + "，方法参数 " + Arrays.asList(args));
	}
}

package test03.annotation_aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Caculator参数验证切面
 * @author zhangqingli
 *
 */
@Component
@Aspect
@Order(1)
public class CaculatorValidateAspect {
	
	@Before("execution(* test03.annotation_aop.CaculatorImpl.*(..))")
	public void beforeMethod(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName(); //方法名
		Object[] args = joinPoint.getArgs(); 					//方法参数列表
		System.out.println("参数验证切面#前置通知: 方法名称 " + methodName + "，方法参数 " + Arrays.asList(args));
	}
}

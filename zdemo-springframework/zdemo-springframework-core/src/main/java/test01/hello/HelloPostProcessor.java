package test01.hello;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * IOC容器bean初始化处理器
 * 
 * 一个bean的生命周期：
 * constructor-->set方法赋值-->beforeInit、init、afterInit-->destory
 * 
 */
public class HelloPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) 
			throws BeansException { //init方法之前被调用
		if ("hello2".equals(beanName)) {
			System.err.println("[HelloPostProcessor.postProcessBeforeInitialization] " + bean.hashCode() + " " + beanName);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException { //init方法之后被调用
		
		if ("hello2".equals(beanName)) {
			System.err.println("[HelloPostProcessor.postProcessAfterInitialization] " + bean.hashCode() + " " + beanName);
		}
		return bean;
	}

}

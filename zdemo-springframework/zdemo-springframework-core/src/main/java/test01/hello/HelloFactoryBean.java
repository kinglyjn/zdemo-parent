package test01.hello;

import org.springframework.beans.factory.FactoryBean;

/**
 * 通过Spring自带的FactoryBean生成hello实例
 * @author zhangqingli
 *
 */
public class HelloFactoryBean implements FactoryBean<Hello>{
	private Integer num;
	private String str;
	
	//setter
	public void setNum(Integer num) {
		this.num = num;
	}
	public void setStr(String str) {
		this.str = str;
	}

	/**
	 * 返回的实例
	 */
	@Override
	public Hello getObject() throws Exception {
		return new Hello(num, str);
	}

	/**
	 * 返回的实例的类型
	 */
	@Override
	public Class<?> getObjectType() {
		return Hello.class;
	}

	/**
	 * 是否单例
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

}

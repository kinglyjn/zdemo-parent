package test01.hello;

/**
 * Hello实例工厂方法（需要生成工厂实例就才能创建Hello对象）
 * @author zhangqingli
 *
 */
public class HelloFactory {
	public Hello generateHello(Integer num) {
		return new Hello(num, "实例工厂方法生成hello实例");
	}
}

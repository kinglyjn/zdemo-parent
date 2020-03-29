package test01.hello;

/**
 * Hello静态工厂方法（不需要生成工厂实例就可以创建Hello对象）
 * @author zhangqingli
 *
 */
public class HelloStaticFactory {
	
	public static Hello getInstance(Integer num) {
		return new Hello(num, "静态工厂方法生成hello实例");
	}
}

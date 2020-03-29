package test01.hello;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello测试类
 * @author zhangqingli
 *
 */
public class HelloTest {
	private ClassPathXmlApplicationContext ctx;
	
	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("test01/hello/spring01-config.xml");
	}
	
	@After
	public void after() {
		ctx.close();
	}
	
	public static void main(String[] args) {
		//Hello hello = (Hello) ctx.getBean("hello1");
		//Hello hello = (Hello) ctx.getBean("hello2");
		//Hello hello = (Hello) ctx.getBean("hello3");
		//Hello hello = (Hello) ctx.getBean("hello4");
	}
	
	@Test
	public void test01() {
		Hello hello1 = (Hello) ctx.getBean("hello1");
		System.out.println("hello1 hashcode: " + hello1.hashCode());
	}
	
	@Test
	public void test02() {
		Hello hello2 = (Hello) ctx.getBean("hello2");
		System.out.println("hello2 hashcode: " + hello2.hashCode());
	}
	
	@Test
	public void test03() {
		Hello hello3 = (Hello) ctx.getBean("hello3");
		System.out.println("hello3 hashcode: " + hello3.hashCode());
	}
	
	@Test
	public void test04() {
		Hello hello4 = (Hello) ctx.getBean("hello4");
		System.out.println("hello4 hashcode: " + hello4.hashCode());
	}
	
	@Test
	public void test05() {
		Hello hello5 = (Hello) ctx.getBean("hello5");
		System.out.println("hello5 hashcode: " + hello5.hashCode());
	}
}


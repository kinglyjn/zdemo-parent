package mybatis00.test08.spring_integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
	"classpath:/test08/spring_integration/applicationContext.xml"
})
public class UserTest {
	
	@Autowired
	private UserMapper userMapper; // 注意线程安全性(通过测试发现，不同线程注入的是同一个mapper对象)
	
	
	/**
	 * 原始开发方式
	 * 
	 */
	@Test
	@SuppressWarnings("resource")
	public void test01() {
		UserDao userDao = (UserDao) new ClassPathXmlApplicationContext("/test08/spring_integration/applicationContext.xml").getBean("userDao");
		User user = userDao.findUserById(1);
		System.err.println(user);
	}
	
	
	/**
	 * Mapper代理开发方式
	 * @throws InterruptedException 
	 * 
	 */
	@Test
	public void test02() throws InterruptedException {
		User user = userMapper.findUserById(1);
		System.err.println(user);
	}
	
	@Test
	public void test03() throws InterruptedException {
		User user = userMapper.findUserById(1);
		user.setAge(user.getAge()+1);
		userMapper.updateUser(user);
	}
	
}

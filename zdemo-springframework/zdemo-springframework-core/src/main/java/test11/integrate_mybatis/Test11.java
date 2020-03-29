package test11.integrate_mybatis;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试类
 * @author zhangqingli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/test11/integrate_mybatis/spring11-config.xml"
})
public class Test11 {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserMapper userMapper; // 注意线程安全性(通过测试发现，不同线程注入的是同一个mapper对象)
	
	
	@Test
	public void testInit() throws SQLException {
		System.out.println(dataSource.getConnection());
	}
	
	
	/**
	 * 原始开发方式
	 * 
	 */
	@Test
	@SuppressWarnings("resource")
	public void test01() {
		UserDao userDao = (UserDao) new ClassPathXmlApplicationContext("/test11/integrate_mybatis/spring11-config.xml").getBean("userDao");
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

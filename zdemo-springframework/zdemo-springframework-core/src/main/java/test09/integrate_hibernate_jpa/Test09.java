package test09.integrate_hibernate_jpa;

import java.sql.SQLException;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * spring整合jpa测试
 * 
 * spring整合jpa的两个目标：
 * 1. 使用spring的ioc容器管理jpa entityManager
 * 2. 使jpa使用spring的声明式事务管理
 * 
 * 要求整合之后entityManger的获取方式和使用方式：
 * 1. 使用 类属性注解 @PersistenceContext 来获取 entityManager
 * 2. 使用 entityManager 必须要在spring事务（ @Transactional ）管理之下，
 *    否则将抛出javax.persistence.TransactionRequiredException异常
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/test09/integrate_hibernate_jpa/spring09-config.xml"
})
public class Test09 {
	
	@Autowired
	private PersonService personService;
	
	@Test
	public void testInit() {
	}

	@Test
	public void testPersonServiceWithTx() throws SQLException {
		Person p1 = new Person("张三", 22, Gender.MALE, new Date());
		Person p2 = new Person("小娟", 21, Gender.FEMALE, new Date());
		personService.saveTwoPerson(p1, p2);
	}
}






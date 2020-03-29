package test10.integrate_hibernate_springdata_jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * UserTest
 * @author zhangqingli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/test10/integrate_hibernate_springdata_jpa/spring10-config.xml"
})
public class UserTest {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	
	
	@Test
	public void testInit() throws SQLException {
		System.out.println(dataSource.getConnection());
		Class<? extends EntityManager> entityManagerInterface = entityManagerFactory.getEntityManagerInterface();
		System.out.println(entityManagerInterface); //HibernateEntityManager
	}
	
	@Test
	public void testUserRepository() {
		User user = userRepository.findById(1).get();
		System.out.println(user);
	}
	
	@Test
	public void testBatchSave() {
		List<User> users = new ArrayList<User>();
		users.add(new User("张三", 23, new Date()));
		users.add(new User("李四", 22, new Date()));
		users.add(new User("小娟", 21, new Date()));
		userService.batchSave(users);
	}
}

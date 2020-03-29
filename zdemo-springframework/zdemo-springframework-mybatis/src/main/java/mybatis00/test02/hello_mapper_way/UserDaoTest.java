package mybatis00.test02.hello_mapper_way;

import java.io.IOException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 用户dao测试类
 * @author zhangqingli
 *
 */
public class UserDaoTest {
	private SqlSession session;
	
	@Before
	public void before() throws IOException {
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
			.build(Resources.getResourceAsStream("test02/hello_mapper_way/mybatis02-config.xml"));
		session = sessionFactory.openSession();
	}
	
	@After
	public void after() {
		session.commit();
		session.close();
	}
	
	
	@Test
	public void test01() {
		UserMapper mapper = session.getMapper(UserMapper.class);
		User user = mapper.findUserById(1);
		System.out.println(user);
	}
	
}

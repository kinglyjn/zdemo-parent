package mybatis00.test07.first_second_cache;

import java.io.IOException;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserDaoTest {

	private SqlSession session;
	
	@Before
	public void before() throws IOException {
		session = new SqlSessionFactoryBuilder()
		.build(Resources.getResourceAsStream("test07/first_second_cache/mybatis07-config.xml"))
		.openSession();
	}
	
	@After
	public void after() {
		session.commit();
		session.close();
	}
	
	
	/**
	 * mybatis二级缓存对细粒度数据级别的缓存实现不好，比如如下要求：对商品信息进行缓存，由于商品信息
	 * 查询访问量大，但是要求用户每次都能查询出最新的商品数据，此时如果在使用mybatis的二级缓存就无法
	 * 实现当一个商品变化时只刷新该商品的缓存信息而不刷新其他商品的信息，因为mybatis的二级缓存区域以
	 * mapper为单位划分，当一个商品变化会将所有商品的缓存数据清空。解决此类问题需要在业务层根据需求对
	 * 数据有针对性地进行缓存。
	 * 
	 */
	@Test
	public void test01() {
		UserMapper mapper = session.getMapper(UserMapper.class);
		User user1 = mapper.findUserById(1); // 耗时1500ms
		System.err.println(user1); 			// Cache Hit Ratio [test07.first_second_cache.UserMapper]: 0.0
		
		// 查出的数据会先放在一级缓存中，
		//只有当会话提交或关闭之后，
		//一级缓存中的数据才会转移到二级缓存中
		session.commit();
		//user1.setAge(24);
		//mapper.updateUser(user1);
		
		User user2 = mapper.findUserById(1); // 耗时0ms
		System.err.println(user2); 			// Cache Hit Ratio [test07.first_second_cache.UserMapper]: 0.5
		System.err.println(user1==user2); 	// true
	}
	
	@Test
	public void test02() {
		UserMapper mapper = session.getMapper(UserMapper.class);
		User user = new User("zhangsan1", 23, new Date());
		user.setId(1);
		mapper.updateUser(user);
	}
}

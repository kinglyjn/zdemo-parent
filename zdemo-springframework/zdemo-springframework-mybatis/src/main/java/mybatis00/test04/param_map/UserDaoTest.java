package mybatis00.test04.param_map;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserDaoTest {
	private SqlSession session;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Before
	public void before() throws IOException {
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
			.build(Resources.getResourceAsStream("test04/param_map/mybatis04-config.xml"));
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
	
	@Test
	public void test02() {
		UserMapper mapper = session.getMapper(UserMapper.class);
		List<User> users = mapper.findUserByNameLikeAndAgeBetween("%i%", 18, 26);
		System.out.println(users);
	}
	
	@Test
	public void test03() throws ParseException {
		UserMapper mapper = session.getMapper(UserMapper.class);
		List<User> users = mapper.findUserByNameLikeAndBirthdayBetween("%i%", sdf.parse("1990-01-01"), sdf.parse("1995-01-01"));
		System.out.println(users);
	}
	
	@Test
	public void test04() {
		UserMapper mapper = session.getMapper(UserMapper.class);
		User user = new User("xiaojuan", 23, new Date());
		user.setId(3);
		mapper.updateUser(user);
	}
	
	@Test
	public void test05() {
		UserMapper mapper = session.getMapper(UserMapper.class);
		Map<String, Object> userMap = new HashMap<String,Object>();
		userMap.put("name", "zhangsan");
		userMap.put("age", 23);
		List<User> users = mapper.findUserByMap1(userMap);
		System.out.println(users);
	}
	
	
	@Test
	public void test06() {
		UserMapper mapper = session.getMapper(UserMapper.class);
		Set<Integer> ids = new HashSet<>(Arrays.asList(1,2,3));
		List<User> users = mapper.findUserByIdSet(ids);
		System.out.println(users);
	}
	
	@Test
	public void test07() {
		UserMapper mapper = session.getMapper(UserMapper.class);
		List<Integer> ids = Arrays.asList(1,2,3);
		List<User> users = mapper.findUserByIdListFirst(ids);
		System.out.println(users);
	}
	
	@Test
	public void test08() {
		UserMapper mapper = session.getMapper(UserMapper.class);
		Integer[] ids = {1,2,3};
		List<User> users = mapper.findUserByIdArrayFirst(ids);
		System.out.println(users);
	}
	
	@Test
	public void test09() {
		UserMapper mapper = session.getMapper(UserMapper.class);
		User user = new User();
		user.setName("zhangsan");
		List<User> users = mapper.findUserByIdAndUserVo(1, user);
		System.out.println(users);
	}
	
	@Test
	public void test10() {
		UserMapper mapper = session.getMapper(UserMapper.class);
		User user = new User();
		user.setName("zhangsan");
		List<User> users = mapper.findUserByParameterAndDatabaseId(user);
		System.out.println(users);
	}
	
	
}

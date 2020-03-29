package mybatis00.test01.hello_origin_way;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户dao测试类
 * @author zhangqingli
 *
 */
public class UserDaoTest {
	private SqlSession session;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Before
	public void before() throws IOException {
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
			.build(Resources.getResourceAsStream("mybatis00/test01/hello_origin_way/mybatis01-config.xml"));
		session = sessionFactory.openSession();
	}
	
	@After
	public void after() {
		session.commit();
		session.close();
	}
	
	
	
	/**
	 * 测试查询
	 * statement: 就是mapper映射文件中select#id，其值为【namespace. + select#id】
	 * parameter: 指定映射文件中匹配的parameterType类型的参数
	 * 返回结果: 为映射文件中resultType类型的返回结果
	 * 
	 */
	@Test
	public void test01() {
		User user = session.selectOne("mybatis00/test01.hello_origin_way.UserMapper.findUserById", 1);
		System.out.println(user);
	}
	
	@Test
	public void test02() {
		List<User> users = session.selectList("mybatis00/test01.hello_origin_way.UserMapper.findUserByNameLike", "i");
		System.out.println(users);
	}
	
	@Test
	public void test03() {
		Integer count = session.selectOne("countUser");
		System.out.println(count);
	}
	
	
	/**
	 * 测试插入
	 * 
	 */
	@Test
	public void test04() throws ParseException {
		User user1 = new User("wangwu", 30, sdf.parse("1987-09-09 12:12:12"));
		session.insert("insertUser1", user1);
		System.out.println(user1);
		
		User user2 = new User("zhaoliu", 30, sdf.parse("1987-09-09 12:12:12"));
		session.insert("insertUser1", user2);
		System.out.println(user2);
	}
	
	
	/**
	 * 测试更新 
	 * 
	 */
	@Test
	public void test05() {
		User user = new User("小娟娟", 23, new Date());
		user.setId(3);
		session.update("updateUser", user);
	}
	
	
	/**
	 * 测试删除
	 * 
	 */
	@Test
	public void testDelete() {
		int result = session.delete("deleteUser", 7);
		System.out.println(result);
	}
}

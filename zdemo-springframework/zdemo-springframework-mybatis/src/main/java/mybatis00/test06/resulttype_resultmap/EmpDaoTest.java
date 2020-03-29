package mybatis00.test06.resulttype_resultmap;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class EmpDaoTest {
	private SqlSession session;
	
	@Before
	public void before() throws IOException {
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
			.build(Resources.getResourceAsStream("test06/resulttype_resultmap/mybatis06-config.xml"));
		session = sessionFactory.openSession();
	}
	
	@After
	public void after() {
		session.commit();
		session.close();
	}
	
	
	/**
	 * 单表查询
	 * 
	 */
	@Test
	public void testFindEmpsByDeptId() {
		EmpMapper mapper = session.getMapper(EmpMapper.class);
		List<Emp> emps = mapper.findEmpsByDeptId(1);
		System.out.println(emps);
	}
	
	@Test
	public void test01() {
		EmpMapper mapper = session.getMapper(EmpMapper.class);
		List<Emp> emps = mapper.findEmpByLastnameLike("%i%");
		System.out.println(emps);
	}
	
	@Test
	public void test02() {
		EmpMapper mapper = session.getMapper(EmpMapper.class);
		Map<String, Object> map = mapper.findEmpByIdWithReturnedMap(1);
		System.out.println(map);
	}
	
	@Test
	public void test03() {
		EmpMapper mapper = session.getMapper(EmpMapper.class);
		Map<Integer, Emp> resultMap = mapper.findEmpByIdWithReturnedMap2();
		System.out.println(resultMap);
	}
	
	
	/**
	 * 多表查询
	 * 
	 */
	@Test
	public void test04() {
		EmpMapper mapper = session.getMapper(EmpMapper.class);
		Emp emp = mapper.findEmpWithDeptByEmpId(1);
		System.out.println(emp);
	}
	
	@Test
	public void test05() {
		EmpMapper mapper = session.getMapper(EmpMapper.class);
		List<Emp> emps = mapper.findAllEmpWithDept();
		for (Emp emp : emps) {
			System.out.println(emp);
		}
	}
	
	
	/**
	 * 分步查询和懒加载
	 * 
	 */
	@Test
	public void test06() {
		EmpMapper mapper = session.getMapper(EmpMapper.class);
		Emp emp = mapper.findEmpByIdReturnedEmpWithDeptStepMode(1);
		System.err.println(emp.getLastName());
		System.err.println(emp.getDept());
		
	}
	
	
	
	
}

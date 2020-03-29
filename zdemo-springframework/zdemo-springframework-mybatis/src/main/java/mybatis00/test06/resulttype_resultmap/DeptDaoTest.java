package mybatis00.test06.resulttype_resultmap;

import java.io.IOException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DeptDaoTest {
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
	
	
	@Test
	public void test01() {
		DeptMapper mapper = session.getMapper(DeptMapper.class);
		Dept dept = mapper.findDeptById(1);
		System.out.println(dept);
	}
	
	
	@Test
	public void test02() {
		DeptMapper mapper = session.getMapper(DeptMapper.class);
		Dept dept = mapper.findDeptByIdReturnedDeptWithEmpStepMode(1);
		System.err.println(dept.getDeptName());
		System.err.println(dept.getEmps());
	}
	
}

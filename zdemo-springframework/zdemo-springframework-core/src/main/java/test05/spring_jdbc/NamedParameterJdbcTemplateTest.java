package test05.spring_jdbc;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * NamedParameterJdbcTemplate测试类
 * @author zhangqingli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/test05/spring_jdbc/spring05-config.xml"
})
public class NamedParameterJdbcTemplateTest {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	/**
	 * 测试插入数据
	 * 
	 */
	@Test
	public void testInsert() {
		String sql = "insert into t_emp(name, salary, dept_id) values(:name, :salary, :deptid)";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", "老111");
		paramMap.put("salary", 111);
		paramMap.put("deptid", 2);
		
		int result = jdbcTemplate.update(sql, paramMap);
		System.out.println(result);
	}
	
	/**
	 * sql语句中的参数名必须和类的属性名一致
	 * 
	 */
	@Test
	public void testInsert2() {
		String sql = "insert into t_emp(name, salary) values(:name, :salary)";
		
		Emp emp = new Emp("老120", 12000);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(emp);
		jdbcTemplate.update(sql, paramSource);
	}
	
}

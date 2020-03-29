package test05.spring_jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JdbcTemplate测试类
 * @author zhangqingli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/test05/spring_jdbc/spring05-config.xml"
})
public class JdbcTemplateTest {
	
	@Autowired
	private ComboPooledDataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private EmpService empService;
	
	
	@Test
	public void initTest() throws SQLException {
		System.out.println(dataSource.getUser());
		System.out.println(dataSource.getPassword());
		System.out.println(dataSource.getJdbcUrl());
		System.out.println(dataSource.getDriverClass());
		Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
		System.out.println(conn);
	}
	
	
	/**
	 * 测试批插入
	 * jdbcTemplate默认使用jdbc原生的事务策略，即autoCommited=true，程序发生异常默认不会回滚事务
	 * 
	 */
	@Test
	public void testBatchInsert() {
		String sql = "insert into t_emp(name, salary, dept_id) values(?, ?, ?)";
		
		List<Object[]> batchArgs = new ArrayList<>();
		batchArgs.add(new Object[]{"老11", 11000, 1});
		batchArgs.add(new Object[]{"老12", 12000, 2});
		batchArgs.add(new Object[]{"老13", 13000, 2});
		
		int[] results = jdbcTemplate.batchUpdate(sql, batchArgs);
		System.out.println(Arrays.asList(results));
	}
	
	/**
	 * 从数据库中查询一条记录，实际得到对应的对象
	 * 注意：使用sql列中的别名完成 列名和属性名的映射
	 * 不支持级联属性，jdbcTemplate说到底就是一个jdbc小工具，而不是orm框架
	 *
	 */
	@Test
	public void testQueryForObject() {
		String sql = "select id, name, salary, dept_id as \"dept.id\" from t_emp where id=?";
		
		BeanPropertyRowMapper<Emp> rowMapper = new BeanPropertyRowMapper<>(Emp.class); //指定如何映射结果集
		Emp emp = jdbcTemplate.queryForObject(sql, rowMapper, 1);
		
		System.out.println(emp); 			// Emp [id=1, name=zhangsan, salary=30000.0, dept=null]
		System.out.println(emp.getDept()); 	// null
	}
	
	/**
	 * 获取单个列的值，或者做统计查询
	 * 
	 */
	@Test
	public void testQueryForObject2() {
		String sql = "select count(id) from t_emp";
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		System.out.println(count);
	}
	
	
	/**
	 * 查询一实体类的集合
	 * 
	 */
	@Test
	public void test() {
		String sql = "select id, name, salary from t_emp where id>?";
		//
		RowMapper<Emp> rowMapper = new BeanPropertyRowMapper<Emp>(Emp.class);
		List<Emp> emps = jdbcTemplate.query(sql, rowMapper, 3);
		System.out.println(emps);
	}
	
	/**
	 * 测试更新 
	 * 
	 */
	@Test
	public void testUpdate() {
		String sql = "update t_emp set salary=? where id=?";
		int result = jdbcTemplate.update(sql, 1000, 4);
		System.out.println(result);
	}
	
	/**
	 * 测试插入时的事务
	 * 事务可以起作用！
	 * 
	 */
	@Test
	public void testBatchSave() {
		empService.batchSave();
	}
}

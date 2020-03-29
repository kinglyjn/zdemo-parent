package test05.spring_jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EmpService
 * @author zhangqingli
 *
 */
@Service
public class EmpService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 测试jdbcTemplate执行时的事务
	 * 
	 */
	@Transactional
	public void batchSave() {
		String sql = "insert into t_emp(name, salary, dept_id) values(?, ?, ?)";
		
		List<Object[]> batchArgs = new ArrayList<>();
		batchArgs.add(new Object[]{"老21", 21000, 1});
		batchArgs.add(new Object[]{"老22", 22000, 2});
		batchArgs.add(new Object[]{"老23", 23000, 2});
		
		int[] results = jdbcTemplate.batchUpdate(sql, batchArgs);
		System.out.println("执行结果为：" + Arrays.asList(results));
	}
}

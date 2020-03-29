package test10.integrate_hibernate_springdata_jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UserService Impl
 * @author zhangqingli
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 测试批量插入的事务
	 * @param users
	 */
	//@Transactional(readOnly=true) //Caused by: java.sql.SQLException: Connection is read-only. Queries leading to data modification are not allowed
	@Transactional
	public void batchSave(List<User> users) {
		userRepository.saveAll(users);
	}
}

package test09.integrate_hibernate_jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * PersonService
 * @author zhangqingli
 *
 */
@Service("personService")
public class PersonService {

	@Autowired
	private PersonDao personDao;
	
	
	/**
	 * 插入两个人
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveTwoPerson(Person p1, Person p2) {
		personDao.insertPerson(p1);
		
		//故意抛出异常以测试spring声明式事务
		//Integer.parseInt("aaa");
			
		personDao.insertPerson(p2);
	}
}

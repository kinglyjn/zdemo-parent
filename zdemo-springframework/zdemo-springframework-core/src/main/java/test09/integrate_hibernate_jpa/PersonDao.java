package test09.integrate_hibernate_jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * PersonDao
 * @author zhangqingli
 *
 */
@Repository("personDao")
public class PersonDao {
	
	@PersistenceContext //获取和当前事务和持久上下文关联的entityManager
	private EntityManager entityManager;
	
	
	/**
	 * 插入
	 */
	public void insertPerson(Person p) {
		entityManager.persist(p);
	}
}

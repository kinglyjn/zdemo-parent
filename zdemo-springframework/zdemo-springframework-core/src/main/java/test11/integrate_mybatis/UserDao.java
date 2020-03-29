package test11.integrate_mybatis;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * UserDao
 * @author zhangqingli
 *
 */
public class UserDao extends SqlSessionDaoSupport {
	public User findUserById(Integer id) {
		SqlSession session = this.getSqlSession();
		User user = session.selectOne("test11.integrate_mybatis.UserMapper.findUserById", 1);
		return user;
	}
}


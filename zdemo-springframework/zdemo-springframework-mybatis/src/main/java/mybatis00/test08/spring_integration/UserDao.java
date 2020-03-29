package mybatis00.test08.spring_integration;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class UserDao extends SqlSessionDaoSupport {
	public User findUserById(Integer id) {
		SqlSession session = this.getSqlSession();
		User user = session.selectOne("test08.spring_integration.UserMapper.findUserById", 1);
		return user;
	}
}

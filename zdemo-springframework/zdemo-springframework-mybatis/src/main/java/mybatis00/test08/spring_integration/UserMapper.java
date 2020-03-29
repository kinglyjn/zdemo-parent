package mybatis00.test08.spring_integration;

public interface UserMapper {
	User findUserById(Integer id);
	void updateUser(User user);
}

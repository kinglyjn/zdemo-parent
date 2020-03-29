package mybatis00.test07.first_second_cache;

public interface UserMapper {
	User findUserById(Integer id);
	void updateUser(User user);
}

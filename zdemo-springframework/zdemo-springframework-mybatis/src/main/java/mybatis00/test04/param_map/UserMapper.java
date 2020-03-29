package mybatis00.test04.param_map;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
	User findUserById(Integer id);
	List<User> findUserByNameLikeAndAgeBetween(String name, Integer minAge, Integer maxAge);
	List<User> findUserByNameLikeAndBirthdayBetween(@Param("name") String name, @Param("minDate") Date minDate, @Param("maxDate") Date maxDate);
	void updateUser(User user);
	List<User> findUserByMap1(Map<String, Object> map);
	List<User> findUserByIdSet(Set<Integer> ids);
	List<User> findUserByIdListFirst(List<Integer> ids);
	List<User> findUserByIdArrayFirst(Integer[] ids);
	List<User> findUserByIdAndUserVo(Integer id, @Param("user") User user);
	List<User> findUserByParameterAndDatabaseId(User user);
}

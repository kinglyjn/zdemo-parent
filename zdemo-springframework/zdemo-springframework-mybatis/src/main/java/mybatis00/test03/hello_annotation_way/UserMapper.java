package mybatis00.test03.hello_annotation_way;

import org.apache.ibatis.annotations.Select;

/**
 * UserMapper
 * @author zhangqingli
 *
 */
public interface UserMapper {
	@Select("select * from t_user where id=#{id}")
	User findUserById(Integer id);
}

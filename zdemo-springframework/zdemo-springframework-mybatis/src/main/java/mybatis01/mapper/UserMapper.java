package mybatis01.mapper;

import mybatis01.module.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @Author KJ
 * @Date 2020-03-23 1:52 AM
 * @Description
 */
public interface UserMapper {
    List<User> listAll();
    int add(User user);
    int delete(@Param("id") int id);
}

package mybatis01.mapper;

import mybatis01.module.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * @Author KJ
 * @Date 2020-03-23 2:02 AM
 * @Description
 */
public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper {
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<User> listAll() {
        UserMapper mapper = getSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.listAll();
    }

    @Override
    public int add(User user) {
        UserMapper mapper = getSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.add(user);
    }

    @Override
    public int delete(int id) {
        UserMapper mapper = getSqlSessionTemplate().getMapper(UserMapper.class);
        return mapper.delete(id);
    }

}

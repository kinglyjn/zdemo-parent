package mybatis01.service;

import mybatis01.mapper.UserMapper;
import mybatis01.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author KJ
 * @Date 2020-03-23 4:10 AM
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override
    public void b1() {
        User user = new User("小娟2", "123");
        userMapper.add(user);
        userMapper.delete(user.getId());
    }
}

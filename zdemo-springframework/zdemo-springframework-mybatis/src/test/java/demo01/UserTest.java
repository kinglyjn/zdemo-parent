package demo01;

import com.keyllo.demo.mybatis.demo01.config.MyConfig;
import com.keyllo.demo.mybatis.demo01.mapper.UserMapper;
import com.keyllo.demo.mybatis.demo01.module.User;
import com.keyllo.demo.mybatis.demo01.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author KJ
 * @Date 2020-03-23 2:00 AM
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        MyConfig.class
})
public class UserTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Test
    public void test01() {
        List<User> users = userMapper.listAll();
        System.out.println(users);
    }

    @Test
    public void test02() {
        userService.b1();
    }
}

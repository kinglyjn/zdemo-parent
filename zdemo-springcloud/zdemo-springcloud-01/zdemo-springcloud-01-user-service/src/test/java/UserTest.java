import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springcloud01.usercenter.UserApplication;
import springcloud01.usercenter.module.User;
import springcloud01.usercenter.service.UserService;

import java.util.Date;

/**
 * @Author KJ
 * @Date 2020-04-02 12:19 AM
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApplication.class})
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void test01() {
        User user = User.builder()
                .username("zhangsan")
                .password("123456")
                .name("张三")
                .age(27)
                .gender(1)
                .birthday(new Date())
                .remark("这是张三的信息")
                .build();
        userService.save(user);
    }

}

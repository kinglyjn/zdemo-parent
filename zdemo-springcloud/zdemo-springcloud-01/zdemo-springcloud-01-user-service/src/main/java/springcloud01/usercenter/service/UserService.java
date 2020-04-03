package springcloud01.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import springcloud01.usercenter.module.User;

/**
 * @Author KJ
 * @Date 2020-04-01 3:45 PM
 * @Description
 */
public interface UserService extends IService<User> {

    User getUserById(Long id);
}

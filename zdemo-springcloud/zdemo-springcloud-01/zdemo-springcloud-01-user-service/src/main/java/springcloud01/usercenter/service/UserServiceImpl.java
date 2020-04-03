package springcloud01.usercenter.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import springcloud01.usercenter.datasource.DataSource;
import springcloud01.usercenter.datasource.DataSourceEnum;
import springcloud01.usercenter.mapper.UserMapper;
import springcloud01.usercenter.module.User;

/**
 * @Author KJ
 * @Date 2020-04-01 3:47 PM
 * @Description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 这里我的idea装了mybatis-plus插件，如果没有装，会出现红色警告。
     *
     * 解决方案有很多种：
     * 1. @Resource
     * 2. @Autowired(required = false)
     * 3. 安装mybatis-plus插件
     * 4. 在UserMapper接口上加上@Repository注解（其实没必要，因为在Application中加了扫描 @MapperScan）
     * 5. 使用lombok @RequiredArgsConstructor(onConstructor = @_(@Autowired))，这时候在聚合对象上就不需要加 @Autowired 注解了
     *
     */
    //@Autowired
    //private UserMapper userMapper;

    @DataSource(value = DataSourceEnum.DB2)
    @Override
    public User getUserById(Long id) {
        return this.getById(id);
    }
}

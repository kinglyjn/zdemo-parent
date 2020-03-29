package test11.integrate_mybatis;

/**
 * UserMapper
 * @author zhangqingli
 *
 */
//@Repository("userMapper") //如果需要给UserMapper设置名称，可以加上这个注解
//@MapperScan
//注意四点对应 1.namespace 2.statement#id  3.parameterType  4.resultType&resultMap
public interface UserMapper {
	User findUserById(Integer id);
	void updateUser(User user);
}

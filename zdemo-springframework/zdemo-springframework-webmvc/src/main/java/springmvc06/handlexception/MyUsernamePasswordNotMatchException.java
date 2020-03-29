package springmvc06.handlexception;

/**
 * 自定义异常
 * @author zhangqingli
 * @creation 2017年4月15日 
 *
 */
public class MyUsernamePasswordNotMatchException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MyUsernamePasswordNotMatchException() {}
	public MyUsernamePasswordNotMatchException(String message) {
		super(message);
	}
}

package springmvc05.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器1
 * @author zhangqingli
 * @creation 2017年4月15日 
 *
 */
public class FirstHandlerInterceptor implements HandlerInterceptor {
	/**
	 * 在dispatcherServlet#handle（执行控制器方法）执行之前 执行applyPreHandle方法
	 * 可以做权限、日志、事务等
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println(">> [1] preHandle");
		return true;
	}

	/**
	 * 在dispatcherServlet#handle（执行控制器方法）之后 和 processDispatchResult（渲染视图）之前 
	 * 执行applyPostHandle方法
	 * 可以对请求域中的属性或试图做出修改
	 * 
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println(">> [1] postHandle");
	}

	/**
	 * 在dispatcherServlet#processDispatchResult（渲染视图）之后的finally中
	 * 执行applyAfterConcurrentHandlingStarted方法
	 * 可以做释放资源的操作
	 * 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println(">> [1] afterCompletion");
	}

}

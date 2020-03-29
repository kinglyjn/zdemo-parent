package springmvc05.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器2
 * @author zhangqingli
 * @creation 2017年4月15日 
 * 
 * 
 * 
 * 正常执行顺序：
 * [1]prehandle -------> [2]prehandle 
 * 								|
 * 							handle
 * 								|
 * [1]posthandle <------- [2]posthandle
 *		 |
 * [1]afterCompletion --> [2]afterCompletion
 * 
 * 
 * 
 * 非正常执行的顺序（[2]的prehandle返回了false）：
 * [1]prehandle -------> [2]prehandle 
 * 								|
 * [1]posthandle <--------------|
 * 		|
 * [1]afterCompletion
 * 
 * 	
 */
public class SecondHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println(">> [2] preHandle");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println(">> [2] postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println(">> [2] afterCompletion");
	}

}

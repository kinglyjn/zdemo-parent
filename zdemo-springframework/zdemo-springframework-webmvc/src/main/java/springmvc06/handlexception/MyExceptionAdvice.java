package springmvc06.handlexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 定义全局的异常处理
 * @author zhangqingli
 * @creation 2017年4月15日 
 * 
 * 其他还有springmvc默认异常处理器，
 * 参见org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
 *
 */
@ControllerAdvice
public class MyExceptionAdvice {

	/**
	 * 处理数学异常
	 * 注意：如果希望异常信息返回到页面上，必须使用modelAndView作为返回值！
	 * 
	 */
	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView handleArithmeticException(Exception ex) {
		System.out.println(">> springmvc06.handlexception.MyExceptionAdvice.handleArithmeticException(Exception)");
		System.out.println("出异常了，异常为：" + ex);
		
		ModelAndView mv = new ModelAndView("error/500");
		mv.addObject("exception", ex);
		return mv;
	}
	
	
	/**
	 * 处理数组越界异常
	 * 
	 */
	@ExceptionHandler({IndexOutOfBoundsException.class})
	public ModelAndView handleIndexOutOfBoundsException(Exception ex) {
		System.out.println(">> springmvc06.handlexception.MyExceptionAdvice.handleIndexOutOfBoundsException(Exception)");
		System.out.println("出异常了，异常为：" + ex);
		
		ModelAndView mv = new ModelAndView("error/500");
		mv.addObject("exception", ex);
		return mv;
	}
	
	
	/**
	 * 测试使用 @ResponseStatus 注解定制页面
	 * 
	 */
	@ResponseStatus(code=HttpStatus.FORBIDDEN, reason="用户名或密码错误，哈哈哈！")
	@ExceptionHandler({MyUsernamePasswordNotMatchException.class})
	public void handleMyUsernamePasswordNotMatchException() {
		System.out.println(">> springmvc06.handlexception.MyExceptionAdvice.handleMyUsernamePasswordNotMatchException()");
	}
}

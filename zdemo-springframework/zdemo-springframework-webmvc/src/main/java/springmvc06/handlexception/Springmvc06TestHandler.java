package springmvc06.handlexception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Springmvc06Handler
 * @author zhangqingli
 * @creation 2017年4月15日 
 *
 */
@Controller
@RequestMapping("/springmvc06")
public class Springmvc06TestHandler {
	
	@RequestMapping("/tomaintest")
	public String toMainTest() {
		System.out.println(">> springmvc06.handlexception.Springmvc06TestHandler.toMainTest()");
		return "springmvc06/maintest";
	}
	
	
	/**
	 * 单个handler的异常处理
	 * 注意：如果希望异常信息返回到页面上，必须使用modelAndView作为返回值！
	 *
	 */
	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView handleArithmeticException(Exception ex) {
		System.out.println(">> springmvc06.handlexception.Springmvc06TestHandler.handleArithmeticException(Exception)");
		System.out.println("出异常了，异常为：" + ex);
		
		ModelAndView mv = new ModelAndView("error/500");
		mv.addObject("exception", ex);
		return mv;
	}
	
	
	/**
	 * 测试使用 @ExceptionHandler 标注handler异常处理方法来处理单个控制器异常
	 * 
	 */
	@RequestMapping("/test01")
	public String test01(@RequestParam("i") Integer i) {
		System.out.println(">> springmvc06.handlexception.Springmvc06TestHandler.test01(Integer)");
		
		System.out.println("10/"+ i +" is: " + 10/i);
		return "success";
	}
	
	
	/**
	 * 测试使用 @ControllerAdvice 注解来定义全局的异常处理
	 * 见 springmvc06.handlexception.MyExceptionAdvice
	 * 
	 */
	@RequestMapping("/test02")
	public String test02(@RequestParam("i") Integer i) {
		System.out.println(">> springmvc06.handlexception.Springmvc06TestHandler.test02()");
		
		Integer[] array = new Integer[10];
		System.out.println("array[i]=" + array[i]);
		return "success";
	}
	
	
	/**
	 * 测试使用 SimpleMappingExceptionResolver 映射全局的异常处理
	 * 见springmvc.xml中对SimpleMappingExceptionResolver视图解析器的配置
	 * 
	 */
	@RequestMapping("/test03")
	public String test03(@RequestParam("i") Integer i) throws ClassNotFoundException {
		System.out.println(">> springmvc06.handlexception.Springmvc06TestHandler.test03()");
		
		if (i==0) {
			throw new ClassNotFoundException();
		}
		return "success";
	}
	
	
	/**
	 * 测试使用 @ResponseStatus 注解定制页面
	 * 见 springmvc06.handlexception.MyExceptionAdvice
	 * 
	 */
	@RequestMapping("/test04")
	public String test04(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println(">> springmvc06.handlexception.Springmvc06TestHandler.test04()");
		
		if (!("zhangsan".equals(username) && "123456".equals(password))) {
			throw new MyUsernamePasswordNotMatchException();
		}
		return "success";
	}
}

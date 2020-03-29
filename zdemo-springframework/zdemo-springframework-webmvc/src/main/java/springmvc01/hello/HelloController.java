package springmvc01.hello;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Hello
 * @author zhangqingli
 *
 */
@Controller
@RequestMapping("/springmvc01")
//将键为test17_user或类型为Address的数据放入session域中
@SessionAttributes(value={"test17_user"}, types={Address.class}) 
public class HelloController {
	private static final String SUCCESS = "success";
	
	@Autowired
	private ResourceBundleMessageSource messageResource;
	
	
	@RequestMapping("/tohello")
	public String toHello(Locale locale) {
		System.out.println(">> springmvc01.hello.HelloController.toHello(Locale)");
		System.out.println(messageResource.getMessage("i18n.username", null, locale));
		System.out.println(messageResource.getMessage("i18n.password", null, locale));
		return "springmvc01/springmvc01-main";
	}
	
	
	/**
	 * <pre>
	 * 由 @ModelAttribute标记的方法，会在每个方法执行之前被springmvc调用
	 *      
	 * 源码分析的流程:
	 * 	1. 调用 @ModelAttribute 注解修饰的方法，实际上是把 @ModelAttribute 
	 *     方法中Map的数据放在了implicitModel中
	 *  2. 解析请求处理器的目标参数，实际上该目标方法参数来自于WebDataBinder对象的target属性
	 *     1）创建WebDataBinder对象：
	 *        第一步：确定objectName属性：
	 *          i.若传入的attrName的属性值为""，则objectName为类名第一个字母小写；
	 *         	ii.目标方法的pojo参数也可以使用 @ModelAttribute 注解来修饰， 
	 *             @ModelAttribute 的value值即为attrName，此时若传入的attrName
	 *             的属性值不为""，那么attrName的值会赋给objectName
	 *        第二步：确定target属性：
	 *          在implicitModel中查找attrName对应的属性值，若存在ok。若不存在则验证当前handler是否
	 *          使用了 @SessionAttributes 进行修饰，若使用了，则尝试从session中获取attrName对应的
	 *          属性值，若session中没有对应的属性值则抛出异常；若handler没有使用 @SessionAttribute
	 *          来修饰，或者 @SessionAttributes 中没有使用value值指定的键和attrName相互匹配，则通过
	 *          反射创建了pojo对象。
	 *     2）springmvc把表单的请求参数赋给了WebDataBinder的target对应的属性 
	 *     3）*springmvc会把WebDataBinder的attrName和target给到implicitModel，进而后续会传递到request对象中
	 *     4）把WebDataBinder的target作为参数传递给目标方法的入参  
	 * </pre>       
	 *  
	 * <pre>       
	 * 综上所述：
	 * springmvc确定目标方法pojo类型入参的过程：
	 *  1. 确定一个key
	 *      i. 若目标方法的入参没有使用 @ModelAttribute 来修饰，则key为pojo首字母小写的非全限类名
	 *      ii. 若目标方法的使用了 @ModelAttribute 来修饰，则key为 @ModelAttribute 注解的value属性值
	 *  2. 在implicitModel中查找key对应的对象
	 *      i. 若存在则作为目标方法的入参（若在 @ModelAttribute 修饰的方法中在map中保存过，且key值和1确定的
	 *         key值一致，则会获取到）；
	 *      ii. 若不存在key所对应的对象，则检查当前的handler是否使用了 @SessionAttributes 注解来修饰，若使用
	 *         了该注解且该注解的的value属性中也包含了key，则就会从HttpSession中来获取key所对应的value值，若
	 *         存在key所对应的值则将此值传入到目标方法的入参中，若还不存在则直接抛出session attribute xxx required 异常。
	 *      iii. 若当前的handler没有使用 @SessionAttributes 注解来修饰 或 @SessionAttributes 注解的value
	 *         属性值中不包含key，则最终会通过发射来创建pojo类型的参数，作为目标方法的入参。
	 *  3. springmvc会将key和pojo类型的对象保存到implicitModel中，进而会保存到request作用域中    
	 * </pre>
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value="id", required=false) Integer id,
			ModelMap modelMap) {
		
		if (id != null) {
			// 模拟从数据库中获取对象
			User user = new User("张三", "123456", 23);
			user.setId(id);
			System.out.println("从数据库中获取的对象：" + user);
			modelMap.put("user", user);
		}
	}
	
	
	
	/**
	 * <pre>
	 * 使用 @RequestMapping 映射请求的url
	 * 1. 使用 @RequestMapping 映射请求的url
	 * 2. 返回值会通过视图解析器（如 InternalResourceViewResolver）解析为实际的物理视图
	 * 	    i. 通过 prefix(/WEB-INF/views/) + retrunval + suffix(.jsp) 这样的方式得到实际的物理视图
	 *      ii. 然后做转发操作
	 * </pre>
	 */
	@RequestMapping("/test01")
	public String test01() {
		System.out.println(">> HelloController#test01");
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * 使用 @RequestMapping 映射请求方法
	 * </pre>
	 */
	@RequestMapping(value="/test02", method={RequestMethod.POST})
	public String test02() {
		System.out.println(">> HelloController#test02");
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * 使用 @RequestMapping 映射请求参数
	 * 
	 * 使用params 和 headers支持简单的表达式：
	 *     param1: 表示请求必须包含名称为param1的参数
	 *     !param1: 表示请求不能包含名称为param1的参数
	 *     param1!=value1: 表示请求包含名称为param1的参数，但是其值不能是value1
	 *     {"param1=value1", "param2"}: 表示请求必须包含名称为param1和param2的两个参数，且param1的值必须为value1
	 * </pre>
	 */
	@RequestMapping(value="/test03", params={"username", "age!=10"})
	public String test03() {
		System.out.println(">> HelloController#test03");
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * 使用 @RequestMapping 映射请求头、提交内容类型、返回的内容类型
	 * headers：映射请求头参数
	 * consumes：映射请求的提交内容类型（Content-Type），例如application/json, text/html;
	 * produces：映射返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回
	 * </pre>
	 * 
	 */
	@RequestMapping(value="/test04", headers={"Content-Language=en-US"})
	public String test04() {
		System.out.println(">> HelloController#test04");
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * 使用 @RequestMapping 映射ant风格路径
	 *     **：可以匹配任意多层路径
	 *     *：可以匹配任意字符
	 *     ?：可以匹配一个字符
	 * </pre>
	 *     
	 */
	@RequestMapping("/test05/*/abc")
	public String test05() {
		System.out.println(">> HelloController#test05");
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * 使用 @PathVariable 映射请求路径中的参数
	 * </pre>
	 * 
	 */
	@RequestMapping("/test06/delete/{id}")
	public String test06(@PathVariable("id") Integer id) {
		System.out.println(">> HelloController#test06");
		System.out.println("id=" +id);
		return SUCCESS;
	}
	
	
	
	/**
	 * <pre>
	 * 测试对REST风格请求的支持
	 * 
	 * REST方式的请求示例：
	 *     /order    HTTP POST: 新增order
	 *     /order/1  HTTP DELETE: 删除id为1的order
	 *     /order/1  HTTP PUT: 更新id为1的order
	 *     /order/1  HTTP GET: 获取id为1的order
	 *     
	 *     页面中的请求：（关键是要传递name为_method的隐藏域的值）
	 *     &lt;form url="/delele" method="post&gt;
	 *         &lt;input type="hidden" name="_metod" value="DELETE"/&gt;
	 *         &lt;input type="submit" value="提交"/&gt;
	 *     &lt;/form&gt;
	 *     
	 * HiddenHttpMethodFilter：（需要在web.xml配置该过滤器）
	 *     浏览器form表单只支持get和post请求，而delete、put等method并不支持，
	 *     spring3.0增加了这个过滤器，可以将这些请求转换为标准的http方法，使
	 *     得支持post、delete、put、get请求
	 * </pre>
	 * 
	 */
	@RequestMapping("/test07/post")
	public String test07() {
		System.out.println(">> HelloController#test07");
		return SUCCESS;
	}
	
	@RequestMapping("/test08/delete/{id}")
	public String test08(@PathVariable("id") Integer id) {
		System.out.println(">> HelloController#test08");
		System.out.println("id=" + id);
		return SUCCESS;
	}
	
	@RequestMapping("/test09/put/{id}")
	public String test09(@PathVariable("id") Integer id) {
		System.out.println(">> HelloController#test09");
		System.out.println("id=" + id);
		return SUCCESS;
	}
	
	@RequestMapping("/test10/get/{id}")
	public String test10(@PathVariable("id") Integer id) {
		System.out.println(">> HelloController#test10");
		System.out.println("id=" + id);
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * 使用 @RequestParam 绑定请求参数
	 * </pre>
	 * 
	 */
	@RequestMapping("/test11")
	public String test11(@RequestParam(value="username") String un, 
			@RequestParam(value="age", required=false, defaultValue="0") int age) {
		System.out.println(">> HelloController#test11");
		System.out.println("username=" + un + ", age=" + age);
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * 使用 @CookieValue 绑定cookie参数
	 * </pre>
	 * 
	 */
	@RequestMapping("/test12")
	public String test12(@CookieValue("JSESSIONID") String sessionid) {
		System.out.println(">> HelloController#test12");
		System.out.println("sessionid=" + sessionid);
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * 测试pojo对象的绑定
	 * springmvc会按请求参数名后pojo的属性名进行自动匹配，
	 * 自动为该对象填充属性值，并且支持级联属性，如user.address.province
	 * </pre>
	 * 
	 */
	@RequestMapping("/test13")
	public String test13(User user) {
		System.out.println(">> HelloController#test13");
		System.out.println(user);
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * 测试springmvc获取servlet原生API
	 * 支持的类型见
	 * org.springframework.web.servlet.mvc.annotation
	 * 	.AnnotationMethodHandlerAdapter.ServletHandlerMethodInvoker#resolveStandardArgument方法
	 * 		HttpServletRequest
	 * 		HttpServletRequest
	 * 		HttpSession
	 * 		java.security.Principal
	 * 		java.util.Locale	
	 * 		InputStream
	 * 		OutputStream
	 * 		Reader
	 * 		Writer
	 * </pre>
	 * 
	 */
	@RequestMapping("/test14")
	public void test14(HttpServletRequest request, 
			HttpServletResponse response,
			HttpSession session,
			java.security.Principal principal,
			Locale locale,
			Writer writer) throws IOException {
		
		System.out.println(">> HelloController#test14");
		System.out.println("request: " + request);
		System.out.println(request.getCharacterEncoding());
		System.out.println("response: " + response);
		System.out.println("session: " + session);
		System.out.println("principal: " + principal);
		System.out.println("locale: " + locale); //zh_CN
		//
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		response.setHeader("Server", "Nginx");
		response.setCharacterEncoding(request.getCharacterEncoding());
		writer.write("<h4>hello springmvc!你好！</h4>");
	}
	
	
	/**
	 * <pre>
	 * springmvc处理数据模型之 modelAndView（作为返回值）
	 * springmvc会将modelAndView的model中的数据放到request作用域中
	 * 参见源码：org.springframework.web.servlet.view.AbstractView#exposeModelAsRequestAttributes
	 * </pre>
	 * 
	 */
	@RequestMapping("/test15")
	public ModelAndView test15() {
		System.out.println(">> HelloController#test15");
		ModelAndView modelAndView = new ModelAndView(SUCCESS);
		modelAndView.addObject("test15_time", new Date());
		return modelAndView;
	}
	
	
	/**
	 * <pre>
	 * springmvc处理数据模型之 Map&amp;Model（作为参数或者是返回值）
	 * 实际上类型也可以是Model或ModelMap类型
	 * </pre>
	 * 
	 */
	@RequestMapping("/test16")
	public String test16(Map<String, Object> map) {
		System.out.println(">> HelloController#test16");
		System.out.println(map.getClass().getName()); //BindingAwareModelMap
		map.put("test16_names", Arrays.asList("张三", "李四", "小娟"));
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * springmvc处理数据模型之 @SessionAttributes (将值放在session域中)
	 * </pre>
	 * 
	 */
	@RequestMapping("/test17")
	public String test17(Map<String, Object> map) {
		System.out.println(">> HelloController#test17");
		map.put("test17_user", new User("张三", "1234567", 23));
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * springmvc处理数据模型之 @ModelAttributes（在目标方法执行之前将数据放到数据模型中）
	 * </pre>
	 * 
	 */
	@RequestMapping("/test18")
	public String test18(@ModelAttribute("user") User user111) {
		System.out.println(">> HelloController#test18");
		System.out.println("修改后的user：" + user111);
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * springmvc视图解析流程断点分析
	 * 
	 * 请求的方法完成之后，最终会返回一个ModelAndView对象。对于那些返回值为String、View或者ModelMap型
	 * 的处理方法，springmvc也会在内部将他们转化成一个ModelAndView对象，它包含了逻辑名和模型对象的视图。
	 * springmvc借助视图解析器（ViewResolver）得到最终的视图，最终的视图可以使JSP，也可能是Excel、
	 * jfreeChart等各种视图的表现形式。
	 * 对于最终将采用何种视图对象对模型数据进行渲染，处理器并不关心，处理器的工作重点聚焦在生产模型数据的
	 * 工作上，从而实现mvc的充分解耦。
	 * 
	 * 视图的作用是渲染模型数据，将模型里的数据以某种形式呈献给客户，为了实现视图模型和具体实现技术的解耦，
	 * spring在org.springframework.web.servlet包中定义了一个高度抽象的View接口。
	 * 视图对象由试图解析器负责实例化。由于视图是无状态的（每一次请求创建新的试图对象），所以他们不会有线
	 * 程安全的问题。
	 * 
	 * springmvc为逻辑视图名的解析提供了不同的策略，可以在spring-web上下文中配置一种或多种解析策略，并
	 * 指定他们之间的先后顺序。每一种映射策略对应一个具体的试图解析器实现类。
	 * 试图解析器的作用比较单一，就是逻辑视图解析为一个具体的视图对象。
	 * 所有的视图解析器都必须实现ViewResolver接口。
	 * 
	 * 若在项目中使用了jstl，则springmvc会自动把视图由InteralResourceView转为JstlView
	 * 若是使用jstl的fmt标签则需要在springmvc的配置文件中配置国际化资源文件
	 * &lt;bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource"&gt;
	 *     &lt;property name="basename" value="i18n"&gt;&lt;/property&gt;
	 * &lt;/bean&gt;
	 * 若是希望直接响应通过springmvc的渲染页面，则可以使用mvc:view-controller标签实现
	 * &lt;mvc:view-controller path="/springmvc/test19" view-name="success"/&gt;
	 * 
	 * String
	 * ModelAndView ----->ModelAndView---(ViewResolver)--->View----(view#render)--->响应结果
	 * ModelMap
	 * </pre>
	 * 
	 */
	@RequestMapping("/test19")
	public String test19() {
		System.out.println(">> HelloController#test19");
		return SUCCESS;
	}
	
	
	/**
	 * <pre>
	 * 测试自定义视图
	 * </pre>
	 * 
	 */
	@RequestMapping("/test20")
	public String test20() {
		System.out.println(">> HelloController#test20");
		return "helloView";
	}
	
	
	/**
	 * <pre>
	 * 转发测试（默认就是转发，可以不写forward关键字）
	 * springmvc会在内部自动处理forward和redirect关键字，将他们映射成转发或重定向操作
	 * 参见org.springframework.web.servlet.view.UrlBasedViewResolver.createView(String, Locale)
	 * </pre>
	 * 
	 */
	@RequestMapping("/test21")
	public String test21() {
		System.out.println(">> HelloController#test21");
		return "forward:/success";
	}
	
	
	/**
	 * <pre>
	 * 重定向测试
	 * </pre>
	 * 
	 */
	@RequestMapping("/test22")
	public String test22() {
		System.out.println(">> HelloController#test22");
		return "redirect:/success";
	}
}

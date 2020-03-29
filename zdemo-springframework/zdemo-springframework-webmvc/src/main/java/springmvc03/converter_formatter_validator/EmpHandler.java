package springmvc03.converter_formatter_validator;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * EmpHandler
 * @author zhangqingli
 * @creation 2017年4月12日 
 *
 */
@Controller("empHandler03")
@RequestMapping("/springmvc03")
public class EmpHandler {
	
	@Autowired
	@Qualifier("empService03")
	private EmpService empService;
	
	@Autowired
	@Qualifier("deptService03")
	private DeptService deptService;
	
	
	
	/**
	 * ModelAttribute方法
	 * 
	 */
	@ModelAttribute
 	public void getEmpFromDB(@RequestParam(value="id", required=false) Integer id, 
 			ModelMap modelMap) {
		if (id != null) {
			//注意只有修改操作的时候才会传递id参数，其他时候我们都是使用REST风格将id参数绑定到url中
			modelMap.put("emp", empService.getEmp(id));
		}
	}
	
	
	/**
	 * 跳转列表页
	 * 
	 */
	@RequestMapping(value="/emps", method=RequestMethod.GET)
	public String getEmps(Map<String, Object> map) {
		System.out.println(">> EmpHandler#getEmps");
		
		map.put("emps", empService.getAll()); //
		return "springmvc03/emps";
	}
	
	
	/**
	 * 跳转添加页
	 * 
	 */
	@RequestMapping(value="/emp", method=RequestMethod.GET)
	public String getEmp(Map<String, Object> map) {
		System.out.println(">> EmpHandler#getEmp with map");
		
		map.put("emp", new Emp());
		map.put("depts", deptService.getAll());
		return "springmvc03/emp";
	}
	
	/**
	 * 跳转修改页
	 * 
	 */
	@RequestMapping(value="/emp/{id}", method=RequestMethod.GET)
	public String getEmp(@PathVariable(value="id", required=true) Integer id, 
			Map<String, Object> map) {
		System.out.println(">> EmpHandler#getEmp with emp.id and map");
		
		map.put("emp", empService.getEmp(id));
		map.put("depts", deptService.getAll());
		return "springmvc03/emp";
	}
	
	
	/**
	 * 添加操作：
	 * 
	 */
	@RequestMapping(value="/emp", method=RequestMethod.POST)
	public String postEmp(@Valid Emp emp, BindingResult bindResult, 
			Map<String, Object> map){
		System.out.println(">> EmpHandler#postEmp");
		
		//bindResult
		if (bindResult.getErrorCount() > 0) {
			System.out.println("出错啦！");
			List<FieldError> errors = bindResult.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getField() + ": " + error.getDefaultMessage());
			}
			//表单出错跳转到定制页面
			map.put("depts", deptService.getAll());
			return "springmvc03/emp";
		}
		
		empService.saveOrUpdate(emp);
		return "redirect:/springmvc03/emps?t="+new Date().getTime(); //默认为302重定向
	}
	
	
	/**
	 * 修改操作：
	 * 
	 */
	@RequestMapping(value="/emp", method=RequestMethod.PUT)
	public String putEmp(@Valid @ModelAttribute("emp") Emp emp, BindingResult bindResult,
			Map<String, Object> map) {
		System.out.println(">> EmpHandler#putEmp");
		
		//bindResult
		if (bindResult.getErrorCount() > 0) {
			System.out.println("出错啦！");
			List<FieldError> errors = bindResult.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getField() + ": " + error.getDefaultMessage());
			}
			//表单出错跳转到定制页面
			map.put("depts", deptService.getAll());
			return "springmvc03/emp";
		}
		
		empService.saveOrUpdate(emp);
		return "redirect:/springmvc03/emps?t="+new Date().getTime();
	}
	
	
	/**
	 * 删除操作
	 * 
	 */
	@RequestMapping(value="/emp/{id}", method=RequestMethod.DELETE)
	public String deleteEmp(@PathVariable(value="id", required=true) Integer id) {
		System.out.println(">> EmpHandler#deleteEmp");
		
		empService.delete(id);
		return "redirect:/springmvc03/emps?t="+new Date().getTime();
	}
	
	
	
	/**
	 * @InitBinder 注解测试
	 * 
	 * 1. 由 @InitBinder 标识的方法，可以对WebDataBinder对象进行初始化。
	 *    WebDataBinder是DataBinder的子类，用于完成由表单字段到javaBean属性的绑定。
	 * 2. @InitBinder 标识的方法不能有返回值（必须声明为void）
	 * 3. @InitBinder 标识的方法的参数通常是WebDataBinder
	 * 
	 */
	/*@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("email"); //不对email字段进行映射
	}*/
	
	
	
	/**
	 * 类型转换器测试
	 *   1. 开启了 &lt;mvc:annotation-driven conversion-service="conversionService"/&gt;
	 *   2. 装配了格式化和类型转换器 FormattingConversionServiceFactoryBean
	 *   3. 在FormattingConversionServiceFactoryBean中添加了自定义类型转换器
	 *   
	 */
	@RequestMapping(value="/testconverter01", method=RequestMethod.POST)
	public String testConverter01(Emp emp) {
		System.out.println(">> EmpHandler#testConverter01");
		System.out.println(emp);
		return "success";
	}
	
	
	/**
	 * 格式化器测试
	 *   1. 开启了 &lt;mvc:annotation-driven conversion-service="conversionService"/&gt;
	 *   2. 装配了格式化和类型转换器 FormattingConversionServiceFactoryBean
	 *   3. 在相应pojo上有 @DateTimeFormat(pattern="yyyy-MM-dd") 或 @NumberFormat(pattern="#,###,#") 等注解
	 *      或在FormattingConversionServiceFactoryBean中添加了自定义格式化器
	 *      
	 */
	@RequestMapping(value="/testformatter01", method=RequestMethod.POST)
	public String testFormatter01(Emp emp, BindingResult bindingResult) { //BindingResult中封装了结果的信息
		System.out.println(">> EmpHandler#testFormatter01");
		
		if (bindingResult.getErrorCount() > 0) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (ObjectError error : errors) {
				System.out.println(error.getObjectName() + " : " + error.getDefaultMessage());
			}
			return "error/500";
		}
		
		System.out.println(emp);
		return "success";
	}
	
}

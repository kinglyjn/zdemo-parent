package springmvc02.curd;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
@Controller
@RequestMapping("/springmvc02")
public class EmpHandler {
	
	@Autowired
	private EmpService empService;
	
	@Autowired
	private DeptService deptService;
	
	
	
	/**
	 * ModelAttribute方法
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
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/emps", method=RequestMethod.GET)
	public String getEmps(Map<String, Object> map) {
		System.out.println(">> EmpHandler#getEmps");
		
		map.put("emps", empService.getAll()); //
		return "springmvc02/emps";
	}
	
	
	/**
	 * 跳转添加页
	 * Springmvc默认是必须要进行表单回显的，需要事先将表单bean（emp）放入作用域进行回显，否则报错！
	 * 
	 */
	@RequestMapping(value="/emp", method=RequestMethod.GET)
	public String getEmp(Map<String, Object> map) {
		System.out.println(">> EmpHandler#getEmp with map");
		
		map.put("emp", new Emp());
		map.put("depts", deptService.getAll());
		return "springmvc02/emp";
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
		return "springmvc02/emp";
	}
	
	
	/**
	 * 添加操作：
	 * 注意springmvc默认会对页面进行缓存，缓存的容器默认为
	 * AbstractCachingViewResolver#viewCreationCache属性（是一个linkedHashMap<Object, View>）
	 * 默认的缓存大小为 AbstractCachingViewResolver#DEFAULT_CACHE_LIMIT=1024，当缓存页面的数量
	 * 超过该值，springmvc将移除viewCreationCache缓存中最旧的缓存页面！
	 * 为了防止添加完成跳转到列表页时列表页面被缓存（得到的是一个缓存的list页面），
	 * 这时候可以在跳转url（缓存的key）后加一个动态参数以防止页面是从缓存中获取到的。
	 * 
	 */
	@RequestMapping(value="/emp", method=RequestMethod.POST)
	public String postEmp(Emp emp){
		System.out.println(">> EmpHandler#postEmp");
		
		empService.saveOrUpdate(emp);
		return "redirect:/springmvc02/emps?t="+new Date().getTime(); //默认为302重定向
	}
	
	
	/**
	 * 修改操作：
	 * 
	 */
	@RequestMapping(value="/emp", method=RequestMethod.PUT)
	public String putEmp(@ModelAttribute("emp") Emp emp) {
		System.out.println(">> EmpHandler#putEmp");
		
		empService.saveOrUpdate(emp);
		return "redirect:/springmvc02/emps?t="+new Date().getTime();
	}
	
	
	/**
	 * 删除操作
	 * 
	 */
	@RequestMapping(value="/emp/{id}", method=RequestMethod.DELETE)
	public String deleteEmp(@PathVariable(value="id", required=true) Integer id) {
		System.out.println(">> EmpHandler#deleteEmp");
		
		empService.delete(id);
		return "redirect:/springmvc02/emps?t="+new Date().getTime();
	}
}

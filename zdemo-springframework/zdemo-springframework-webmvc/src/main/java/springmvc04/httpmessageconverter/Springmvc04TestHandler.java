package springmvc04.httpmessageconverter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/springmvc04")
public class Springmvc04TestHandler {
	
	
	@RequestMapping("/tomaintest")
	public String toMainTest() {
		System.out.println(">> springmvc04.httpmessageconverter.Springmvc04TestHandler.toMainTest()");
		return "springmvc04/maintest";
	}
	
	
	/**
	 * 使用HttpMessageConverter的@ResponseBody修饰handler方法的返回值，来处理json
	 * 需要事先加入jackson的jar包
	 * 
	 */
	@RequestMapping("/test01")
	@ResponseBody
	public List<User>  test01() {
		System.out.println(">> springmvc04.httpmessageconverter.Springmvc04TestHandler.test01()");
		List<User> users = new ArrayList<User>();
		
		User user01 = new User(1, "张三", "123456", 23, new Address("山东", "聊城"));
		User user02 = new User(2, "李四", "123456", 24, new Address("山东", "济南"));
		User user03 = new User(3, "张三", "123456", 22, new Address("北京", "北京南站"));
		users.add(user01);
		users.add(user02);
		users.add(user03);
		
		return users;
	}
	
	/**
	 * 使用HttpMessageConverter的@RequestBody修饰handler方法的入参，@ResponseBody修饰返回值，测试
	 * 
	 */
	@RequestMapping("/test02")
	@ResponseBody
	public String test02(@RequestBody String requestBody) {
		System.out.println(">> springmvc04.httpmessageconverter.Springmvc04TestHandler.test02()");
		System.out.println(requestBody);
		return "demo01_hello, time is " + new  Date();
	}
	
	/**
	 * 使用HttpMessageConverter的@ResponseBody修饰返回值实现下载功能
	 * 
	 */
	@RequestMapping("/test03")
	@ResponseBody
	public byte[] test03(HttpSession session, HttpServletResponse response) throws IOException {
		InputStream is = session.getServletContext().getResourceAsStream("/static/js/jquery/jquery-2.1.4.min.js");
		byte[] bs = FileCopyUtils.copyToByteArray(is); /*byte[] bs = new byte[is.available()]; is.read(bs);*/
		
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=jquery.js");
		return bs;
	}
	
	/**
	 * 使用HttpMessageConverter的ResponseEntity<T>修饰返回值实现下载功能
	 * 
	 */
	public ResponseEntity<byte[]> test04(HttpSession session) throws IOException {
		System.out.println(">> springmvc04.httpmessageconverter.Springmvc04TestHandler.test04(HttpSession)");
		InputStream is = session.getServletContext().getResourceAsStream("/static/js/jquery/jquery-2.1.4.min.js");
		byte[] bs = FileCopyUtils.copyToByteArray(is);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=jquery.js");
		HttpStatus status = HttpStatus.OK;
		return new ResponseEntity<byte[]>(bs, headers, status);
	}
	
	
	/**
	 * 使用CommonsMutipartResolver组件实现上传功能
	 * 需要commons-fileupload 和 commons-io的支持
	 * 需要在springmvc中装配CommonsMutipartResolver组件bean
	 * 
	 */
	@ResponseBody //注意StringHttpMessageConverter的默认编码是ISO-8859-1，所以这里需要指定返回数据的类型
	@RequestMapping(value="/test05", produces={"text/html;charset=UTF-8"})
	public String test05(@RequestParam(value="desc", required=false) String desc,
			@RequestParam(value="file", required=true) MultipartFile file) throws IOException {
		System.out.println(">> springmvc04.httpmessageconverter.Springmvc04TestHandler.test05(String, MultipartFile)");
		
		System.out.println("contentType: " + file.getContentType());
		System.out.println("filename: " + file.getOriginalFilename());
		System.out.println("size: " + file.getSize());
		System.out.println("inputStream: " + file.getInputStream());
		
		return "<h4>上传成功！<h4>";
	}
}


HttpMessageConverter<T>：
	使用HttpMessageConverter<T>将请求信息转化并绑定到处理方法的入参中 
	或者将响应的结果转为对应的响应信息，spring提供了两种途径：
	    1. 使用@RequestBody/@ResponseBody对处理方法进行标注
	    2. 使用HttpEntity<T>/ResponseEntity<T>作为处理方法的入参或者返回值
	当控制器处理方法使用到@RequestBody/@ResponseBody 或 HttpEntity<T>/ResponseEntity<T>时，
	spring首先根据请求头或响应头Accept属性选择匹配的HttpMessageConverter，进而根据参数类型或
	泛型的类型过滤器得到匹配的HttpMessageConverter，若找不到可用的HttpMessageConverter将报错。
   【注】@RequestBody和@ResponseBody不需要成对出现
	
	
	
文件上传：
	springmvc为文件的上传提供了更直接的支持，这种支持是通过即插即用的MutipartResolver实现的。
	spring用jakarta commons fileupload技术实现了一个MultipartResolver的实现类，即：
	CommonsMultipartResolver
	
	springmvc上下文中默认没有装配MutipartResolver，因此默认情况下不能使用MutipartResolver
	进行文件的上传工作，如果想使用spring的文件上传功能，需要在上下文中配置MultipartResolver
	
	
	
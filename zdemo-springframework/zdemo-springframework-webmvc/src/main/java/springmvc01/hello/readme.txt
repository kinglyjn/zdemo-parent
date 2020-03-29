
关于使用 <mvc:annotation-driven/>：
	springmvc的mvc:annotation-driven会自动注册
	ReuquestMappingHandlerMapping、RequestMappingHandlerAdapter、ExceptionHandlerExceptionResolver三个bean。
	
	还将提供一下支持：
		支持使用ConversionService实例对表单参数进行类型转换
		支持使用 @NumberFormat、@DateTimeFormat注解来完成数据类型的格式化
		支持使用 @Valid 注解对javaBean实例进行 jsr303 验证
		支持使用 @RequestBody 和 @ResponseBody 注解
		
		
	
		
		

	
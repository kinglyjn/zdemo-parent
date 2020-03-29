ExceptionHandlerExceptionResolver:
	主要处理handler中用@ExceptionHandler注解定义的方法。
	
	@ExceptionHandler注解定义方法的优先级问题：例如发生的是NullPointerException，
	但是声明的以上有RuntimeException和Exception，这时会根据异常的最近继承关系找到
	继承关系最浅的那个。
	@ExceptionHandler注解方法，即标记了RuntimeException的方法
	
	ExceptionHandlerMethodResolver内部若是找不到@ExceptionHandler注解的话，会找
	@ControllerAdvice中的@ExceptionHandler注解方法。
	
		
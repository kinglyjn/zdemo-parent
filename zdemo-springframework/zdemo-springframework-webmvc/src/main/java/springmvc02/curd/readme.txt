
数据绑定流程：
	1. springmvc主框架将servletRequest对象及目标方法的入参实例传递传递给WebDataBinderFactory实例，
	   以创建DataBinder对象
	   
	2. DataBinder调用装配在springmvc上下文中的ConversionService组件进行数据类型的转换、数据格式工作，
	   将servlet请求中信息填充到入参对象中
	   
	3. 调用Validator组件对已经绑定了请求消息的入参对象进行数据合法性校验，并最终生成数据绑定结果
	   BindingData对象
	
	4. springmvc抽取BindingResult中的入参对象和校验错误对象，将他们赋值给处理方法的响应入参。
	
	   
	 
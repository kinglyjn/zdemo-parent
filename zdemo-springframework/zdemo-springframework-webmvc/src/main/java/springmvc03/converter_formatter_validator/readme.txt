
JSR303:
	JSR303是java为bean数据合法性校验提供的标准框架，它已经包含在了javaee6.0+中。
	JSR303通过在bean属性上标注类似于@NotNull、@Max等标准的注解指定校验规则，并
	通过标准的验证接口对bean进行验证。
	
	@Null（被标注的元素必须为null）
	@NotNull（被标注的元素必须不为null）
	@AssertTrue（被标注的元素必须为true）
	@AssertFalse（被标注的元素必须为false）
	@Min(value)（被标注的元素必须为数字，其值最小值为value）
	@Max(value)（被标注的元素必须为数字，其值最大值为value）
	@DecimalMin(value)（被标注的元素必须为数字，其值最小值为value）
	@DecimalMax(value)（被标注的元素必须为数字，其值最大值为value）
	@Size(max, min)（被标注的元素必须为数字，大小在指定范围内）
	@Digits(integer, fraction)（被标注的元素必须为数字，大小在可接受范围内）
	@Past(被标注的元素必须为一个过去的日期)
	@Future（被标注的元素必须为一个将来的日期）
	@Pattern(value)（被标注的元素必须符合指定的正则表达式）
	
	
Hibernate Validaor 是jsr303的一个参考实现，除了支持所有标准的校验注解外，它还支持以下扩展注解：
	@Email（被标注的元素必须为电子邮件）
	@Length（被标注的元素必须为字符串，其长度必须在指定的范围之内）
	@NotEmpty（被标注的元素必须非空）
	@Range（被标注的元素必须在指定的范围之内）
	
	
使用JSR303对绑定数据进行校验：
	1. 加入hibernate validator jar包
	2. springmvc开启mvc:annotation-driven
	3. 需要在 pojo bean的属性上添加对应的校验注解
	4. 在目标方法参数前添加 @Valid 注解
	   注意需校验的bean对象和其绑定结果对象是成对出现的他们之间不允许声明其他类型的入参
	   
	   
在页面上显示错误消息：
	springmvc除了将表单/命令对象的校验结果保存到对应的bindingResult或errors对象之外，
	还会将所有的校验结果保存到“隐含模型”。即使处理方法的签名中没有对应于表单/命令对象的
	结果入参，校验结果也会保存在“隐含模型中”。
	
	隐含模型中的所有数据最终会通过httpServletRequest的属性列表暴露给jsp视图对象，因此在
	jsp中可以获取错误信息。在jsp页面上可以通过<form:errors path="userName"/>方式显示
	错误消息。
	

提示消息的国际化：
	每个属性在数据绑定和数据校验发生错误时，都会生成一个对应FieldError对象。当一个校验失败
	后，校验框架会为该属性生成4个消息代码，这些代码以校验注解类名为前缀，结合modelAttribute、
	属性名及属性类型名生成多个对应的消息代码：例如User类中的password所定义的规则时，就生成了
	一下4种错误代码：
		Pattern.user.password
		Pattern.password
		Pattern.java.lang.String
		Pattern
	当使用sprinmvc标签显示错误消息时，springmvc会查看web上下文中是否配置了对应的国际化资源
	消息，如果没有，则显示默认的错误消息，否则就使用国际化消息。
	
	若数据类型转化 或 数据格式转换时发生错误，或该有的参数不存在，或调用处理方法时候发生错误，
	都会在隐含模型中创建错误消息，其代码的前缀说明如下：
		required：必要的参数不存在。如
			@RequiredParam("param1") 标注了一个入参，但是该参数不存在
		typeMismatch：数据邦定数据时，发生数据类型不匹配的问题
		methodInvocation：springmvc调用处理方法时候发生了错误
		
			
		
	
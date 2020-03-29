
组件扫描：
	 spring能从classpath下自动扫描、侦测和实例化具有特定注解的组件。这些组件包括：
	 @Component
	 @Repository
	 @Service
	 @Controller
	 对于扫描到的组件，spring有默认的命名策略（使用非全限类名，第一个字母小写，也可以
	 通过value属性值标识组件的id名称）
	 
	 当在组件类上使用了特定的注解之后，还需要在spring的配置文件中声明<context:component-scan>
	 base-package: 指定需要扫描的基类包，spring容器将扫描这个基类包及其子包中的所有的类，当需要
	 			   扫描多个包时，可以使用都好分隔
	 resource-pattern: 如果仅希望扫描扫描特定的类而非基类包下的所有类，可使用该属性过滤特定的类，
	 				   如：resource-pattern="spring02/genericdi_annotation/*.class"
	 -<context:include-filter>子节点表示需要包含的目标类
	 -<context:exclude-filter>子节点表示需要排除在外的目标类
	 -include和exclude子节点支持多种类型的过滤表达式，常用annotation（根据注解）、assignable（根据接口或类）
	 -include子节点需要和 [component-scan的use-default-filers="false"] 属性配合使用
	 -<context:component-scan>下可以拥有多个include和exclude子节点	 


@Autowired自动装配
	使用@Autowared注解自动装配具有兼容类型的单个bean属性，如果spring发现匹配接口的类型不止一个，
	此时@Autowired会根据bean的名字进行匹配，如果根据名字匹配不上，则spring会抛异常。这时候可以
	增加@Qualifiter注解设置以指定bean的名称。@Autowired可以用在构造器、普通字段（即使是非public的）、
	一切具有参数的方法上。
	
	默认情况下，所有使用@Autowired注解的属性都需要被设置，当spring找不到匹配的bean装配属性时会抛出
	异常。若某一属性允许不被设置，可以设置@Autowired注解的required属性为false。
	
	@Autowired注解可以应用在数组类型的属性上，此时spring将会把所有匹配的bean进行自动装配。
	@Autowired注解可以应用在集合属性上，此时spring读取该集合的类型信息，然后自动装配所有与之兼容的bean。
	@Autowired注解用在Map上时，若该Map的键为String类型，那么spring将自动装配与之Map值类型兼容的bean，
			  此时bean的名称作为键值。
			  
	
	
泛型依赖注入
	spring4.x中可以为子类注入子类对应的泛型类型的成员变量的引用
	
		BaseService<T> ------------------------> BaseRepository<T>
		      /\									    /|
			    \								  	   /
			     \								  	  /
		    UserService<User> -------------> UserRespository<User>
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  

AOP（Aspect Oriented Programming）- 面向切面编程
	它是一种新的方法论，是对传统的面向对象编程（OOP）的一个补充。AOP的主要编程对象是切面（aspect），
	AOP编程抽取出一个个的横切关注点，这些横切关注点就组成一个切面，切面就是将横切关注点模块化。
	
	
	基本概念：
		切面（aspect）: 横切关注点被模块化后的特殊对象
		通知（advice）: 切面必须要完成的工作
		目标（target）: 被通知的对象
		代理（proxy）: 向目标对象通知之后被创建的对象
		连接点（joinpoint）: 程序执行的摸个特定的位置，如某个类方法的调用之前、调用之后、方法抛出异常后等。
						连接点是由两个信息确定的，即方法和方位。
		切点（pointcut）: 如果说连接点是程序类中一个个的客观物理存在，那么切点就相当于查询连接点的查询条件。
						
						//可以声明一个切入点方法用以重用切入点表达式，如
						@Pointcut("execution(int spring03.aop_annotation.ArithmeticCaculatorImpl.*(int, int))")
						public void pointcut01() {}
						
						//使用示例
						@Before("pointcut01()")
						@After("MyArithmeticCaculatorLoggingAspect.pointcut01()")
						@AfterReturning(value="spring03.aop_annotation.MyArithmeticCaculatorLoggingAspect.pointcut01()", returning="result")
						@AfterThrowing(value="pointcut01()", throwing="ex")
						@Around("pointcut01()")
						
						
AspectJ
	java社区里做完美流行的aop框架，在spring2.0以上的版本中，
	可以使用基于aspectj注解 或 基于xml配置的AOP。
	
	基于注解的aspectj:
		1) 加入注解jar，并在spring配置文件中加入启用切面注解的配置
			spring-aop  aspectjrt  aspectjweaver  [aopalliance可以没有]
		2) 在spring配置文件中加入aop命名空间，并配置开启aop注解
			<aop:aspectj-autoproxy/>
		3) 切面类上需加注解 @Component和@Aspect 以表明这是一个被spring管理的aop切面
		   如果一个类存在多个切面，可以在其对应的切面类上增加@Order注解以设置切面的优先级，优先级值越小就越早执行
			
		4) 在切面类中声明各种通知：
			i) 前置通知 @Before
			ii) 后置通知 @After
			iii) 返回通知 @AfterReturning
			iv) 异常通知 @AfterThrowing
			v) 环绕通知 @Around
	
		
	


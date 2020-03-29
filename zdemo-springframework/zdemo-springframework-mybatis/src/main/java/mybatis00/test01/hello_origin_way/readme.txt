
mybatis开发dao的两种方式：(注意开发mybatis时候将log日志的输出级别设置为debug)
	1. 原始dao开发方法（需要编写dao接口和dao实现类）
	2. mybatis的mapper接口代理开发方法（推荐）
	
	
mybatis示例内容：
	1. 输入和输出映射（parameterType、resultType&resultMap）
	2. 动态sql（sql segment & where、if、foreach）
	3. 高级映射和延迟加载（1-1、1-n、n-n）
	4. 查询缓存（一级缓存、二级缓存）
	6. mybatis逆向工程
	5. spring整合mybatis
	


mybatis框架架构：
----------------------------------------------------------------------
						  SqlMapConfig.xml 
						   多个mapper1.xml
								|
								|
								|/
					SqlSessionFactory（创建SqlSession）
								|
								|
								|/
						SqlSession（操作数据库CURD）
					在SqlSession实现类中除了有操作DB的方法，
					还有数据域的属性因此它是线程不安全的！！
					因此SqlSession的应用场合是在方法体内，
					即将SqlSession定义成局部变量使用。
								|
	  输入参数					|						 输出结果
		 \						|					 	  /
		  \						|/						 /
		   \|		Executor（基本执行器、缓存执行器）		/
			MappedStatement（封装的了操作数据库的一些信息，包括sql、输入参数、输出类型）	
								|
								|
								|/
								DB
----------------------------------------------------------------------
				

				
				
				

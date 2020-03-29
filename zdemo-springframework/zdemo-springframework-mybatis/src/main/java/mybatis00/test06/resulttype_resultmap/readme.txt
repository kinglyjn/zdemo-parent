
非延迟加载的情况（使用迫切左外连接进行查询）：
	select 
		o.*, 
		u.* 
	from 
		t_order o
	left join t_user u on u.id=o.user_id
	



延迟加载的原理
	使用子查询进行延迟加载，只有真正使用延迟加载的属性时才进行一个子查询的关联查：
	select 
		o.*,
		(select u.id from t_user u where u.id=o.user_id) u_id,
		(select u.username from t_user u where u.id=o.user_id) u_username
	from t_order o;
	
	
配置方法：
	1. 在mybatis全局配置文件中开启延迟加载的全局参数：
		<!-- 
			全局参数（将可能影响mybatis的运行行为）
			lazyLoadingEnabled（true）：开启全局的延迟加载
			aggressiveLazyLoading（false）：对延迟加载属性不进行积极加载
			
			注意：
			由于mybatis首先要加载全局配置参数而后运行时加载mapper映射文件，
			因此全局配置参数的优先级比mapper映射文件的低，如果在mapper映射
			文件中配置了fetchType="lazy"属性，无论全局参数的延迟加载如何配置，
			mybatis都会对相应的属性进行懒加载。
		-->
		<settings>
			<setting name="lazyLoadingEnabled" value="true"/>
			<setting name="aggressiveLazyLoading" value="false"/>
		</settings>
		
	2. 在mapper映射文件中按以下形式进行输入和输出映射：
		
		<resultMap id="findAll1UserResultMap" type="mybatis07.mapper_lazyload.Order">
			<id column="id" property="id"/>
			<result column="create_time" property="createTime"/>
			<result column="note" property="note"/>
			<result column="number" property="number"/>
			
			<!-- 映射关系属性（使用延迟加载） -->
			<!-- 
				select 
					o.*,
					(select u.id from t_user u where u.id=o.user_id) u_id,
					(select u.username from t_user u where u.id=o.user_id) u_username
				from t_order o;
				
				association#select 表示上面查询中的子查询
				association#column 就表示上面查询条件中的 o.user_id
			 -->
			<association property="user" javaType="mybatis07.mapper_lazyload.User" 
				select="mybatis07.mapper_lazyload.UserMapper.findById" column="user_id"/>
		</resultMap>
	
	   【注意】如果在在mybatis全局配置文件中开启了延迟加载的全局参数，而在配置输入和输出映射时配置了fetchType="eager"，
	   		那么 mybatis会向数据库发送多条查询sql 而不是像hibernate一样使用迫切左外连接的方式将延迟加载属性一次性查询
	   		出来，所以在延迟加载这点上mybatis不如hibernate做的好。
			mybatis的延迟加载没有使用代理去创建输出映射对象，而hibernate会为延迟加载的输出对象创建代理（所以hibernate
			可能会出现延迟加载异常，而mybatis不会）。
			
			
			
	
	
	


### 设计理念

`将SQL交给开发人员，将预编译、参数设置、sql执行、封装结果等步骤交给框架。`

<br>



### 传统使用方式简单示例

1、必要的依赖包

```xml
<dependencies>
	<!-- mybatis -->
	<dependency>
		<groupId>org.mybatis</groupId>
		<artifactId>mybatis</artifactId>
		<version>3.4.1</version>
	</dependency>
	<!-- mysql -->
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<version>5.1.39</version>
	</dependency>
	<!-- logs 日志 -->
	<dependency>
		<groupId>org.slf4j</groupId>
		<artifactId>slf4j-api</artifactId>
		<version>1.7.24</version>
	</dependency>
	<dependency>
		<groupId>org.slf4j</groupId>
		<artifactId>slf4j-log4j12</artifactId>
		<version>1.7.24</version>
	</dependency>
	<dependency>
		<groupId>log4j</groupId>
		<artifactId>log4j</artifactId>
		<version>1.2.17</version>
	</dependency>
</dependencies>
```

2、代码结构 和 库表准备

```default
/*代码结构*/
test01.sql_session_factory
    |--Emp.java
    |--Test01.java
    |--mybatis-test01-config.xml
    |--mybatis-test01-mapper.xml
    

/*创建数据库*/
create database if not exists test;
/*创建员工表*/
create table if not exists test.t_emp(
	id int not null primary key auto_increment,
	last_name varchar(50),
	email varchar(50),
	gender varchar(10)
);
```

3、mybatis-test01-config.xml（全局配置文件）

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
  
<configuration>
	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<property name="driver" value="com.mysql.jdbc.Driver" />
				<property name="url" value="jdbc:mysql://192.168.1.96:3306/test" />
				<property name="username" value="xxx" />
				<property name="password" value="xxx" />
			</dataSource>
		</environment>
	</environments>
	<mappers>
		<mapper resource="test01/sql_session_factory/mybatis-test01-mapper.xml" />
	</mappers>
</configuration>
```

4、mybatis-test01-mapper.xml（SQL映射文件）

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 
	namespace: 名称空间（此处暂时名字任意）
	resultType: 返回数据的类型
	#{xxx}: 传递过来的参数值
-->
<mapper namespace="test01.sql_session_factory">
	<select id="selectEmp" resultType="test01.sql_session_factory.Emp">
		select id, last_name as lastName, email, gender from t_emp where id = #{id}
	</select>
</mapper>
```

5、Emp.java

```java
public class Emp {
	private Integer id;
	private String lastName;
	private String email;
	private String gender;
    // geter/setter ...
}
```

6、Test01.java

```java
/*
 SqlSession对象代表和数据库的一次会话，用完之后必须关闭。
 SqlSession和connection一样都是非线程安全的，每次使用都应该获取新的sqlsession对象。
*/
@Test
public void createSqlSessionFactoryWithXML() throws IOException {
  // 创建 SqlSessionFactory
  String resource = "test01/sql_session_factory/mybatis-test01-config.xml";
  InputStream inputStream = Resources.getResourceAsStream(resource);
  SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
  System.err.println(sqlSessionFactory);

  SqlSession session = null;
  try {
    // 获取 SqlSession
    session = sqlSessionFactory.openSession();
    Emp emp = session.selectOne("selectEmp", 1);
    System.err.println(emp);
  } finally {
    session.close();
  }
}

@Test
public void createSqlSessionFactoryWithoutXML() {
  // 数据源
  Properties properties = new Properties();
  properties.setProperty("driver", "com.mysql.jdbc.Driver");
  properties.setProperty("url", "jdbc:mysql://192.168.1.96:3306/test");
  properties.setProperty("username", "xxx");
  properties.setProperty("password", "xxx");
  PooledDataSourceFactory pooledDataSourceFactory = new PooledDataSourceFactory();
  pooledDataSourceFactory.setProperties(properties);
  DataSource dataSource = pooledDataSourceFactory.getDataSource();
  // 事务
  TransactionFactory transactionFactory = new JdbcTransactionFactory();
  //
  Environment environment = new Environment("development", 
                                            transactionFactory, dataSource);
  Configuration configuration = new Configuration(environment);
  configuration.addMapper(Mapper.class);
  SqlSessionFactory sqlSessionFactory = 
    new SqlSessionFactoryBuilder().build(configuration);
  System.err.println(sqlSessionFactory);
}
```

<br>



### 使用接口方式(推荐)的简单示例

1、在方式一的基础上增加 EmpMapper 接口

```java
// 接口式编程，将DAO层的定义和实现解耦
public interface EmpMapper {
	Emp getEmpById(Integer id);
}
```

2、mapper映射文件绑定 Mapper接口

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--
1、命名空间namespace必须与Mapper接口全类名相同
2、sql语句id必须与Mapper接口的方法名相同
说明：当使用package批量注册SQL映射文件时，则要求mapper映射
     文件(如果有)和mapper接口必须在同一个包下，并且名称相同
-->
<mapper namespace="test02.sql_session_factory.EmpMapper">
	<select id="getEmpById" resultType="test02.sql_session_factory.Emp">
		select id, last_name as lastName, email, gender from t_emp where id = #{id}
	</select>
</mapper>
```

3、测试类

```java
@Test
public void createSqlSessionFactoryWithXML() throws IOException {
  // 创建 SqlSessionFactory
  String resource = "test02/sql_session_factory/mybatis-test02-config.xml";
  InputStream inputStream = Resources.getResourceAsStream(resource);
  SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
  System.err.println(sqlSessionFactory);

  // 通过 session 获取 mapper
  SqlSession session = null;
  try {
    session = sqlSessionFactory.openSession();
    EmpMapper empMapper = session.getMapper(EmpMapper.class);
    Emp emp = empMapper.getEmpById(1);
    System.err.println(empMapper); // xxx.MapperProxy@87f383f
    System.err.println(emp);
  } finally {
    session.close();
  }
}
```

<br>



### 使用注解映射SQL（代码和SQL耦合，不推荐使用）

1、编写mapper接口

```java
public interface EmpAnnotationMapper {
	@Select("select * from t_emp where id=#{id}")
	Emp getEmpById(Integer id);
}
```

2、引入SQL映射接口

```xml
<mappers>
  <mapper class="test03.configuration.EmpAnnotationMapper"/>
</mappers>
```

3、测试（略）

<br>



### 全局配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
  
<configuration>
	<!-- 
	 properties: 
	 properties标签引入外部properties配置文件中的内容（一般的spring项目会把配置工作交给spring来做）
	 resource: 引入外部properties配置文件
	 url: 引入网络或磁盘路径下的properties配置文件
	 -->
	<properties resource="test03/configuration/jdbc.properties">
		<!-- peoperty ... -->
	</properties>


	<!-- 
	 settings:
	 包含了众多极为重要的配置项
	-->
	<settings>
		<!-- 参照: http://www.mybatis.org/mybatis-3/zh/configuration.html -->
		<setting name="mapUnderscoreToCamelCase" value="true"/>
	</settings>
	
	
	<!-- 
	 tyleAliases:
	 别名处理器，可以为java类型起别名
	 typeAlias: 为java类型起别名，type指定全类型，alias指定别名（如果不指定则默认为类名首字母小写）
	 package: 为某个包及其下的子包的所有类批量起别名（默认别名为类名首字母小写，实际上别名不区分大小写）
	 注意：如果一个包下的子包中也有相同别名的类，可以使用 @Alias 注解为类指定新的别名以避免冲突
	 建议：写全类名以方便开发
	 参考：常见的Java类型内建的相应的类型别名参照 
          http://www.mybatis.org/mybatis-3/zh/configuration.html
	-->
	<typeAliases>
		<!-- <typeAlias type="test03.configuration.Emp"/> -->
		<package name="test03.configuration"/>
	</typeAliases>
	
	
	<!-- 
	 typeHandlers:
	 无论是 MyBatis 在预处理语句（PreparedStatement）中设置一个参数时，还是从结果集中取出一个值时， 
	 都会用类型处理器将获取的值以合适的方式转换成 Java 类型。mybatis定义了一些默认的类型处理器。另外
	 你可以重写类型处理器或创建你自己的类型处理器来处理不支持的或非标准的类型。
	 具体做法为: 实现 org.apache.ibatis.type.TypeHandler 接口，或继承一个很便利的类 
	 org.apache.ibatis.type.BaseTypeHandler，然后可以选择性地将它映射到一个 JDBC 类型。
	 -->
	<typeHandlers>
		<!-- typeHandler ... -->
	</typeHandlers>
	

	<!-- 
	 plugins:
	 MyBatis允许你在已映射语句执行过程中的某一点进行拦截调用（实际还是代理）。
	 默认情况下，MyBatis允许使用插件来拦截的方法调用包括：
	 Executor (update, query, flushStatements, commit, rollback, 
               getTransaction, close, isClosed)
	 ParameterHandler (getParameterObject, setParameters)
	 ResultSetHandler (handleResultSets, handleOutputParameters)
	 StatementHandler (prepare, parameterize, batch, update, query)
	 使用插件是非常简单的，只需实现 Interceptor 接口，并指定想要拦截的方法签名即可。
     使用插件需要很小心，因为如果在试图修改或重写已有方法的行为的时候，你很可能会修改MyBatis的核心模块。
	 -->
	 <!-- 
	 <plugins>
	 	<plugin interceptor="org.mybatis.example.ExamplePlugin">
	 		<property name="someProperty" value="100"/>
	 	</plugin>
	 </plugins>
	 -->


	<!-- 
	 environments:
	 1、可以配置多个environment，但创建SessionFactory的时候只能选择其一。
	 2、每个environment标签下必须包含 transactionManager 和 dataSource 两个子标签。
	 3、事务管理器transactionManager的可选值有 JDBC|MANAGED。
	    JDBC: 这个配置就是直接使用了 JDBC 的提交和回滚设置，它依赖于从数据源得到的连接来管理事务作用域。
	    MANAGED: 这个配置几乎没做什么。它从来不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（
	    比如 JEE 应用服务器的上下文）。默认情况下它会关闭连接，然而一些容器并不希望这样，因此需要使用其下
	    property子标签将closeConnection 属性设置为false来阻止它默认的关闭行为。
	    其实这两个可选值它们是类型别名（参照 org.apache.ibatis.session.Configuration），换句话说，
	    通过实现 TransactionFactory接口 可以自定义事务，
        并使用实现类的完全限定名或类型别名代替 JDBC|MANAGED。
	    注意：如果你正在使用 Spring + MyBatis，则没有必要配置事务管理器，
	         因为Spring模块会使用自带的管理器来覆盖前面的配置。
	 4、数据源dataSource的可选值有 UNPOOLED|POOLED|JNDI。
	    通过实现 DataSourceFactory接口 可以设置自定义的数据源（eg. c3p0、druid等）
	-->
	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<property name="driver" value="${jdbc.driver}" />
				<property name="url" value="${jdbc.url}" />
				<property name="username" value="${jdbc.username}" />
				<property name="password" value="${jdbc.password}" />
			</dataSource>
		</environment>
	</environments>
	
	
	<!-- 
	databaseIdProvider:
	Mybatis对多数据库厂商的支持，方法如下：
	1、定义数据库厂商标识的别名：多数据库厂商支持，作用就是根据数据库厂商的标识。驱动包实现方法
	  DatabaseMetaData#getDatabaseProductName可以获取厂商标识。由于通常情况下这个字符串
	  都非常长而且相同产品的不同版本会返回不同的值，所以最好通过设置属性别名来使其变短。
	2、设置具体sql语句相关标签的databaseId属性为当前数据库厂商标识别名，如：
	  <select id="getEmpById" resultType="emp" databaseId="mysql">xxx<select>
	3、配置相应数据库的 environment，并设置为默认环境。
	-->
	<databaseIdProvider type="DB_VENDOR">
		<property name="MySQL" value="mysql"/>
	  	<property name="Oracle" value="oracle" />
		<property name="SQL Server" value="sqlserver"/>
	  	<property name="DB2" value="db2"/>    
	</databaseIdProvider> 
	
	
	<!-- 
	 mappers:
	 1、使用映射文件映射SQL(推荐)
	 将SQL映射注册到全局配置中。
	 resource：引用类路径下的sql映射文件
	 url：引用网络路径下的sql映射文件
	 package：用于批量注册，要求mapper映射文件(如果有)和mapper接口必须在同一个包下，并且名称相同。
	 class：a、引用mapper接口，如果使用class注册mapper接口，那么mapper
	          映射文件必须和mapper接口在同一个目录下，并且具有相同的名称。
	        b、class标签也可以直接注册使用注解映射SQL的mapper接口。
	 
	 2、使用注解映射SQL
	 在这种情况下，所有的SQL都是使用注解写在接口上，并且使用class标签将接口注册进来。
	-->
	<mappers>
		<package name="test03.configuration"/>
		<!-- 
		<mapper resource="test03/configuration/EmpMapper.xml" />
		<mapper class="test03.configuration.EmpAnnotationMapper"/>
		-->
	</mappers>
</configuration>
```

<br>



### 简单的增查改删

1、定义mapper接口

```java
public interface EmpMapper {
	void addEmp(Emp emp);
  	void addEmp2(Emp emp);
  	void addEmp3(Emp emp);
	Emp getEmpById(Integer id);
	void updateEmp(Emp emp);
	void deleteEmp(Integer id);
  	itn deleteEmp2(Integer id);
}
```

2、编写SQL映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="test04.mapper.crud.EmpMapper">
  	<!-- mybatis底层调用Statement#getGeneratedKeys 获取生成的主键并赋值给封装对象的某个属性 -->
	<insert id="addEmp" databaseId="mysql" useGeneratedKeys="true" keyProperty="id">
		insert into t_emp(last_name,email,gender) values(#{lastName},#{email},#{gender})
	</insert>
    <!-- 使用 selectKey 标签在sql执行前后查询主键并封装到目标对象的某个属性 -->
	<insert id="addEmp2" databaseId="oracle">
		<selectKey keyProperty="id" resultType="integer" order="BEFORE">
			select emp_seq.nextval from dual
		</selectKey>
		insert into t_emp(id,last_name,email,gender) 
      		values(#{id},#{lastName},#{email},#{gender})
	</insert>
	<insert id="addEmp3" databaseId="oracle">
		<selectKey keyProperty="id" resultType="integer" order="AFTER">
			select emp_seq.currentval from dual
		</selectKey>
		insert into t_emp(id,last_name,email,gender) 
      		values(emp_seq.nextval,#{lastName},#{email},#{gender})
	</insert>
	<select id="getEmpById" resultType="emp" databaseId="mysql">
		select * from t_emp where id = #{id}
	</select>
	<update id="updateEmp">
		update t_emp set last_name=#{lastName},email=#{email},gender=#{gender} 
        where id=#{id}
	</update>
	<delete id="deleteEmp">
		delete from t_emp where id=#{id}
	</delete>
  	<delete id="deleteEmp2">
		delete from t_emp where id=#{id}
	</delete>
</mapper>
```

3、在全局配置文件中批量注册mapper

```xml
<mappers>
  <package name="test04.mapper.crud"/>
</mappers>
```

4、测试

```java
public SqlSessionFactory getSqlSessionFactory() throws IOException {
  String resource = "test04/mapper/crud/mybatis-test04-config.xml";
  InputStream inputStream = Resources.getResourceAsStream(resource);
  return new SqlSessionFactoryBuilder().build(inputStream);
}

@Test
public void addEmp() {// addEmp2 addEmp3 同理
  SqlSession session = null;
  try {
    SqlSessionFactory sf = getSqlSessionFactory();
    session = sf.openSession(); // autoCommit=false
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    Emp emp = new Emp("wangwu", "wangwu@keyllo.com", "1");
    mapper.addEmp(emp);
    System.out.println("插入后的员工对象（含有生成的主键）: " + emp);
  } catch (Exception e) {
    e.printStackTrace();
  } finally {
    session.commit();
    session.close();
  }
}

@Test
public void getEmp() {
  SqlSession session = null;
  try {
    SqlSessionFactory sf = getSqlSessionFactory();
    session = sf.openSession();
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    Emp emp = mapper.getEmpById(1);
    System.out.println(emp);
  } catch (Exception e) {
    e.printStackTrace();
  } finally {
    session.close();
  }
}

@Test
public void updateEmp() {
  SqlSession session = null;
  try {
    session = getSqlSessionFactory().openSession();
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    Emp emp = new Emp("wangwu2", "wangwu2@keyllo.com", "1");
    emp.setId(4);
    mapper.updateEmp(emp);
  } catch (IOException e) {
    e.printStackTrace();
  } finally {
    session.commit();
    session.close();
  }
}

@Test
public void deleteEmp() { // deleteEmp2 同理
  SqlSession session = null;
  try {
    session = getSqlSessionFactory().openSession();
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    mapper.deleteEmp(4);
  } catch (IOException e) {
    e.printStackTrace();
  } finally {
    session.commit();
    session.close();
  }
}
```

<br>



### Mybatis对映射参数的处理(ParamNameResolver)

```default
1、单个参数
名称任意，直接 #{xxx}

2、多个参数
多个参数会被封装成一个map，map的key为 param1..paramN，map的value为传入的参数值，
所以在映射sql取值的时候，应当使用 #{param1},..,#{paramN} 或者 #{0},..,#{N-1} 的方式

3、多个参数使用命名参数(推荐)
首先定义mapper接口的时候，使用 @Param 注解定义参数的名称，然后就可以在mapper映射文件中使用自定义的参数名
a. Emp findEmpByIdAndName(@Param("id") Integer id, @Param("lastName") String lastName);
b. select * from t_emp where id=#{id} and last_name=#{lastName}

4、多个参数是POJO的属性时可以使用POJO传递参数，取值时采用 #{属性名} 方式
有时为方便会专门封装TO（Transfer Object）对象用于传递参数(eg. Page)
a. void updateEmp(Emp emp);
b. update t_emp set last_name=#{lastName},email=#{email},gender=#{gender} where id=#{id}

5、多个参数没有对应POJO时也可以使用map进行参数传递，取值时采用 #{map的key} 方式
a. Emp findEmpByIdAndName(Map map);
b. select * from t_emp where id=#{id} and last_name=#{lastName}

6、如果参数是集合或数组，Mybatis也会把集合或数组封装在map中，map的key即为集合或者数组对象。
如果是List类型的参数还可以使用 "list" 作为取值的键，如果是数组则还可以使用 "array" 作为取值的键
List<Emp> findEmpByIds1(Set<Integer> ids);
<select id="findEmpByIds1" resultType="emp">
  select * from t_emp where id in
  <foreach collection="collection" item="id" open="(" close=")" separator=",">
      #{id}
  </foreach>
</select>

Emp findEmpByIds2(List<Integer> ids);
<select id="findEmpByIds2" resultType="emp">
	select * from t_emp where id=#{list[0]}
</select>

Emp findEmpByIds3(Integer[] ids);
<select id="findEmpByIds3" resultType="emp">
	select * from t_emp where id=#{array[0]}
</select>


一些示例：
-------
Emp getEmp(@Param("id")Integer id, String lastName);
取值：id=#{id/param1}  last_name=#{param2}

Emp getEmp(Integer id, @Param("emp")Emp emp);
取值：id=#{param1}  last_name=#{param2.lastName/emp.lastName}
```

<br>



### mybatis两个内置映射参数

```default
_parameter: 代表整个参数。如果是多个参数，则多个参数会被封装成为一个map，_parameter就代表这个map。
_databaseId: 如果配置了databaseProvider标签，则_databaseId就是代表当前数据库的别名。

<select id="getEmpsByParameterAndDatabaseId" resultType="test06.mapper.dynamic_sql.Emp">
	<if test="_databaseId=='mysql'">
		select * from t_emp 
		<if test="_parameter!=null">
			where last_name=#{lastName}
		</if>
	</if>
	<if test="_databaseId=='oracle'">
		select * from t_emp 
	</if>
</select>
```

<br>



### #{}取值和${}取值方式的区别

```default
不同点：
#{} 相当于一个占位符，以预编译的形式将参数设置到sql语句中，可防止SQL注入的问题
${} 用于字符拼接，是直接将传递的参数拼装在sql语句中

用法：
a. 大多情况下我们使用 #{} 进行参数传递
b. 如果需要动态查询则需要使用 ${} 进行取值( 如 select * from ${} )

其他说明：
#{}更丰富的用法(规定参数的一些规则)：
  javaType：java类型
  jdbcType：jdbc类型(参考 JDBCType枚举)，通常需要在特定情况下设置，
            因为有些数据库(oracle)不能识别 mybatis 对null的处理，
            默认情况下 mybatis 将null值作为 JDBCType#OTHER类型
            进行处理(全局配置 jdbcTypeForNull=OTHER)，要想在
            oracle中正常使用，可以设置参数时 #{xxx,jdbcType=NULL}
  mode：用于存储过程
  numericScale：数字类型小数位数
  resultMap：将返回数据封装成的复杂数据类型
  typeHandler：类型处理器
  jdbcTypeName
  expression
```

<br>



### mybatis对返回值的处理

```default
########################
resultType: 常规结果集封装
########################

1、返回集合list，resultType则为返回list元素的类型
<select id="findEmpByIds1" resultType="emp">
    select * from t_emp where lsit_name like #{lastName}
</select>

2、返回集合map（键为数据库字段类型，值为数据库字段值），resultType 则为 map
Map<String,Object> getEmpByIdReturnMap(Integer id);
<select id="getEmpByIdReturnMap" resultType="map">
	select * from t_emp where id=#{id}
</select>

3、返回集合map（键为数据库表主键值，值为主键对应记录），则
@MapKey("id") //告诉mybatis封装map的时候将封装对象的哪个属性作为key
Map<Integer,Emp> getEmpByIdReturnMap2();
<select id="getEmpByIdReturnMap2" resultType="emp">
	select * from t_emp;
</select>



##########################
resultMap: 自定义结果集封装
##########################

案例1、查询emp和它关联的dept
Emp getEmpById(Integer id);
<resultMap type="test05.mapper.resultmap.Emp" id="MyEmp">
	<!-- 主键映射规则 -->
	<id column="id" property="id"/>
	<!-- 普通属性映射规则 -->
	<result column="last_name" property="lastName"/>
	<result column="email" property="email"/>	
	<result column="gender" property="gender"/>	
	
	<!-- 级联属性映射规则 -->
	<result column="did" property="dept.id"/>	
	<result column="dept_name" property="dept.deptName"/>	
	<!--或者也可以按照以下方式映射-->
	<!--
	<association property="dept" javaType="test05.mapper.resultmap.Dept">
    	<id column="did" property="id"/>
    	<result column="dept_name" property="deptName"/>
    </association>
	-->
</resultMap>
<select id="getEmpById" resultMap="MyEmp">
	select e.*, d.id as did,d.dept_name from t_emp e left join t_dept d 
	on e.dept_id=d.id where e.id=#{id}
</select> 
```

<br>



### 分步查询和懒加载

```default
说明：
使用分布查询可以实现与案例1同样的功能，更好的是你还可以使用分步查询的延迟加载功能！
如果使用分布查询的延迟加载功能，只需要在全局设置中增加如下两个配置：
<setting name="lazyLoadingEnabled" value="true"/>     //开启延迟加载
<setting name="aggressiveLazyLoading" value="false"/> //延迟加载属性按需查询
懒加载时，只有用到主数据时，才会查询关联数据，那么这个 “用到” 是什么意思？
eager: query主数据时，会立刻query关联数据，这两个query使用的同一个jdbc connection
lazy: query主数据库时，不会query关联数据，只有在调用主数据toString方法或获取关联属性时，
才会query获取数据，这两个query使用不同connection


案例1、分步查询emp的属性dept(先根据id查询emp，再根据emp.dept_id查询dept)
Dept getDeptById(Integer id);
<select id="getDeptById" resultType="test05.mapper.resultmap.Dept">
	select id,dept_name as deptName from t_dept where id=#{id}
</select>

Emp getEmpById(Integer id);
<resultMap type="test05.mapper.resultmap.Emp" id="MyEmp">
	<!-- 主键映射规则 -->
	<id column="id" property="id"/>
	<!-- 普通属性映射规则 -->
	<result column="last_name" property="lastName"/>
	<result column="email" property="email"/>	
	<result column="gender" property="gender"/>
	<!-- 级联属性映射规则 -->
	<association property="dept"
		select="test05.mapper.resultmap.DeptMapper.getDeptById" 
		column="dept_id" fetchType="lazy"/>
</resultMap>
<select id="getEmpById" resultMap="MyEmp">
	select * from t_emp where id=#{id}
</select>


案例2、分步查询dept的属性emps(先根据id查询dept，再根据dept.id查询emps)
Dept getDeptByIdWithEmps(Integer id);
<resultMap type="test05.mapper.resultmap.Dept" id="MyDept">
	<id column="id" property="id"/>
	<result column="dept_name" property="deptName"/>
	<collection property="emps" 
		select="test05.mapper.resultmap.EmpMapper.getEmpsByDeptId" 
		column="id" fetchType="eager"/>
</resultMap>
<select id="getDeptByIdWithEmps" resultMap="MyDept">
	select * from t_dept where id=#{id}
</select>

List<Emp> getEmpsByDeptId(Integer deptId);
<select id="getEmpsByDeptId" resultType="test05.mapper.resultmap.Emp">
	select id,last_name as lastName,email,gender from t_emp where dept_id=#{deptId}
</select>


说明：
当关联字段是多个需要传递时怎么办？
可以将column的值封装成一个map，map的key为 EmpMapper#getEmpsByDeptId的 #{deptId} 中的名字，
map的值为 DeptMapper#getDeptByIdWithEmps 查询出的t_dept表字段 id。如果还有其他字段需要传递，
则只需按照同样的规则以 "," 分隔即可。
<collection property="emps" 
	select="test05.mapper.resultmap.EmpMapper.getEmpsByDeptId" 
	column="{deptId=id}" fetchType="eager"/>
```

<br>



### ResutMap鉴别器

```default
List<Emp> getAllEmps();
<resultMap type="test05.mapper.resultmap.Emp" id="MyEmp2">
	<id column="id" property="id"/>
	<result column="last_name" property="lastName"/>
	<result column="email" property="email"/>
	<result column="gender" property="gender"/>
	<!-- 
	鉴别器： 
	当查询的员工是女生时，查询并封装dept属性
	当查询的员工是男生时，不查询dept属性，并且将查询的 email赋值给last_name	
	-->
	<discriminator column="gender" javaType="string">
		<!-- 女生 -->
		<case value="0" resultType="test05.mapper.resultmap.Emp">
			<association property="dept"
            	select="test05.mapper.resultmap.DeptMapper.getDeptById" 
            	column="dept_id"/>
		</case>
		<!-- 男生 -->
		<case value="1" resultType="test05.mapper.resultmap.Emp">
			<result column="email" property="lastName"/>
		</case>
	</discriminator>
</resultMap>
```

 <br>



### 动态SQL示例

```xml
<!-- 条件判断 -->
<select id="getEmpsByConditionIf1" resultType="test06.mapper.dynamic_sql.Emp">
	select * from t_emp 
	<where>
		<!-- OGNL表达式 -->
		<if test="id!=null"> 
			id=#{id}
		</if> 
		<if test="lastName!=null and lastName!=''"> 
			and last_name like #{lastName} 
		</if>
		<if test="email!=null and email.trim()!=&quot;&quot;"> 
			and email like #{email} 
		</if>
		<if test="gender==0 or gender==1">
			and gender=#{gender}
		</if>
	</where>
</select>
<select id="getEmpsByConditionIf2" resultType="test06.mapper.dynamic_sql.Emp">
	select * from t_emp 
	<trim prefix="where" suffixOverrides="and">
		<!-- OGNL表达式 -->
		<if test="id!=null"> 
			id=#{id} and
		</if> 
		<if test="lastName!=null and lastName!=''"> 
			last_name like #{lastName} and
		</if>
		<if test="email!=null and email.trim()!=&quot;&quot;"> 
			email like #{email} and 
		</if>
		<if test="gender==0 or gender==1">
			gender=#{gender}
		</if>
	</trim>
</select>


<!-- 分支选择 -->
<select id="getEmpsByConditionChoose1" resultType="test06.mapper.dynamic_sql.Emp">
	select * from t_emp 
	<where>
		<choose>
			<when test="lastName!=null">
				last_name=#{lastName}
			</when>
			<when test="email!=null">
				email=#{email}
			</when>
			<otherwise>
				1=0
			</otherwise>
		</choose>
	</where>
</select>


<!-- 更新标签 -->
<update id="updateEmp">
	update t_emp 
	<set> 
		<if test="lastName!=null">
			last_name=#{lastName},
		</if>
		<if test="email!=null">
			email=#{email},
		</if>
		<if test="gender==1 or gender==0">
			gender=#{gender}
		</if>
	</set>
	where id=#{id}
</update>


<!-- 遍历标签 -->
<!-- List<Emp> getEmpsByConditionForeach(@Param("ids") List<Integer> ids); -->
<select id="getEmpsByConditionForeach" resultType="test06.mapper.dynamic_sql.Emp">
	select * from t_emp where id in
	<!-- index：遍历list的时候是索引，遍历map的时候index是map.key，item是遍历的map的值 -->
	<foreach collection="ids" item="item" index="index" separator="," open="(" close=")">
		#{item}
	</foreach>
</select>

<!-- void addEmps(@Param("emps") List<Emp> emps); -->
<!--
| id | last_name | email               | gender | dept_id |
| 10 | tianqi    | tianqi@keyllo.com   | 1      |    NULL |
| 11 | zhouba    | zhouba@keyllo.com   | 1      |    NULL |
-->
<insert id="addEmps" useGeneratedKeys="true" keyProperty="id">
	insert into t_emp(last_name, email, gender, dept_id)
	values
	<foreach collection="list" item="emp" index="index" separator=",">
		(#{emp.lastName}, #{emp.email}, #{emp.gender}, #{emp.dept.id})
	</foreach>
</insert>

<!-- Oracle批量插入方式一 -->
<insert id="addEmps2" databaseId="oracle">
	<foreach collection="list" item="emp" open="begin" close="end;">
		insert into t_emp(id,last_name,email) 
      		values(emp_seq.nextval, #{emp.lastName}, #{emp.email});
	</foreach>
</insert>
<!-- Oracle批量插入方式二 -->
<insert id="addEmps3" databaseId="oracle">
	insert into t_emp(id,last_name,email)
		select emp.nextval,last_name,email from
		<foreach collection="list" item="emp" separator="union" open="(" close=")">
			select #{emp.lastName} as last_name, #{emp.email} as email from dual
		</foreach>
</insert>


<!-- 将OGNL表达式中的值绑定到一个变量中，方便后面引用这个变量的值 -->
<select id="getEmpsByBind" resultType="test06.mapper.dynamic_sql.Emp">
	<bind name="_lastName" value="'%'+ lastName +'%'"/> 
	select * from t_emp where last_name like #{_lastName} 
</select>


<!-- 
	使用sql标签抽取可重用的sql标签，方便引用
	在sql标签中使用include中自定义的属性，取值方式是 ${xxx}
-->
<sql id="selectColumns">
	<if test="_databaseId=='mysql'">
		id,last_name,email,gender,${testProperty}
	</if>
	<if test="_databaseId=='oracle'">
		id,last_name,email,gender
	</if>
</sql>
<select id="getEmpsByBind" resultType="test06.mapper.dynamic_sql.Emp">
	<bind name="_lastName" value="'%'+ lastName +'%'"/>
	select 
	<include refid="selectColumns">
		<property name="testProperty" value="dept_id"/>
	</include> 
	from t_emp where last_name like #{_lastName}
</select>
```

<br>



### mysql 一级缓存和二级缓存

```default
Mybatis的设计分为两级缓存：
一级缓存：也叫本地缓存或session缓存，是指与数据库同一次会话期间查询到的数据会放入一级缓存中，以后查询相同
		的数据直接从缓存中取，没必要再从数据库中查询得到。一级缓存是一直开启的（session缓存没法关闭）。
        1、不同session的缓存不共用
        2、同一个session，两次查询之间插入、更新或删除了数据，则session缓存失效
        3、同一个session，调用 session#clearCache(清1级缓存) 或 session#commit，session缓存失效
           
二级缓存：也叫全局缓存或namespace缓存，一个namespace对应一个二级缓存。
        
        /**工作机制：*/
        1、session查询一条记录，这条记录就会被放在一级缓存中；
        2、如果当前会话关闭，则以及缓存中的数据就会被保存在二级缓存中，新的会话就会参照二级缓存查询数据；
        3、同一个session使用不同namespace mapper 查询的的记录会放在不同的namespace#map缓存中
		
		client---->CachingExcutor---->Excutor---->db
		                |               |
		           SecondryCache      LocalCache
		           
		
		/**使用方法：*/
		1、开启全局二级缓存配置项：
		<setting name="cacheEnabled" value="true"/>
		2、在每个 namespace mapper 中配置使用二级缓存
		<cache></cache>
		eviction: 缓存的回收策略，LRU(默认)、FIFO、SOFT、WEAK
		flushInterval: 缓存多长时间(ms)清空一次(默认不清空)
		readOnly: 默认为true。如果是只读为true，mybatis认为所有从缓存中获取的数据的操作都是只读操作，
				  不会修改数据，查询直接就会将数据在缓存中交给用户，速度快但不安全；如果只读为false，
				  mybatis就会利用序列化和反序列化技术克隆一份新的数据给你。速度稍慢但安全。
		size: 缓存大小，默认为1024
		type: 指定自定义缓存的全类名(实现 org.apache.ibatis.cache.Cache 接口)
		3、PO需要实现序列化接口  
		   
		   
        /**缓存相关标签：*/
        <!--查询是否使用二级缓存-->
        <select id="getEmpById" resultType="emp" useCache="false">...</select>
        <!-- 
        	每次增删改操作清除全部一级和二级缓存，
        	select标签默认为false，增删改默认为true 
        	缓存的定时刷新可以通过 cache标签的flushInterval设置
        -->
        <insert id="addEmp" flushCache="true">...</insert> 
		
		最后，请注意：
		1、查出的数据会先放在一级缓存中，只有当会话提交或关闭之后，一级缓存中的数据才会转移到二级缓存中。
		2、我们在实际开发中应该尽量避免使用文集缓存，因为二级缓存是基于namespace的，尤其当同一个mapper
		   可能还要操作其他表的数据，当使用其他mapper查询时很可能会造成其缓存和数据库数据不一致的问题！
		3、最后还是建议，放弃二级缓存，在业务层使用可控制的缓存代替更好。
		

使用Ehcache充当mybatis的二级缓存：
请参考 http://www.mybatis.org/ehcache-cache/
	1、导入jar包
	   <dependency>
			<groupId>net.sf.ehcache</groupId>
			<artifactId>ehcache-core</artifactId>
			<version>2.6.9</version>
		</dependency>
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis-ehcache</artifactId>
			<version>1.1.0</version>
		</dependency>
	2、classpath:ehcache.xml
        <defaultCache
          maxEntriesLocalHeap="1000" 
          eternal="false"
          timeToLiveSeconds="3600"
          timeToIdleSeconds="3600"
          overflowToDisk="false">
       </defaultCache>
	3、全局配置项
	  <setting name="cacheEnabled" value="true"/>
	4、namespace mapper 配置项
	  EmpMapper.xml
	  <cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
        <property name="timeToIdleSeconds" value="3600"/><!--1 hour-->
        <property name="timeToLiveSeconds" value="3600"/><!--1 hour-->
        <property name="maxEntriesLocalHeap" value="1000"/>
        <property name="maxEntriesLocalDisk" value="10000000"/>
        <property name="memoryStoreEvictionPolicy" value="LRU"/>
      </cache>
      或者
      DeptMapper.xml
      <cache-ref namespace="test07.cache.EmpMapper"/>
```

<br>



### mybatis 与 spring 整合

1、依赖包

```xml
<!--mybatis-->
<dependency>
	<groupId>org.mybatis</groupId>
	<artifactId>mybatis</artifactId>
	<version>3.4.1</version>
</dependency>
<!--mybatis-spring-->
<dependency>
	<groupId>org.mybatis</groupId>
	<artifactId>mybatis-spring</artifactId>
	<version>1.3.1</version>
</dependency>
<!--spring&aspectj-->
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-context</artifactId>
	<version>4.3.8.RELEASE</version>
</dependency>
<dependency>
	<groupId>org.aspectj</groupId>
	<artifactId>aspectjrt</artifactId>
	<version>1.8.10</version>
</dependency>
<dependency>
	<groupId>org.aspectj</groupId>
	<artifactId>aspectjweaver</artifactId>
	<version>1.8.10</version>
</dependency>
```

2、mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration SYSTEM "http://mybatis.org/dtd/mybatis-3-config.dtd" >

<configuration>
	<!-- 全局配置项 -->
	<settings>
		<setting name="lazyLoadingEnabled" value="true"/>
		<setting name="aggressiveLazyLoading" value="false"/>
		<setting name="mapUnderscoreToCamelCase" value="true"/>
	</settings>
	<!-- 别名管理器 -->
	<typeAliases>
		<package name="test08.spring"/>
	</typeAliases>
</configuration>
```

<br>

3、spring applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring-1.2.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.3.xsd">

	
	<!-- 引入外部配置文件 -->
	<context:property-placeholder location="test08/spring/jdbc.properties"/>
	
	<!-- 扫描组件包 -->
	<context:component-scan base-package="test08.spring"></context:component-scan>
	
	<!-- 数据源 -->
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="${jdbc.driver}"/>
		<property name="jdbcUrl" value="${jdbc.url}"/>
		<property name="user" value="${jdbc.username}"/>
		<property name="password" value="${jdbc.password}"/>
	</bean>
	
	<!-- 事务管理器 -->
	<bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"/>
	</bean>
	<tx:annotation-driven transaction-manager="transactionManager"/>
	
	<!-- 
		整合mybatis: 目的：1、spring自动注入mapper；2、管理事务
		SqlSessionFactoryBean 实现了 spring FactoryBean接口，
		spring ioc容器启动即会创建SqlSessionFactory对象
	 -->
	<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource"/>
		<property name="configLocation" value="test08/spring/mytais-config.xml"/>
		<property name="mapperLocations" >
			<list>
				<value>classpath:/test08/spring/*Mapper.xml</value>
			</list>
		</property>
	</bean>
	<!-- 扫描所有mapper接口实现，让这些mapper被容器管理 -->
	<mybatis-spring:scan base-package="test08.spring"/>
</beans>
```

<br>

4、编写mapper

```default
package test08.spring;
public interface EmpMapper {
	Emp getEmpById(Integer id);
}

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper SYSTEM "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="test08.spring.EmpMapper">
	<select id="getEmpById" resultType="emp">
		select * from t_emp where id=#{id}
	</select>	
</mapper>
```

5、测试

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
	"classpath:/test08/spring/applicationContext.xml"
})
public class Test1 {
	@Autowired
	private EmpMapper empMapper;
	
	@Test
	public void test01() {
		Emp emp = empMapper.getEmpById(1);
		System.err.println(emp);
	}
}
```

<br>



### mybatis 逆向工程 MBG(mybatis genderator)

[官方文档地址](http://www.mybatis.org/generator/) <br>

[github地址](https://github.com/mybatis/generator) <br>

[eclipse插件地址](https://marketplace.eclipse.org/content/mybatis-generator) <br>

generatorConfig.xml 示例1：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN" "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
  	<!--
	简单版：MyBatis3Simple，只有简单的增删改查方法
	豪华版：MyBatis3，含有带有Example#Criteria的查询方法
	-->
	<context id="mysqlTables" targetRuntime="MyBatis3">
		<!-- 连接配置 -->
		<jdbcConnection 
			connectionURL="jdbc:mysql://dbserver:3306/test"
			driverClass="com.mysql.jdbc.Driver" 
			password="23wesdxc" 
			userId="root" />
		
		<!-- java类型解析器 -->
		<javaTypeResolver>
			<property name="forceBigDecimals" value="false"/>
		</javaTypeResolver>
		
		<!-- 生成策略配置 -->
		<javaModelGenerator 
			targetPackage="test09.genderator"
			targetProject="zdemo-mybatis2/src/test/java" />
		<sqlMapGenerator 
			targetPackage="test09.genderator" 
			targetProject="zdemo-mybatis2/src/test/java">
			<property name="enableSubPackages" value="true"/>
		</sqlMapGenerator>
		<javaClientGenerator 
			type="XMLMAPPER" 
			targetPackage="test09.genderator" 
			targetProject="zdemo-mybatis2/src/test/java">
			<property name="enableSubPackages" value="true"/>
		</javaClientGenerator>
		
		<!-- 映射表配置 -->
		<table schema="test" tableName="t_emp" domainObjectName="Emp">
			<!-- <columnOverride column="???" property="???" /> -->
		</table>
		<table schema="test" tableName="t_dept" domainObjectName="Dept"/>
	</context>
</generatorConfiguration>
```

测试

```java
public SqlSessionFactory getSqlSessionFactory() throws IOException {
	String configfile = "test09/genderator/mybatis-config.xml";
	InputStream is = Resources.getResourceAsStream(configfile);
	return new SqlSessionFactoryBuilder().build(is);
}

@Test
public void test01() throws IOException {
	SqlSession session = getSqlSessionFactory().openSession();
	
	//查询所有
	EmpMapper mapper = session.getMapper(EmpMapper.class);
	List<Emp> list1 = mapper.selectByExample(null);
	System.err.println(list1);
	
	//WHERE ( last_name like ? and gender = ? )  or email like ?
	EmpExample example1 = new EmpExample();
	Criteria criteria1 = example1.createCriteria();
	criteria1.andLastNameLike("%a%");
	criteria1.andGenderEqualTo("1");
	Criteria criteria2 = example1.createCriteria();
	criteria2.andEmailLike("%a%");
	example1.or(criteria2);
	List<Emp> list2 = mapper.selectByExample(example1);
	System.err.println(list2);
	
	session.close();
}
```

<br>



### Mybatis 运行原理

```default
接口层：
	接口方法：增、删、改、查、配置接口
	调用方式：基于StatementID、基于mapper
	
数据处理层：
    ParameterHandler: 参数映射
    SqlSource: SQL解析
    Executor: SQL执行--SimpleExecutor、BatchExecutor、ReuseExecutor
    ResultSetHandler: 结果处理和映射
		
框架支撑层：
	Configuration接口
	SQL语句配置方式：基于XML配置、基于注解配置
	另外包括 事务管理、连接池管理、缓存机制等内容
	
引导层：
	基于XML配置方式、基于java API配置方式
	
```










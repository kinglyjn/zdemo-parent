
JDWP远程调试简介（Java Debug Wire Protocol）
	Java远程调试的原理是两个VM之间通过debug协议进行通信，然后以达到远程调试的目的。两者之间可以通过
	socket进行通信。这对于一些分布式框架（如hadoop集群【可设置YARN_RESOURCEMANAGER_OPTS 环境
	变量为 -agentlib:xxx】、storm集群等）的远程调试非常有用！
	
	首先被debug程序的虚拟机在启动时要开启debug模式，启动debug监听程序：
	//jdk1.7版本之前的方法：
	$ java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n xxx
	//jdk1.7版本之后的方法：
	java -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=y -jar xxx.jar
	
	xxx 是main程序
	server=y 表示是监听其他debugclient端的请求
	address=8000 表示端口是8000
	suspend 表示是否在调试客户端建立连接之后启动VM，
		    如果为y，那么当前的VM就是suspend直到有debug client连接进来才开始执行程序，
		    如果你的程序不是服务器监听模式并且很快就执行完毕的，那么可以选择在y来阻塞它的启动。

	最后用一个debug客户端去debug远程的程序了，比如用Eclipse自带的debug客户端，
	填写运行被debug程序的虚拟机监听的端口号和地址，选择connect方式为attach。
	OK，现在就可以开始远程调试！
	
	
	简单示例：
	1、在开发客户端创建一个简单的应用程序（hadoop-debugdemo），将其打包部署到远程服务器（nimbusz）的某个目录下
	2、在远程nimbusz机器启动待调试的应用程序
	3、在开发客户端（如 Eclipse RemoteDebug）调试远程的应用程序
	可以看到每次开发客户端向下执行一步，都能在远程看到执行的效果。测试成功！
	
	
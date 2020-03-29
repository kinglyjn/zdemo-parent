1、VisualVM 简介

	VisualVM 是一个工具，它提供了一个可视界面，用于查看 Java 虚拟机 (Java Virtual Machine, JVM) 上运行的
	基于 Java 技术的应用程序（Java 应用程序）的详细信息。VisualVM 对 Java Development Kit (JDK) 工具所检
	索的 JVM 软件相关数据进行组织，并通过一种使您可以快速查看有关多个 Java 应用程序的数据的方式提供该信息。您可
	以查看本地应用程序以及远程主机上运行的应用程序的相关数据。此外，还可以捕获有关 JVM 软件实例的数据，并将该数据
	保存到本地系统，以供后期查看或与其他用户共享。


2、Tomcat远程监控配置

	2.1启动visualvm
		命令行运行 jvisualvm
	2.2JMX 配置
		远程机器的程序需要加上JVM参数
		-Dcom.sun.management.jmxremote=true
		-Dcom.sun.management.jmxremote.port=8099（配置远程 connection 的端口号的）
		-Dcom.sun.management.jmxremote.ssl=false(指定了 JMX 是否启用 ssl)
		-Dcom.sun.management.jmxremote.authenticate=false（ 指定了JMX 是否启用鉴权（需要用户名，密码鉴权））
		-Djava.rmi.server.hostname=192.168.0.1（配置 server 的 IP）
		备注：另外需要检查 hostname –i，看解析出来是否为本地的IP，如是127.0.0.1或者IP为多个IP中之一，则其他的IP无效，会连接不上。
	2.3jstatd 配置
		找到需要远程连接的Linux服务器的jdk的bin目录，在其下面建立一个指定安全策略的文件 jstatd.policy 文件，内容如下：
		grant codebase “file:${java.home}/../lib/tools.jar” {
		permission java.security.AllPermission;
		};
		然后在远程主机上启动 jstatd 并且不要关闭。
		启动命令：jstatd -J-Djava.security.policy=jstatd.policy -p 1099
		这样就可以在 JVisualVM 上，添加远程主机，并且设置 jstatd 的端口就可以了。


3、Dump

	VisualVM 能够生成堆转储，统计某一特定时刻 JVM 中的对象信息，帮助我们分析对象的引用关系、是否有内存泄漏情况的发生等。

	3.1堆dump
	当 VisualVM 统计完堆内对象数据后，会把堆转储信息显示在新的堆转储标签内，我们可以看到摘要、类、实例数等信息
	以及通过 OQL 控制台执行查询语句功能。
	concurrent.jmm_shared.basic.critical_section.A. 堆转储的摘要包括转储的文件大小、路径等基本信息，运行的系统环境信息，也可以显示所有的线程信息。
	B. 从类视图可以获得各个类的实例数和占用堆大小数，分析出内存空间的使用情况，找出内存的瓶颈，避免内存的过度使用。
	C. 还能对两个堆转储文件进行比较。通过比较我们能够分析出两个时间点哪些对象被大量创建或销毁。

	3.2线程Dump
	线程状态：
	RUNNABLE: 运行中状态，可能里面还能看到locked字样，表明它获得了某把锁。
	BLOCKED：被某个锁(synchronizers)給block住了。
	WAITING：等待某个condition或monitor发生，一般停留在park(), wait(), sleep(),join() 等语句里。
	TIME_WAITING：和WAITING的区别是wait() 等语句加上了时间限制 wait(timeout)。


4、CPU

	VisualVM 能够监控应用程序在一段时间的 CPU 的使用情况，显示 CPU 的使用率、方法的执行效率和频率等相关数据帮助我们
	发现应用程序的性能瓶颈。我们可以通过 VisualVM 的监视标签和抽样器标签对应用程序进行 CPU 性能分析。

	4.1 CPU 监视
	在监视标签内，我们可以查看 CPU 的使用率以及垃圾回收活动对性能的影响。
	1、过高的 CPU 使用率可能是由于我们的项目中存在低效的代码，可以通过 Profiler 标签的 CPU 性能分析功能进行详细的分析。
	2、如果垃圾回收活动过于频繁，占用了较高的 CPU 资源，可能是由内存不足或者是新生代和旧生代分配不合理导致的等。

	在抽样器标签，点击“CPU”按钮启动一个 CPU 性能分析会话 ，VisualVM 会检测应用程序所有的被调用的方法。当进入一个方法
	时，线程会发出一个“method entry”的事件，当退出方法时同样会发出一个“method exit”的事件，这些事件都包含了时间戳。
	然后 VisualVM 会把每个被调用方法的总的执行时间和调用的次数按照运行时长展示出来。
	此外，我们也可以通过性能分析结果下方的方法名过滤器对分析结果进行过滤。

	4.2 CPU 快照
	当有一个性能分析会话（内存或者 CPU）正在进行时，我们可以通过性能分析结果工具栏的“快照”按钮生成 Profiler 快照捕获当
	时的性能分析数据。


5、线程

	Java 语言能够很好的实现多线程应用程序。当我们对一个多线程应用程序进行调试或者开发后期做性能调优的时候，往往需要了解
	当前程序中所有线程的运行状态，是否有死锁、热锁等情况的发生，从而分析系统可能存在的问题。

	5.1 线程监视
	在 VisualVM 的监视标签内，我们可以查看当前应用程序中所有活动线程和守护线程的数量等实时信息。

	5.2 线程插件安装
	1、从主菜单中选择“工具”>“插件”。
	2、在“可用插件”标签中，选中该插件的“安装”复选框。单击“安装”。

	5.3 死锁例子
	从线程图中，我们可以很轻易地找到这种可疑特征：两个（或多个）线程长期同时处于”监视（被阻塞）”状态，
	提示“检查到死锁”说明它们出现了死锁，应当生成dump查到详细内容。

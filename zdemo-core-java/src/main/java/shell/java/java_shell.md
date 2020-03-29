
## JAVA环境的搭建

1、下载
2、设置环境变量 JAVA_HOME(供其他java程序使用) 和 CLASSPATH(是java类的搜索路径，默认是当前路径".")
3、检测

```shell
java -version
```


## 编辑、编译、运行第一个java程序

### 编辑
使用notepad++、sublim-text、或eclipse、idea等<br>

```java
concurrent.jmm_shared.basic.critical_section.Arent.critical_section.A.java
package dao;
public class concurrent.jmm_shared.basic.critical_section.A {}

//$ vim ~/testdemo/dao/B.java
package dao;
public class B {}

//$ vim ~/testdemo/service/C.java
package service;
import dao.concurrent.jmm_shared.basic.critical_section.A;
import dao.B;
public class C {
	public static void main(String[] args) {
		System.out.println("Hello World!");

	}
}
```

### 编译

```shell
~/doc
~/lib
~/target
~/testdemo
   |--dao
   |   |--concurrent.jmm_shared.basic.critical_section.A.java
   |   |--B.java
   |
   |--service
       |--C.java [C依赖于A和B]

# 生成的字节码文件应与对应的源文件有相同的目录结构
$ cd ~
$ javac testdemo/dao/*.java -d target
$ javac -cp target testdemo/service/*.java -d target

# 或者按如下方式编译
# 在编译条件里面加入-verbose可以很清楚的看到，编译器在寻找C类的时候，以testdemo为根目录
# 根据根目录和package名，类名最终定位了需要用的（源代码）A和B类
$ cd ~
$ javac -sourcepath testdemo test/service/*.java -d target

# 编译完成后target目录如下：
~/target
   |--dao
   |   |--concurrent.jmm_shared.basic.critical_section.A.class
   |   |--B.class
   |
   |--service
       |--C.class
```

### 打包

```shell
# 打包
# 这里的 * 表示会将target目录中的所有文件(夹)打包
$ cd target
$ jar -cvf testdemo.jar *
$ mv ./testdemo.jar ~/lib

# 打一个可执行包
# 首先需要创建和编辑一个manifest.mf文件，内容为 Main-Class: aap.App
# 注意:Main-Class的大小定，冒号后的空格，aap.App后一定输入回车，然后保存
$ jar -cvfm testdemo.jar manifest.mf *

# 解压包
# 通过解压可已看出这里我们打包目录结构为：
$ cd ~/lib
$ jar -xvf testdemo.jar
~/lib/testdemo.jar
        |--dao
        |   |--concurrent.jmm_shared.basic.critical_section.A.class
        |   |--B.class
        |
        |--service
            |--C.class
    
    
# 编译使用jar包的类，如果 -cp参数有多个路径，则用 ":" 进行分隔
$ javac -cp ~/lib/testdemo.jar ~/testdemo/app/App.java -d ~/target
```

### 执行

```shell
# 执行方式1
$ export CLASSPATH=.:~/target
$ java service.C

# 执行字节码文件
$ java -cp ~/target service.C

# 执行某个jar包的类
$ java -jar testdemo.jar
```

### 生成文档

```shell
# 与javac、jar命令不同，javadoc不支持递归路径，只能使用通配符逐级匹配源文件
# -private表示将私有属性或方法的注解包含到文档中；-author、-version表示文档中包含作者和版本信息
$ cd ~
$ javadoc -sourcepath test test/**/*.java test/**/**/*.java -d doc -private -author -version
```
<br>






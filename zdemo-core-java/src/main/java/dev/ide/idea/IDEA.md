### 功能查找：

``` bash
find action:
	help->find action 或 command+3
```


### 跳转相关

``` bash
next project window & previous project window:
	command+`
	command+shift+`
	
recent files & recently changed files:
	command+E
	command+shift+E

last edit location & next edit location (edit):
	command+shift+delete
	command+control+delete

back & forward (view):
	commnad+[
	commnad+]

bookmark:
	set bookmark xxx: alt+f3
	go to bookmark 0: control+shift+0
	go to bookmark 1: control+shift+1
	go to bookmark 2: control+shift+2
	go to bookmark 9: control+shift+9

add to favorites:
	alt+shift+F

acejumpchar of emacIDEAs plugins:
	control+j+需要跳转到的字符

split:
	split vertically: alt+shift+V
	split horizontally: alt+shit+H
	go to next splitter: alt+table
```


### 搜索相关

``` bash
class...: （查找类）
	command+shift+T
	
file...: （查找文件）
	command+shift+R

symbol...: （查找方法和属性等）
	command+alt+O

find in path...: (查找含有某字符串所在的文件 或 替换这个字符串为特定的字符串)
	command+alt+F
replace in path...:
	command+alt+R

find...:（本文件查找或替换某个字符串）
	command+F
replace...:
	command+R
	
search everywhere:
	双击shit
```


### 列编辑

``` bash
select all occurences:
	command+control+G
	
column selection mode:
    shift+command+8
```

### 代码提示

live templates（代码模板）

``` bash
$END$ 

$VAR1$
$VAR2$
```

postfix （词尾补全）

``` bash
# for循环补全提示
100.for 

# syso补全提示
new Date().sout 

# field补全提示
name.field

# return补全提示
user.return

# nn补全提示
user.nn

# 代码意图式补全
show intention actions:
	command+1

# 代码自动生成
generate:
	new file: control+alt+N
	generate: command+N
```


### 代码重构

``` bash
refactor: 
	shift+F6

extract:
	extract method: command+alt+M
	extract variable: command+alt+L
	extract constant:
	extract field:
	extract parameter:
```


### 本地代码历史记录

``` bash
local history:
	鼠标右键->local history
```


### 版本控制

``` bash
annotate:（svn或git查找代码作者及版本信息）
	在代码编辑区左侧栏鼠标右键->annotate
previous change & next change：（快速定位上一个和下一个当前代码修改处）
	shift+control+alt+up
	shift+control+alt+down
revert changes：（恢复到代码改动之前弹框）
	shift+control+alt+z
```


### 使项目与spring进行关联、与数据库关联

``` bash
与spring关联：
file --> project structure...（command+;）--> facets --> add spring facet for xxxmodule

与数据库和mybatis plugin等关联：
database-->new
```


### 调试

``` bash
view breakpoints 或 设置条件断点：
	shift+command+F8

evalute expression（调试过程中表达式求值或执行表达式）：
	command+U

run to cursor（运行到光标所在位置）：
	F9
step over：（单步执行）
	F6
step into：（单步进入）
	F5
resume program：（跳转到下一个断点）
	F8
set value：（重设值）
	F2
```


### 文件结构图

``` bash
file structure：
	cmmand+O
```


### maven依赖图和类依赖图

``` bash
show dependencies（show diagram）：展示maven依赖或类依赖图
	shift+alt+command+U
	或鼠标右键-->maven-->show dependencies
	或鼠标右键-->diagram-->show diagram

exclude：（排除依赖）
	shift+delete

type hierarchy：（展示类的父类、接口或子类）
	F4

call hierarchy：（显示正向的和反向的方法调用链）
	control+alt+H
```



## JAVAEE软件设计的基本原则：

### 开闭原则

	对于程序员来说，一方面，由于需求是由客户提出的，无法控制需求的变化；另一方面，随着技术的进步和时代的发展，
	技术和商业一定会有着非常巨大的变化。这些变化，都会使得人们对软件的要求有变化。例如，最早，用电话线上网的
	时候，腾讯公司的qq软件只有文字聊天功能；之后，随着带宽的增加和需求的变化，qq软件增加了传送文件等功能；
	再之后，随着ADSL的兴起，如今的qq软件有了语音以及视频聊天、qq游戏、qq空间等等非常丰富的功能。这就是一个
	典型的需求不断变化的例子。

	因此我们发现，需求的变化是必然的，程序员只能去适应需求的变化。于是，人们延长软件寿命的工作重点放在了“避免
	修改代码”上。是啊，如果能够在不修改代码的情况下，满足新的需求，那么就能在最大程度上避免软件被“越改越乱”
	了。于是，软件行业提出了一个设计原则：“开闭原则”。

	这条原则指的是，在软件设计的过程中，要求软件能够做到：对扩展开放，对修改关闭！程序员可以通过在原有代码的
	基础上增加新代码的方式来满足新的需求，而不是修改原有的代码。如果一个软件能够做到这一点，那么软件的功能看
	可以自由扩展，从而应对需求的变化；而原有的部分保持成熟和稳定。这样能够更好地保持程序结构的清晰易懂。

	为了实现开闭原则，有一些更加具体的要求。例如，修改关闭，就意味着原有的代码，在新的扩展以后的系统中继续能
	够使用，也就是代码的“可重用性”；而扩展开放，就意味着新的代码能够很方便的扩展原有的系统，而不影响原有的代
	码，这也就是代码的“可扩展性”；为了能够达到开闭原则，就要求模块之间的联系应当尽可能的弱，这样才能够保证方
	便的扩展新功能而不影响其他功能。同时，我们应该能够根据不同的新需求，扩展相应的软件模块，这也就有了软件“各
	司其职”的要求，即：软件的不同模块在功能上应该有明确的职责划分。也就是说，为了实现开闭原则，我们的软件应该
	具备以下特点：

	可重用、可扩展、弱耦合、职专一

	而这正是面型对象编程思想的特点和要求。举个例子，我们都知道中国古代的四大发明，分别是造纸术，指南针，火药
	和活字印刷术。这其中，造纸术、指南针和火药都是从无到有的发明，唯有活字印刷术比较特别，北宋时期的毕昇只是
	将原有的印刷术加以改进，发明了活字印刷术。这难道不奇怪吗？我们往往认为，技术的发明者要比技术的改良者更值
	得纪念。就像我们记住了灯泡的发明者是爱迪生，却淡忘了节能环保型灯泡的发明者。可是针对印刷术，谁又能说清印
	刷术的发明者是谁呢？我们记住的只是那个改良者—毕昇。这不难理解，传统的印刷术，印刷工人要在一整块木板上刻
	下所有的文字。一个错字就可能使得整个版作废。而活字印刷术高明之处在于，将每个字做成独立的“个体”，由多个“
	个体”组成词语、句子。这样，当文字发生改变的时候，只需要替换或增加有改动的文字即可，使得印刷工作符合了“开
	闭原则”。具体的说，每个字是独立的个体，这符合“各司其职”的要求；做好的字可以反复使用，这符合“可重用性”的
	要求；字与字之间彼此独立，互不影响，这符合“弱耦合性”的要求；整个版面可以在不影响其他字的情况下，随意添加
	新的文字，这又符合了“可扩展性”的要求。总之我们可以戏称，活字印刷术位列四大发明，体现了开闭原则的价值，闪
	烁着面向对象的光芒。

	为了更好的使用面向对象思想，为了使得我们的程序更加符合开闭原则，
	下面介绍非常典型的软件职责划分的方法：软件的 “三层体系结构”。

	在介绍三层体系结构之前，我们先分析一下之前提出的那些需求。我们可以把所有的需求分成三大类。

	第一类，数据从哪儿来。
		之前提出的需求中，有的需求数据是从文件中读取，而有些情况，数据是从网络中读取。这一类需求的变化，是数
		据来源的变化，也可以认为是访问数据的方式的变化。
	第二类，数据怎么处理。
		之前提出的需求中，数据获得之后，有些需求要求把数据全部转为了大写，有些需求要求把数据都转为倒置。这一
		类需求的变化，是对数据处理的变化，也可以认为是处理数据方式的变化。
	第三类，数据怎么显示。
		在我们这些需求中，数据的显示比较简单，通过输出语句直接输出数据。但是我们可以想象，在以后的编程实践中，
		数据的显示会有各种各样的方式，例如通过图形界面显示，通过网页显示等等。

	本着各司其职的思想，我们把软件设计成三个层次。这三个层次分别对应于三类需求。

	1.首先是数据访问层。
		数据访问层是用来和数据打交道，具体的说，负责数据的增加，删除，修改和查询（当然，在我们的例子中，只涉及数据的查询）。
		而数据访问层的对象，被称之为数据访问对象简称DAO。因此，数据访问层也被称为DAO层。DAO层对应着第一类需求：数据从哪儿来。
	2.其次是业务逻辑层。
		业务逻辑层，是专门用来处理数据的，这一层的对象被称之为业务对象,简称BO。而数据访问层也被称为biz层。需要注意的是，
		业务逻辑层处理的数据，往往是从DAO层来的。也可以认为，DAO负责获取数据，然后把数据传递给biz层，让biz层对数据进行处理。
	3.最后是数据展示层。
		用户提交的请求数据需要被接收，当数据处理完之后，结果数据需要显示给用户。而负责接收用户请求，并显示数据的是显示层，
		也被称为view层。而view层中的负责与用户交互的对象称之为View Object，简称VO。

	把软件分成三个层次之后，典型的情况如下：	view层与用户交互的过程中，接受用户的一个指令。如果view层用图形界面显示数据，
	则这个指令有可能是图形界面上的一次点击；如果view层用网页显示数据，则这个指令有可能是网页上发送的一个http请求。当view
	层获得一个用户的指令之后，会把这个指令交给biz层。在view层中，VO会调用biz层中的方法，把用户跟view层交互时输入的一些数
	据，传递给biz层。然后，等biz层把数据处理完成之后，再把处理完成的数据返回给view层，让view层显示结果。而biz层如果要处
	理数据，可能要先获得数据。为了获得数据，biz层需要调用dao层的方法。当dao层把数据获取之后，dao对象会将结果返回给biz层，
	biz层才能根据数据进行下一步的处理。

	因此，view、biz、dao三个层次之间，是从上到下依次调用方法的关系。示意为：

	 view<------->biz<------->dao
        |
        |
      client

	我们以一个现实生活中的例子，来说明三层结构的概念。例如，一家汽车4S店，这种店为客户提供多种服务，例如购车、保养、修车等等。
	比如，客户的车坏了，需要修车。这个时候，当客户来到4S店时，与客户交互的往往是前台的接待员。这些接待员能够跟用户清晰、友好
	的沟通，获得客户的指令。我们可以把这些接待员当做就是view层中的VO。	当客户告诉接待员，“我需要修车”，这就相当于客户发送了
	一个指令给了VO。接待员知道用户需要修车之后，自己不会替用户完成修车这个过程。修车的指令，被接待员发送给了真正的汽车修理工。
	汽车哪部分有毛病，哪部分需要检修，这些数据都由接待员告诉修理工，由修理工真正来完成“修车”这个业务。在这个关系中，修理工就
	相当于biz层中的BO，而上述流程就相当于view层的VO调用biz层的BO的方法。	当修理工修车时，有可能需要进行零部件的更换。例如，
	可能需要更换发动机，为此，修理工必须要获得一个新的发动机。往往备用的零配件是存放在仓库中，而仓库显然不能让每个人都随意进行
	访问，往往公司会安排一个专人做仓库管理员。仓库管理员的职责，就是负责向仓库存放物资，以及从仓库中取出物资。如果我们把零配件
	当做数据，那么仓库管理员的工作就是存取数据，扮演的就是DAO的角色。而修理工修车时，会根据需要向管理员要零配件，可以认为这就
	是biz层的BO对象在调用dao层的DAO对象的方法。	当仓库管理员找到相应的零配件时，会把这些物品交给修理工；而当修理工修车完毕
	之后，会通知前台的接待员；接待员最后会把修好的车以及其他的一些信息（例如修车花了多少钱……）显示给客户。类比Java代码就是：
	dao层返回到biz层，biz层返回给view层，view层把运算的结果显示给客户。


	上面我们介绍了软件的三层体系结构。那么，把软件设计成三层结构有什么好处呢？好处在于：当需求发生改变时，我们可以把改变局限在
	某个层次中，而不影响其他层次。例如，如果仓库的地点以及放置物品的位置发生改变的话，我们不需要对前台接待员和汽车修理工做过多
	的说明，只要让仓库保管员能够清楚应该怎么工作就可以了。同样的，如果某一个层次的需求发生变化，则我们只需要针对那个特定的层次，
	修改相应的代码，而不用改变其他层次的代码。为了让层次与层次之间，实现弱耦合性，我们使用接口来定义三个不同的层次。下面以
	HelloWorld程序为例，看一下如何应用三层体系结构。


    ```java
    // 首先，应当定义三层的接口，先是Dao层的接口
    package dao;
    public interface Dao {
    	String getData();
    }

    // 然后是biz层的接口。需要注意的是，因为biz层要调用dao层的方法，
    // 因此在biz对象中，需要维护一个Dao对象的引用。为此，所有Biz层
    // 接口的实现类都应当有一个Dao类型的属性，并提供一个setDao方法。
    // 因此，在Biz接口中，我们定义了setDao方法
    package biz；
    import dao.Dao;
    public interface Biz {
    	void setDao(Dao dao);
    	String dealData();
    }

    // 最后是view层的接口。与之前的情况类似，view层也应当有一个Biz
    // 类型的引用，用以调用biz层的方法。因此，在view接口中我们定义
    // 了setBiz方法
    package view;
    import biz.Biz;
    public interface View {
    	void setBiz(Biz biz);
    	void showData();
    }


    // 定义完了三个接口以后，接下来应该给出的是接口的实现类。例如，
    // 我们首先给出Dao接口的实现类。假设我们希望从当前目录下的
    // "Test.txt"文件中获取数据，则可以给出一个实现类：FileDaoImpl
    // 实现Dao接口，代码如下：
    package dao;
    import java.io.*;
    public class FileDaoImpl implements Dao {
    	public String getData() {
    		BufferedReader br = null;
    		try {
    			FileInputStream fin = new FileInputStream("Test.txt");
    			br = new BufferedReader(fin);
    			data = br.readLine();
    		} catch (IOException e) {
    			e.printStacktrace();
    		} finally {
    			if (br != null) {
    				try {
    					br.close();
    				}
    				catch(IOException e) {
    					e.printStackTrace();
    				}
    			}
    		}
    		return data;
    	}
    }

    // 下面是Biz接口的实现。假设我们要实现把所有数据转换成大写的逻辑，
    // 则可以给出一个UpperCaseBizImpl的实现类，代码为
    package biz;
    import dao.Dao;
    public class UpperCaseBizImpl implements Biz {
    	private Dao dao;

    	public String dealData() {
    		String data = dao.getData();
    		if (data != null) {
    			data = data.toUpperCase();
    		}
    		return data;
    	}

    	public void setDao(Dao dao) {
    		this.dao = dao;
    	}
    }

    // 最后，view接口的实现比较简单，我们给出TextViewImpl
    // 的实现代码：
    package view;
    import biz.BiZ;
    public class TextViewImpl implements View {
    	private Biz biz;

    	public void setBiz(Biz biz) {
    		this.biz = biz;
    	}

    	public void showData() {
    		String data = biz.dealData();
    		System.out.println(data);
    	}
    }


    // 把三个接口实现完成之后，接下来就可以写主方法，
    // 设置类之间的关联关系。代码如下：
    package test;
    import dao.*;
    import biz.*;
    import view.*;
    public class TestMain {
    	public static void main(String[] args) {
    		Dao dao = new FileDaoImpl();

            Biz biz = new LowerCaseBizImpl();
    		biz.setDao(dao);

    		View view = new TextViewImpl();
    		view.setBiz(biz);
    		view.showData();
    	}
    }
    ```

    上述的设计”修改关闭”的要求就非常近了。
    接近了”修改关闭”，但是依然会在需求变化的时候修改原有的代码，能不能完全不求该代码呢？


    ```java
    // 首先，修改码的原因与创建对象相关。
    // 为此，我们可以利用简单工厂模式，把原有的创建对象的过程，
    // 都挪到一个简单工厂里面，工厂代码如下：
    package factory;
    import dao.*;
    import biz.*;
    import view.*;
    public class SimpleFactory {
    	public SimpleFactory() {
    		//
    	}

    	public Dao createDao() {
    		return new FileDaoImpl();
    	}

    	public Biz createBiz() {
    		return new UpperCaseBizImpl();
    	}

    	pubblic View createView() {
    		return new TextViewImpl();
    	}
    }

    //这样,TestMain程序就可以改成
    package test;
    import dao.*;
    import biz.*;
    import view.*;
    import factory.SimpleFactory;

    public class TestMain {
        public static void main(String[] args) {
    		SimpleFactory factory = new SimpleFactory();
            Dao dao = factory.createDao();

            Biz biz = factory.createBiz();
    		biz.setDao(dao);

            View view = factory.createView();
    		view.setBiz(biz);
    		view.showData();
    	}
    }
    ```

    这样，当实现类改变的时候，主方法不需要改变，因为创建对象的代码都使用factory来完成了。但是，这样如果需求改变的话，
    还是要该factory的代码。有没有办法不改代码，就能够在某个层次用一个实现类替换另一个实现类呢？我们知道，利用反射可
    以灵活的创建对象。使用Class.forName(String className)，通过一个字符串获得类对象。
    然后，通过类对象，可以创建一个相应类型的对象。简单的说，可以通过一个字符串，创建一个该类型的对象。而字符串，既可以
    写在代码中，同样可以从别的途径获得。例如，可以从一个配置文件中获得。因此，修改配置文件，就可以让class.forName获
    得不同的字符串，从而创建出不同类型的对象来。换句话说，我们可以通过配置文件+反射的方式，来完成对象的创建。这样，当
    我们需要修改实现类的时候，只需要修改配置文件，而完全不需要修改代码。
    为此，我们可以为SimpleFactory增加一个Properties的属性，并且在构造方法中，利用load方法，读入conf.props。完
    整的SimpleFactory代码如下：

    ```java
    package factory;
    import dao.*;
    import biz.*;
    import view.*;
    import java.util.Properties;
    import java.io.*;
    public class SimpleFactory {
    	private Properties props;
    	public SimpleFactory() {
    		props = new Properties();
    		InputStream is = null;
    		try{
    			   is = new FileInputStream("conf.props");
    			   props.load(is);
    		   }catch(IOException e){
    			e.printStackTrace();
    		}
    		finally{
    			if (is!=null){
    				try{
    					is.close();
    				}
    				catch(Exception e){
    					e.printStackTrace();
    				}
    			}
    		}
    	}
    	public Dao createDao(){
    		String className = props.getProperty("dao");
    		Dao dao = (Dao) createObject(className);
    		return dao;
    	}
    	public Biz createBiz(){
    		String className = props.getProperty("biz");
    		Biz biz = (Biz) createObject(className);
    		return biz;
    	}

    	public View createView(){
    		String className = props.getProperty("view");
    		View view = (View) createObject(className);
    		return view;
    	}
    	private Object createObject(String name){
    		Object result = null;
    		try {
    			Class c = Class.forName(name);
    			result = c.newInstance();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		return result;
    	}
    }
    ```

    配置conf.props文件如下：
    ```properties
    view=view.TextViewImpl
    biz=biz.UpperCaseBizImpl
    dao=dao.FileDaoImpl
    ```

    test.txt文件内容如下：
    ```
    Hello World
    ```

    当上述简单工厂完成之后，运行结果如下：
    HELLO WORLD

    而当有新需求，希望把所有字符改成小写，只需要修改配置文件即可：
    ```properties
    biz=biz.LowerCaseBizImpl
    ```

    读者也可以在该代码上，尝试着完成其他的一些需求。例如：文字从网络中获取（改变的是数据的获取方式，需要为
    Dao接口添加一个实现类，替换掉FileDaoImpl）；将文字倒置处理（改变的是数据的处理方式，需要为Biz接口添
    加一个实现类，替换LowerCaseBizImpl）。

    也许读者会迷惑，写了这么多的接口和类，最终只是完成了最初级的HelloWorld输出的功能，是不是有点小题大做
    了？诚然，这个程序的功能并不复杂，但这种程序结构，使得在需求变化的时候，我们总是能够有针对性的扩展出某
    个接口的实现类，而不需要改动任何原有代码，从而保证了开闭原则的要求，也使得我们的代码能够“健康长寿”。我
    们可以认为，这才是一个真正贯彻了面向对象思想，发挥了面向对象优势的HelloWorld！




package concurrent.jmm_shared.basic.problem.shared_data_security;

import org.aspectj.lang.annotation.Aspect;
import org.junit.After;
import org.junit.Before;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 以下线程安全标记为Y，不安全标记为N
 */
public class Demo02_SomeExample {

    static class MyServlet01 {
        Map<String,Object> map = new HashMap<>(); // N
        String s1 = "..."; // N
        final String s2 = "..."; // Y
        Date d1 = new Date(); // N
        final Date d2 = new Date(); // N

        public void doGet(HttpServletRequest request, HttpServletResponse response) {
            // 使用上述变量
        }
    }


    static class MyServlet02 {
        static class UserService {
            private int count = 0; // N
            public void update() {
                // ...
                count++;
            }
        }
        private UserService userService = new UserService();

        public void doGet(HttpServletRequest request, HttpServletResponse response) {
            // 使用上述变量
        }
    }


    static class MyServlet03 {
        static class UserService {
            static class UserDao {
                public void update() throws SQLException {
                    String sql = "update user set password = ? where username = ?";
                    Connection conn = DriverManager.getConnection("","",""); // Y
                    // ...
                    conn.close();
                }
            }
            private UserDao userDao = new UserDao();
        }
        private UserService userService = new UserService();

        public void doGet(HttpServletRequest request, HttpServletResponse response) {
            // 使用上述变量
        }
    }


    static class MyServlet04 {
        static class UserService {
            static class UserDao {
                private Connection conn = null; // N
                public void update() throws SQLException {
                    String sql = "update user set password = ? where username = ?";
                    conn = DriverManager.getConnection("","","");
                    // ...
                    conn.close();
                }

            }
            private UserDao userDao = new UserDao();
            public void update() throws SQLException {
                userDao.update();
            }
        }
        private UserService userService = new UserService();

        public void doGet(HttpServletRequest request, HttpServletResponse response) {
            // 使用上述变量
        }
    }


    @Aspect
    @Component
    static class MyAspect {
        private long start = 0L; // N
        @Before
        public void before() {
            start = System.nanoTime();
        }
        @After
        public void after() {
            long end = System.nanoTime();
            System.out.println("cost time:" + (end-start));
        }
    }


    static abstract class MyTest {
        public void bar() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            foo(sdf);
        }
        // 其中 foo 的行为是不确定的，可能导致不安全的发生，被称之为外星方法
        public abstract void foo(SimpleDateFormat sdf);

        public static void main(String[] args) {
            new MyTest(){
                @Override
                public void foo(SimpleDateFormat sdf) {
                    String dateStr = "1999-10-11 00:00:00";
                    for (int i = 0; i < 20; i++) {
                        new Thread(()->{
                            try {
                                sdf.parse(dateStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                }
            }.bar();
        }
    }

}

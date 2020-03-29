package net.http;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author KJ
 * @Date 2020-03-29 1:42 PM
 * @Description
 */
public class UrlDemo {

    @Test
    public void test01() throws Exception {
        URL url = new URL("http://localhost:1234");
        URLConnection conn = url.openConnection();

        // 设置连接的信息
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        // 设置请求头信息
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "utf8");

        // 设置数据的边界
        String boundary = "-----" + System.currentTimeMillis();
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

        // 准备头部信息
        OutputStream os = conn.getOutputStream();
        File file = new File("/Users/zhangqingli/Pictures/mypics/logo/favicon.png");
        FileInputStream is = new FileInputStream(file);

        StringBuffer sb = new StringBuffer();
        sb.append("--")
                .append(boundary)
                .append("\r\n")
                .append("Content-Disposition:form-data;name=\"media\";filename=\"" + file.getName() +"\"\r\n")
                .append("Content-Type:application/octet-stream\r\n\r\n");
        os.write(sb.toString().getBytes());

        // 准备文件内容
        byte[] bs = new byte[1024];
        int len;
        while ((len=is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }

        // 准备尾部信息
        String foot = "\r\n--" + boundary + "--\r\n";
        os.write(foot.getBytes());
        os.flush();
        os.close();
        is.close();

        // 获取响应结果
        InputStream is2 = conn.getInputStream();
        StringBuffer sb2 = new StringBuffer();
        while ((len=is2.read(bs)) != -1) {
            sb2.append(new String(bs ,0, len));
        }
        System.out.println(sb2.toString());
    }

}

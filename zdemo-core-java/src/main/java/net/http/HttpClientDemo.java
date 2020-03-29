package net.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpClientDemo {

    private CloseableHttpClient client;
    @Before
    public void before() {
        client = HttpClientBuilder.create().build();
    }
    @After
    public void after() throws IOException {
        client.close();
    }


    /*
    GET /?name=%E5%BC%A0%E4%B8%89&age=23 HTTP/1.1
    Host: localhost:1234
    Connection: Keep-Alive
    User-Agent: Apache-HttpClient/4.5.10 (Java/1.8.0_131)
    Accept-Encoding: gzip,deflate
    */
    @Test
    public void testGetRequest() throws IOException, URISyntaxException {
        // 初始化 GET
        HttpGet httpGet = new HttpGet();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) // 设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000) // 设置socket读写超时时间
                .setRedirectsEnabled(true)
                .build();
        httpGet.setConfig(requestConfig);

        List<NameValuePair> params = new LinkedList<>(); // 也可以通过拼接url的方式
        params.add(new BasicNameValuePair("name", "张三"));
        params.add(new BasicNameValuePair("age", "23"));
        URI uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(1234).setPath("/").setParameters(params).build();
        httpGet.setURI(uri);

        // 发请求
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();

        // 看响应
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(responseEntity, StandardCharsets.UTF_8));

        // 关资源
        response.close();
    }



    /*
    POST /?account=kinglyjn%40keyllo.com&balance=1024 HTTP/1.1
    Content-Type: application/json;charset=utf8
    MyHeader01: ASDFDGHG
    Content-Length: 39
    Host: localhost:1234
    Connection: Keep-Alive
    User-Agent: Apache-HttpClient/4.5.10 (Java/1.8.0_131)
    Accept-Encoding: gzip,deflate

    {"gender":"F","name":"张三","age":23}
    */
    @Test
    public void testPostRequest() throws IOException, URISyntaxException {
        HttpPost httpPost = new HttpPost();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) // 设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000) // 设置socket读写超时时间
                .setRedirectsEnabled(true)
                .build();
        httpPost.setConfig(requestConfig);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        httpPost.setHeader("MyHeader01", "ASDFDGHG");

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", "张三");
        userMap.put("age", 23);
        userMap.put("gender", "F");
        String userJson = JSONObject.toJSONString(userMap);
        StringEntity stringEntity = new StringEntity(userJson, StandardCharsets.UTF_8);
        httpPost.setEntity(stringEntity);

        LinkedList<NameValuePair> params = new LinkedList<>();
        params.add(new BasicNameValuePair("account", "kinglyjn@keyllo.com"));
        params.add(new BasicNameValuePair("balance", "1024"));
        URIBuilder uriBuilder = new URIBuilder("http://localhost:1234");
        uriBuilder.setParameters(params);
        URI uri = uriBuilder.build();
        httpPost.setURI(uri);

        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();

        System.out.println(response.getStatusLine()); // 响应状态
        InputStream is = responseEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
        response.close();
    }

}

package net.http;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.io.DefaultHttpResponseParserFactory;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 提示性能的点：
 *
 * 1. httpclient反复创建开销
 * httpclient是一个线程安全的类，没有必要由每个线程在每次使用时创建，全局保留一个即可
 *
 * 2. 反复创建tcp连接的开销
 * tcp的三次握手与四次挥手两大裹脚布过程，对于高频次的请求来说，消耗实在太大。试想如果每次
 * 请求我们需要花费5ms用于协商过程，那么对于qps为100的单系统，1秒钟我们就要花500ms用于握
 * 手和挥手。又不是高级领导，我们程序员就不要搞这么大做派了，改成keep alive方式以实现连接复用！
 *
 * 3. 重复缓存entity的开销
 * HttpEntity entity = httpResponse.getEntity(); String response = EntityUtils.toString(entity);
 * 这里我们相当于额外复制了一份content到一个字符串里，而原本的httpResponse仍然保留了一份content，需要被consume掉，
 * 在高并发且content非常大的情况下，会消耗大量内存。并且，我们需要显式的关闭连接，ugly。
 *
 * 小结：
 * 按上面的分析，我们主要要做三件事：一是单例的client，二是缓存的保活连接，三是更好的处理返回结果。
 *
 */
public class HttpService {

    private CloseableHttpClient httpClient;

    /**
     * 提到连接缓存，很容易联想到数据库连接池。httpclient4提供了一个PoolingHttpClientConnectionManager 作为连接池。
     *
     * 为什么使用HttpClient连接池？
     * 在 keep-alive 时间内，可以使用同一个 tcp 连接发起多次 http 请求。
     * 如果不使用连接池，在大并发的情况下，每次连接都会打开一个端口，使系统资源很快耗尽，无法建立新的连接，可以限定最多打开的端口数。
     * 我的理解是连接池内的连接数其实就是可以同时创建多少个 tcp 连接，httpClient 维护着两个 Set，leased(被占用的连接集合) 和
     * avaliabled(可用的连接集合) 两个集合，释放连接就是将被占用连接放到可用连接里面。
     *
     */
    private CloseableHttpClient getCloseableHttpClient() {
        if (httpClient == null) {
            synchronized (HttpClientUtils.class) {
                if (httpClient == null) {
                    // 注册访问协议相关的Socket工厂
                    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.INSTANCE)
                            .register("https", SSLConnectionSocketFactory.getSocketFactory())
                            .build();
                    // 配置写请求/解析响应处理器
                    HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory = new ManagedHttpClientConnectionFactory(
                            DefaultHttpRequestWriterFactory.INSTANCE,
                            DefaultHttpResponseParserFactory.INSTANCE
                    );
                    // DNS解析器
                    DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;

                    //
                    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connectionFactory, dnsResolver);

                    // 设置默认的socket参数
                    connectionManager.setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(true).build());

                    // 设置最大连接数。高于这个值时，新连接请求，需要阻塞，排队等待
                    connectionManager.setMaxTotal(500);

                    // 在从连接池获取连接时，连接不活跃多长时间后需要进行一次验证(运行closeExpiredConnections 和closeIdleConnections方法)，默认为2s
                    connectionManager.setValidateAfterInactivity(2000);

                    // 例如默认每路由最高50并发，具体依据业务来定
                    connectionManager.setDefaultMaxPerRoute(50);

                    //配置默认的请求参数
                    RequestConfig defaultRequestConfig = RequestConfig.custom()
                            .setConnectTimeout(2 * 1000)    //连接超时设置为2s
                            .setSocketTimeout(5 * 1000)     //等待数据超时设置为5s
                            .setConnectionRequestTimeout(2 * 1000)  //从连接池获取连接的等待超时时间设置为2s
                            .build();

                    //
                    httpClient = HttpClients.custom()
                            .setConnectionManager(connectionManager)
                            .setConnectionManagerShared(false)  //连接池不是共享模式，这个共享是指与其它httpClient是否共享
                            .evictIdleConnections(60, TimeUnit.SECONDS) //定期回收空闲连接
                            .evictExpiredConnections()  //回收过期连接
                            .setConnectionTimeToLive(60, TimeUnit.SECONDS)  //连接存活时间，如果不设置，则根据长连接信息决定
                            .setDefaultRequestConfig(defaultRequestConfig)  //设置默认的请求参数
                            .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)    //连接重用策略，即是否能keepAlive
                            //.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)      //长连接配置，即获取长连接生产多长时间
                            .setKeepAliveStrategy((response, context) -> {
                                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                                while (it.hasNext()) {
                                    HeaderElement he = it.nextElement();
                                    String param = he.getName();
                                    String value = he.getValue();
                                    if (value != null && param.equalsIgnoreCase("timeout")) {
                                        return Long.parseLong(value) * 1000;
                                    }
                                }
                                return 5 * 1000; // 设置 Keep-alive 时间为 5s
                            })
                            .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))  //设置重试次数，默认为3次；当前是禁用掉
                            .build();

                    // JVM停止或重启时，关闭连接池释放掉连接
                    Runtime.getRuntime().addShutdownHook(new Thread() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("closing http client");
                                httpClient.close();
                                System.out.println("http client closed");
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    });
                }
            }
        }
        return httpClient;
    }


    /**
     * main
     *
     */
    public static void main(String[] args) throws IOException {
        // 维持登录状态
        CookieStore cookieStore = new BasicCookieStore();
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        // 多次调用
        HttpGet get = new HttpGet("http://localhost:8912/demo01_hello");
        HttpResponse response = httpClient.execute(get);
    }

}

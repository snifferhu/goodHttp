package org.sniffhu.goodHttp.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpConnectionGenerationFactory {
    private HttpConfig config;

    public HttpConnectionGenerationFactory(){
        config = new HttpConfig();
        config.setConnReqTimeout(5000);
        config.setConnTimeout(5000);
        config.setSocketTimeout(5000);
        config.setDefaultConn(50);
        config.setPoolMaxTotal(500);
    }

    public PoolingHttpClientConnectionManager generatePoolManger() {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .build();
        //初始化连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        // Get the poolMaxTotal value from our application[-?].xml or default to 10 if not explicitly set
        connManager.setMaxTotal(config.getPoolMaxTotal());
        connManager.setDefaultMaxPerRoute(config.getDefaultConn());
        return connManager;
    }

    /**
     * @return 构造请求超时配置对象
     */
    public RequestConfig generateRequestConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(config.getSocketTimeout())
                .setConnectTimeout(config.getConnTimeout())
                .setConnectionRequestTimeout(config.getConnReqTimeout())
                .build();
    }

    public HttpConfig getConfig() {
        return config;
    }

    public HttpConnectionGenerationFactory setConfig(HttpConfig config) {
        this.config = config;
        return this;
    }
}

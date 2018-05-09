package org.sniffhu.goodHttp;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.sniffhu.goodHttp.response.DefaultJsonHandler;

import static java.nio.charset.StandardCharsets.UTF_8;


public class RequestTemplateFactory {
    private static class PoolConnManager{
        private static PoolingHttpClientConnectionManager poolConnManager;
        static {
            poolConnManager = new PoolingHttpClientConnectionManager();
            poolConnManager.setMaxTotal(500);
            poolConnManager.setDefaultMaxPerRoute(100);
        }
    }
    private PoolingHttpClientConnectionManager poolConnManager;

    private  RequestConfig requestConfig;

    private DefaultJsonHandler handler;

    public <In,Out> RequestTemplate<In,Out> genRequestTemplate() {
        return new RequestTemplate<In,Out>()
                .reqCharset(UTF_8.toString())
                .respCharset(UTF_8.toString())
                .requestConfig(requestConfig)
                .connPoolManager(PoolConnManager.poolConnManager)
                .responseHandler(handler)
                .retry(3);
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    public DefaultJsonHandler getHandler() {
        return handler;
    }

    public void setHandler(DefaultJsonHandler handler) {
        this.handler = handler;
    }

    public PoolingHttpClientConnectionManager getPoolConnManager() {
        return poolConnManager;
    }

    public void setPoolConnManager(PoolingHttpClientConnectionManager poolConnManager) {
        this.poolConnManager = poolConnManager;
    }
}
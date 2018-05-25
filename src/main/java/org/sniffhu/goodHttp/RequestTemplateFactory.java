package org.sniffhu.goodHttp;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.sniffhu.goodHttp.config.HttpConnectionGenerationFactory;
import org.sniffhu.goodHttp.response.AbstractResponseHandler;
import org.sniffhu.goodHttp.response.DefaultJsonHandler;

import static java.nio.charset.StandardCharsets.UTF_8;


public class RequestTemplateFactory {
    private HttpConnectionGenerationFactory connectionConfiguration = new HttpConnectionGenerationFactory();
    private final PoolingHttpClientConnectionManager poolConnManager = connectionConfiguration.generatePoolManger();

    private RequestConfig requestConfig;

    private AbstractResponseHandler handler = new DefaultJsonHandler();

    public <In, Out> RequestTemplate<In, Out> genRequestTemplate() {
        return new RequestTemplate<In, Out>()
                .reqCharset(UTF_8.toString())
                .respCharset(UTF_8.toString())
                .requestConfig(requestConfig)
                .connPoolManager(poolConnManager)
                .responseHandler(handler)
                .retry(3);
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    public AbstractResponseHandler getHandler() {
        return handler;
    }

    public void setHandler(AbstractResponseHandler handler) {
        this.handler = handler;
    }

    public PoolingHttpClientConnectionManager getPoolConnManager() {
        return poolConnManager;
    }

}
package org.sniffhu.goodHttp;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.sniffhu.goodHttp.config.HttpConnectionGenerationFactory;
import org.sniffhu.goodHttp.response.AbstractResponseHandler;
import org.sniffhu.goodHttp.response.DefaultJsonHandler;
import org.sniffhu.goodHttp.response.DefaultStringHandler;
import org.sniffhu.goodHttp.response.DefaultXmlHandler;

import static java.nio.charset.StandardCharsets.UTF_8;


public class RequestTemplateFactory {
    private HttpConnectionGenerationFactory connectionConfiguration = new HttpConnectionGenerationFactory();
    private final PoolingHttpClientConnectionManager poolConnManager = connectionConfiguration.generatePoolManger();

    private RequestConfig requestConfig;

    private AbstractResponseHandler defaultHandler;
    private AbstractResponseHandler jsonHandler = new DefaultJsonHandler();
    private AbstractResponseHandler xmlHandler = new DefaultXmlHandler();
    private AbstractResponseHandler strHandler = new DefaultStringHandler();

    public <In, Out> RequestTemplate<In, Out> genRequestTemplate() {
        requestConfig = RequestConfig.custom().build();
        if (defaultHandler == null){
            defaultHandler = jsonHandler;
        }
        return new RequestTemplate<In, Out>()
                .reqCharset(UTF_8.toString())
                .respCharset(UTF_8.toString())
                .requestConfig(requestConfig)
                .connPoolManager(poolConnManager)
                .responseHandler(defaultHandler)
                .retry(3);
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    public AbstractResponseHandler getDefaultHandler() {
        return defaultHandler;
    }

    public RequestTemplateFactory setDefaultHandler(AbstractResponseHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
        return this;
    }

    public RequestTemplateFactory setStringResponse() {
        this.defaultHandler = strHandler;
        return this;
    }

    public RequestTemplateFactory setXmlResponse() {
        this.defaultHandler = xmlHandler;
        return this;
    }

    public RequestTemplateFactory setResponseHandler(AbstractResponseHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
        return this;
    }

    public PoolingHttpClientConnectionManager getPoolConnManager() {
        return poolConnManager;
    }

}
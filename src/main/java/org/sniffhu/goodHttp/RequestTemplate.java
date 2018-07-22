package org.sniffhu.goodHttp;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniffhu.goodHttp.method.MethodFactory;
import org.sniffhu.goodHttp.response.AbstractResponseHandler;
import org.sniffhu.goodHttp.util.ParamsUtils;
import org.sniffhu.goodHttp.util.StringUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;


public class RequestTemplate<In, Out> {
    private static final Logger logger = LoggerFactory.getLogger(RequestTemplate.class);
    private String url;
    private Map<String, String> header = new HashMap<String, String>() {{
        put("userAgent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 UBrowser/5.5.6743.209 Safari/537.36");
    }};
    private Boolean isKeepAlive = true;
    private In params;
    private In body;
    private String reqCharset;
    private String respCharset;
    private AbstractResponseHandler handler;
    private RequestConfig requestConfig;
    private PoolingHttpClientConnectionManager poolConnManager;
    private CloseableHttpClient httpClient;
    private Integer retry;
    private HttpRequestBase method;

    public static RequestTemplate genTemplate() {
        RequestTemplate tmp = new RequestTemplate();
        tmp.respCharset = UTF_8.toString();
        tmp.reqCharset = UTF_8.toString();
        return tmp;
    }

    public RequestTemplate url(String url) {
        this.url = url;
        return this;
    }

    public In getParams() {
        return params;
    }

    public RequestTemplate setParams(In params) {
        this.params = params;
        return this;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public RequestTemplate setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    public In getBody() {
        return body;
    }

    public RequestTemplate setBody(In body) {
        this.body = body;
        return this;
    }

    public RequestTemplate respCharset(String charset) {
        this.respCharset = charset;
        return this;
    }

    public RequestTemplate reqCharset(String charset) {
        this.reqCharset = charset;
        return this;
    }

    public RequestTemplate responseHandler(AbstractResponseHandler handler) {
        this.handler = handler;
        return this;
    }

    RequestTemplate requestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
        return this;
    }

    RequestTemplate connPoolManager(PoolingHttpClientConnectionManager manager) {
        this.poolConnManager = manager;
        return this;
    }

    RequestTemplate retry(Integer retry) {
        this.retry = retry;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RequestTemplate setUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public RequestTemplate setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
        return this;
    }

    public HttpRequestBase getMethod() {
        return method;
    }

    public RequestTemplate setMethod(HttpRequestBase method) {
        this.method = method;
        return this;
    }

    public Boolean getKeepAlive() {
        return isKeepAlive;
    }

    public RequestTemplate notKeepAlive() {
        isKeepAlive = false;
        return this;
    }

    public RequestTemplate setKeepAlive(Boolean keepAlive) {
        isKeepAlive = keepAlive;
        return this;
    }

    public Integer getRetry() {
        return retry;
    }

    public RequestTemplate setRetry(Integer retry) {
        this.retry = retry;
        return this;
    }

    private HttpPatch generateHttpPatch(String urlStr) {
        HttpPatch httpPatch = new HttpPatch(urlStr);
        httpPatch.setHeader("Accept", "application/json");
        httpPatch.setHeader("Content-Type", "application/json");
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
                + " Chrome/44.0.2403.157 UBrowser/5.5.6743.209 Safari/537.36";
        httpPatch.addHeader("User-agent", userAgent);
        httpPatch.addHeader("Connection", "Keep-Alive");
        httpPatch.setConfig(this.requestConfig);
        return httpPatch;
    }

    /**
     * post请求
     * head 为"Content-Type", "application/json")
     *
     * @return
     */
    private HttpPost generateHttpPost() {
        if (StringUtils.isNotEmpty(url)) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
                    + " Chrome/44.0.2403.157 UBrowser/5.5.6743.209 Safari/537.36";
            httpPost.addHeader("User-agent", userAgent);
            httpPost.addHeader("Connection", "Keep-Alive");
            httpPost.setConfig(requestConfig);
            return httpPost;
        } else {
            throw new IllegalArgumentException("request url can't been empty");
        }
    }


    private <Methods extends HttpRequestBase> Out defaultRequest(Methods method)
            throws IOException {
        if (httpClient == null) {
            httpClient = HttpClients.custom()
                    .setConnectionManager(poolConnManager)
                    .build();
        }
        try (CloseableHttpResponse response = httpClient.execute(method)) {
            int status = response.getStatusLine().getStatusCode();
            if (!(status >= 200 && status < 300)) {
                method.abort();
            }
            return (Out) handler.handleResponse(response);
        }
    }


    public Out exec(HttpRequestBase httpRequestBase) throws IOException {
        try {
            return defaultRequest(httpRequestBase);
        } catch (ConnectException connectException) {
            logger.warn("Connect failed. url:{} param:{}", url, params, connectException);
            throw connectException;
        } catch (SocketTimeoutException timeoutException) {
            logger.warn("Connect timeout failed. url:{} param:{}", url, params, timeoutException);
            throw timeoutException;
        } catch (Exception e) {
            logger.error("url:{} param:{}", url, params, e);
            throw e;
        }
    }


    public Out exec(HttpRequestBase httpRequestBase, Integer retryTimes) throws IOException {
        try {
            return defaultRequest(httpRequestBase);
        } catch (ConnectException connectException) {
            logger.warn("Connect failed. retryTimes:{} url:{} param:{}", retryTimes, url, params, connectException);
            if (++retryTimes < this.getRetry()) {
                return exec(httpRequestBase, retryTimes);
            }
            throw connectException;
        } catch (SocketTimeoutException timeoutException) {
            logger.warn("Connect timeout failed. retryTimes:{} url:{} param:{}", retryTimes, url, params, timeoutException);
            if (++retryTimes < this.getRetry()) {
                return exec(httpRequestBase, retryTimes);
            }
            throw timeoutException;
        } catch (Exception e) {
            logger.error("url:{} param:{}", url, params, e);
            throw e;
        }
    }

    private void genURL() {
        if (getParams() != null) {
            setUrl(getUrl() + "?" + ParamsUtils.beanToParamURL(getParams()));
        }
    }

    public Out doGet() throws IOException {
        genURL();
        HttpGet httpGet = MethodFactory.generateGetMethod(this);
        return exec(httpGet);
    }

    public Out doGetOne() throws IOException {
        return doGet();
    }

    public Out doGetRetry() throws IOException {
        genURL();
        HttpGet httpGet = MethodFactory.generateGetMethod(this);
        return exec(httpGet, getRetry());
    }

    public Out doPost() throws IOException {
        genURL();
        HttpPost httpPost = MethodFactory.generatePostMethod(this);
        return exec(httpPost);
    }

    public Out doPostOne() throws IOException {
        return doPost();
    }

    public Out doPostRetry() throws IOException {
        genURL();
        HttpPost httpPost = MethodFactory.generatePostMethod(this);
        return exec(httpPost, getRetry());
    }
}
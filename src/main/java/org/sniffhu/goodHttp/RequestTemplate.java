package org.sniffhu.goodHttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniffhu.goodHttp.method.GetMethod;
import org.sniffhu.goodHttp.response.AbstractResponseHandler;
import org.sniffhu.goodHttp.util.StringUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;


public class RequestTemplate<In, Out> {
    private static final Logger logger = LoggerFactory.getLogger(RequestTemplate.class);
    private String url;
    private String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 UBrowser/5.5.6743.209 Safari/537.36";
    private Boolean isKeepAlive = true;
    private In params;
    private String reqCharset;
    private String respCharset;
    private AbstractResponseHandler handler;
    private RequestConfig requestConfig;
    private PoolingHttpClientConnectionManager poolConnManager;
    private Integer retry;
    private HttpRequestBase method;

    public RequestTemplate genTemplate() {
        RequestTemplate tmp = new RequestTemplate();
        tmp.respCharset = UTF_8.toString();
        tmp.reqCharset = UTF_8.toString();
        return tmp;
    }

    public RequestTemplate url(String url) {
        this.url = url;
        return this;
    }

    public RequestTemplate params(In params) {
        this.params = params;
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

    public String getUserAgent() {
        return userAgent;
    }

    public RequestTemplate setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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

    private CloseableHttpClient httpClient;

    private <Methods extends HttpRequestBase> Out defaultRequest(Methods method)
            throws IOException {
        if (httpClient == null) {
            httpClient = HttpClients.custom()
                    .setConnectionManager(poolConnManager)
                    .build();
        }
        CloseableHttpResponse response = httpClient.execute(method);
        int status = response.getStatusLine().getStatusCode();
        if (!(status >= 200 && status < 300)) {
            method.abort();
        }
        return (Out) handler.handleResponse(response);
    }

    private Out doPost(In params) throws IOException {
        HttpEntity entity = null;
        if (params instanceof String) {
            entity = new ByteArrayEntity(params.toString().getBytes(reqCharset));
        } else if (params instanceof JSONObject) {
            entity = new ByteArrayEntity(params.toString().getBytes(reqCharset));
        } else if (params == null) {
            entity = new ByteArrayEntity(new byte[]{});
        } else if (params instanceof Map && ((Map) params).size() == 0) {
            entity = new ByteArrayEntity(new byte[]{});
        } else {
            entity = new ByteArrayEntity(JSON.toJSONString(params).getBytes(reqCharset));
        }
        HttpPost httpPost = generateHttpPost();
        httpPost.setEntity(entity);
        return defaultRequest(httpPost);
    }

    public Out doPost() throws IOException {
        return doPost(this.params);
    }

    public JSONObject doPostOne() {
        JSONObject jsonObject;
        try {
            return (JSONObject) doPost(this.params);
        } catch (ConnectException connectException) {
            jsonObject = new JSONObject();
            jsonObject.put("CODE", "4002");
            jsonObject.put("MSG", "ConnectException:" + connectException.getLocalizedMessage());
        } catch (java.net.SocketTimeoutException timeoutException) {
            jsonObject = new JSONObject();
            jsonObject.put("CODE", "4003");
            jsonObject.put("MSG", "SocketTimeoutException:" + timeoutException.getLocalizedMessage());
        } catch (Exception e) {
            logger.error("url:{} param{}", url, params, e);
            jsonObject = new JSONObject();
            jsonObject.put("CODE", "4004");
            jsonObject.put("MSG", "Exception:" + e.getLocalizedMessage());
        }
        return jsonObject;
    }

    public JSONObject doPostRetry() {
        JSONObject jsonObject = doPostOne();
        if (jsonObject.containsKey("CODE")) {
            if (jsonObject.get("CODE").toString().contains("400")) {
                for (int i = 0; i < retry; i++) {
                    jsonObject = doPostOne();
                    if (jsonObject != null && (jsonObject.containsKey("CODE") && !jsonObject.get("CODE").toString().contains("400"))) {
                        return jsonObject;
                    }
                }
                return jsonObject;
            }
        }
        return jsonObject;
    }

    private HttpGet generateHttpGet(String urlStr) {
        HttpGet httpGet = new HttpGet(urlStr);
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
                + " Chrome/44.0.2403.157 UBrowser/5.5.6743.209 Safari/537.36";
        httpGet.addHeader("User-agent", userAgent);
        httpGet.addHeader("Connection", "Keep-Alive");
        httpGet.setConfig(requestConfig);
        return httpGet;
    }

    private Out doGet(In params) throws IOException {
        String entity = "";
        if (params instanceof String) {
            entity = "?" + params;
        } else if (params instanceof JSONObject && !((JSONObject) params).isEmpty()) {
            Object[] keys = ((JSONObject) params).keySet().toArray();
            StringBuilder tmpParams = new StringBuilder();
            for (int i = 0, size = keys.length; i < size; i++) {
                String tmpKey = String.valueOf(keys[i]);
                tmpParams.append(tmpKey).append("=").append(((JSONObject) params).get(tmpKey));
                if (i != size - 1) {
                    tmpParams.append("&");
                }
            }
            entity = "?" + tmpParams.toString();
        } else if (params instanceof Map && ((Map) params).size() != 0) {
            Object[] keys = ((Map) params).keySet().toArray();
            StringBuilder tmpParams = new StringBuilder();
            for (int i = 0, size = keys.length; i < size; i++) {
                String tmpKey = String.valueOf(keys[i]);
                tmpParams.append(tmpKey).append("=").append(((JSONObject) params).get(tmpKey));
                if (i != size - 1) {
                    tmpParams.append("&");
                }
            }
            entity = "?" + tmpParams.toString();
        } else if (params == null) {
            entity = "";
        } else {
            Object[] keys = JSON.parseObject(JSON.toJSONString(params)).keySet().toArray();
            StringBuilder tmpParams = new StringBuilder();
            for (int i = 0, size = keys.length; i < size; i++) {
                String tmpKey = String.valueOf(keys[i]);
                tmpParams.append(tmpKey).append("=").append(((JSONObject) params).get(tmpKey));
                if (i != size - 1) {
                    tmpParams.append("&");
                }
            }
            entity = "?" + tmpParams.toString();
        }
//        HttpGet httpGet = generateHttpGet(url + entity);
        HttpGet httpGet = new GetMethod().generateHttpMethod(this);
        return (Out) defaultRequest(httpGet);
    }

    public Out doGet() throws IOException {
        return doGet(this.params);
    }

    public JSONObject doGetOne() {
        JSONObject jsonObject;
        try {
            return (JSONObject) doGet(this.params);
        } catch (ConnectException connectException) {
            jsonObject = new JSONObject();
            jsonObject.put("CODE", "4002");
            jsonObject.put("MSG", "ConnectException:" + connectException.getLocalizedMessage());
        } catch (java.net.SocketTimeoutException timeoutException) {
            jsonObject = new JSONObject();
            jsonObject.put("CODE", "4003");
            jsonObject.put("MSG", "SocketTimeoutException:" + timeoutException.getLocalizedMessage());
        } catch (Exception e) {
            logger.error("url:{} param:{}", url, params, e);
            jsonObject = new JSONObject();
            jsonObject.put("CODE", "4004");
            jsonObject.put("MSG", "Exception:" + e.getLocalizedMessage());
        }
        return jsonObject;
    }

    public JSONObject doGetRetry() {
        JSONObject jsonObject = doGetOne();
        if (jsonObject.containsKey("CODE")) {
            if (jsonObject.get("CODE").toString().contains("400")) {
                for (int i = 0; i < retry; i++) {
                    jsonObject = doGetOne();
                    if (jsonObject != null && (jsonObject.containsKey("CODE") && !jsonObject.get("CODE").toString().contains("400"))) {
                        return jsonObject;
                    }
                }
                return jsonObject;
            }
        }
        return jsonObject;
    }
}
package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public class GetMethod implements GenerateHttpMethod {
    @Override
    public HttpRequestBase generateHttpMethod() {
        HttpGet httpGet = new HttpGet(urlStr);
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
                + " Chrome/44.0.2403.157 UBrowser/5.5.6743.209 Safari/537.36";
        httpGet.addHeader("User-agent", userAgent);
        httpGet.addHeader("Connection", "Keep-Alive");
        httpGet.setConfig(requestConfig);
        return httpGet;
    }

    @Override
    public <In, Out> HttpGet generateHttpMethod(RequestTemplate<In, Out> inOutRequestTemplate) {
        HttpGet httpGet = new HttpGet(inOutRequestTemplate.getUrl());
        String userAgent = inOutRequestTemplate.getUserAgent();
        httpGet.addHeader("User-agent", userAgent);
        if (inOutRequestTemplate.getKeepAlive()) {
            httpGet.addHeader("Connection", "Keep-Alive");
        }
        httpGet.setConfig(inOutRequestTemplate.getRequestConfig());
        return httpGet;
    }
}

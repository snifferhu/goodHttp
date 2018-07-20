package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpGet;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public class GetMethod implements GenerateHttpMethod {

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

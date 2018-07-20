package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpPut;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public class PutMethod implements GenerateHttpMethod {
    @Override
    public <In, Out> HttpPut generateHttpMethod(RequestTemplate<In, Out> inOutRequestTemplate) {
        HttpPut httpPut = new HttpPut(inOutRequestTemplate.getUrl());
        String userAgent = inOutRequestTemplate.getUserAgent();
        httpPut.addHeader("User-agent", userAgent);
        if (inOutRequestTemplate.getKeepAlive()) {
            httpPut.addHeader("Connection", "Keep-Alive");
        }
        httpPut.setConfig(inOutRequestTemplate.getRequestConfig());
        return httpPut;
    }
}

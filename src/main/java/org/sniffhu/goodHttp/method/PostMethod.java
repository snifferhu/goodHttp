package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpPost;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public class PostMethod implements GenerateHttpMethod {
    @Override
    public <In, Out> HttpPost generateHttpMethod(RequestTemplate<In, Out> inOutRequestTemplate) {
        HttpPost httpPost = new HttpPost(inOutRequestTemplate.getUrl());
        String userAgent = inOutRequestTemplate.getUserAgent();
        httpPost.addHeader("User-agent", userAgent);
        if (inOutRequestTemplate.getKeepAlive()) {
            httpPost.addHeader("Connection", "Keep-Alive");
        }
        httpPost.setConfig(inOutRequestTemplate.getRequestConfig());
        return httpPost;
    }
}

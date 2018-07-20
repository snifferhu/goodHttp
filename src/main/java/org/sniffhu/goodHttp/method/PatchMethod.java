package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpPatch;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public class PatchMethod implements GenerateHttpMethod {
    @Override
    public <In, Out> HttpPatch generateHttpMethod(RequestTemplate<In, Out> inOutRequestTemplate) {
        HttpPatch httpPatch = new HttpPatch(inOutRequestTemplate.getUrl());
        String userAgent = inOutRequestTemplate.getUserAgent();
        httpPatch.addHeader("User-agent", userAgent);
        if (inOutRequestTemplate.getKeepAlive()) {
            httpPatch.addHeader("Connection", "Keep-Alive");
        }
        httpPatch.setConfig(inOutRequestTemplate.getRequestConfig());
        return httpPatch;
    }
}

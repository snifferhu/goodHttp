package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpDelete;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public class DeleteMethod implements GenerateHttpMethod {


    @Override
    public <In, Out> HttpDelete generateHttpMethod(RequestTemplate<In, Out> inOutRequestTemplate) {
        HttpDelete httpDelete = new HttpDelete(inOutRequestTemplate.getUrl());
        String userAgent = inOutRequestTemplate.getUserAgent();
        httpDelete.addHeader("User-agent", userAgent);
        if (inOutRequestTemplate.getKeepAlive()) {
            httpDelete.addHeader("Connection", "Keep-Alive");
        }
        httpDelete.setConfig(inOutRequestTemplate.getRequestConfig());
        return httpDelete;
    }
}

package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpPut;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public class PutMethod<In, Out> extends AbstractGenerateHttpMethod<In, Out> {
    public PutMethod(RequestTemplate<In, Out> inOutRequestTemplate) {
        super(inOutRequestTemplate);
    }

    @Override
    public  HttpPut generateHttpMethod() {
        HttpPut httpPut = new HttpPut(getInOutRequestTemplate().getUrl());
        if (getInOutRequestTemplate().getKeepAlive()) {
            httpPut.addHeader("Connection", "Keep-Alive");
        }
        httpPut.setConfig(getInOutRequestTemplate().getRequestConfig());
        return httpPut;
    }
}

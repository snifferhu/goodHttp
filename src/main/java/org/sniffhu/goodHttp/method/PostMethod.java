package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpPost;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public class PostMethod<In, Out> extends AbstractGenerateHttpMethod<In, Out> {
    @Override
    public <In, Out> HttpPost generateHttpMethod() {
        HttpPost httpPost = new HttpPost(getInOutRequestTemplate().getUrl());
        if (getInOutRequestTemplate().getKeepAlive()) {
            httpPost.addHeader("Connection", "Keep-Alive");
        }
        httpPost.setConfig(getInOutRequestTemplate().getRequestConfig());
        return httpPost;
    }

    public PostMethod(RequestTemplate<In, Out> inOutRequestTemplate) {
        super(inOutRequestTemplate);
    }
}

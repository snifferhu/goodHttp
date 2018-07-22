package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpGet;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public class GetMethod extends AbstractGenerateHttpMethod {

    public GetMethod(RequestTemplate requestTemplate) {
        super(requestTemplate);
    }

    @Override
    public <In, Out> HttpGet generateHttpMethod() {
        HttpGet httpGet = new HttpGet(getInOutRequestTemplate().getUrl());
        if (getInOutRequestTemplate().getKeepAlive()) {
            httpGet.addHeader("Connection", "Keep-Alive");
        }
        httpGet.setConfig(getInOutRequestTemplate().getRequestConfig());
        return httpGet;
    }


}

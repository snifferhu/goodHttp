package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpPatch;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public class PatchMethod<In, Out> extends AbstractGenerateHttpMethod<In, Out> {
    public PatchMethod(RequestTemplate<In, Out> inOutRequestTemplate) {
        super(inOutRequestTemplate);
    }

    @Override
    public HttpPatch generateHttpMethod() {
        HttpPatch httpPatch = new HttpPatch(getInOutRequestTemplate().getUrl());
        if (getInOutRequestTemplate().getKeepAlive()) {
            httpPatch.addHeader("Connection", "Keep-Alive");
        }
        httpPatch.setConfig(getInOutRequestTemplate().getRequestConfig());
        return httpPatch;
    }


}

package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpDelete;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public class DeleteMethod <In, Out> extends AbstractGenerateHttpMethod <In, Out> {


    public DeleteMethod(RequestTemplate requestTemplate) {
        super(requestTemplate);
    }

    @Override
    public HttpDelete generateHttpMethod() {
        HttpDelete httpDelete = new HttpDelete(getInOutRequestTemplate().getUrl());
        if (getInOutRequestTemplate().getKeepAlive()) {
            httpDelete.addHeader("Connection", "Keep-Alive");
        }
        httpDelete.setConfig(getInOutRequestTemplate().getRequestConfig());
        return httpDelete;
    }
}

package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * Created by Sniff on 2018/5/9.
 */
public interface GenerateHttpMethod {
    public HttpRequestBase generateHttpMethod();

    <In, Out> HttpGet generateHttpMethod(RequestTemplate<In, Out> inOutRequestTemplate);
}

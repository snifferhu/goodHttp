package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by Sniff on 2018/5/9.
 */
public interface GenerateHttpMethod {

    <In, Out> HttpRequestBase generateHttpMethod();
}

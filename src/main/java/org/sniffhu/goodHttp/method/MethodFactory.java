package org.sniffhu.goodHttp.method;

import org.apache.http.client.methods.*;
import org.sniffhu.goodHttp.RequestTemplate;

/**
 * @auth snifferhu
 * @date 2018/7/21 14:03
 */
public class MethodFactory {
    public static HttpGet generateGetMethod(RequestTemplate inOutRequestTemplate) {
        return new GetMethod(inOutRequestTemplate).generateHttpMethod();
    }

    public static HttpPost generatePostMethod(RequestTemplate inOutRequestTemplate) {
        return new PostMethod(inOutRequestTemplate).generateHttpMethod();
    }

    public static HttpPatch generatePatchMethod(RequestTemplate inOutRequestTemplate) {
        return new PatchMethod(inOutRequestTemplate).generateHttpMethod();
    }

    public static HttpPut generatePutMethod(RequestTemplate inOutRequestTemplate) {
        return new PutMethod(inOutRequestTemplate).generateHttpMethod();
    }

    public static HttpDelete generateDeteteMethod(RequestTemplate inOutRequestTemplate) {
        return new DeleteMethod(inOutRequestTemplate).generateHttpMethod();
    }
}

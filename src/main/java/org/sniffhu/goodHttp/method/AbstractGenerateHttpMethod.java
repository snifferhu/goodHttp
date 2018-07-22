package org.sniffhu.goodHttp.method;

import org.sniffhu.goodHttp.RequestTemplate;
import org.sniffhu.goodHttp.util.StringUtils;

/**
 * @auth snifferhu
 * @date 2018/7/22 19:46
 */
public abstract class AbstractGenerateHttpMethod<In, Out> implements GenerateHttpMethod {
    private RequestTemplate<In, Out> inOutRequestTemplate;


    public  AbstractGenerateHttpMethod(RequestTemplate<In, Out> inOutRequestTemplate){
        if (StringUtils.isEmpty(inOutRequestTemplate.getUrl())){
            throw new IllegalArgumentException("request url can't been empty");
        }
        this.inOutRequestTemplate = inOutRequestTemplate;
    }

    public RequestTemplate<In, Out> getInOutRequestTemplate() {
        return inOutRequestTemplate;
    }

    public AbstractGenerateHttpMethod setInOutRequestTemplate(RequestTemplate<In, Out> inOutRequestTemplate) {
        this.inOutRequestTemplate = inOutRequestTemplate;
        return this;
    }
}

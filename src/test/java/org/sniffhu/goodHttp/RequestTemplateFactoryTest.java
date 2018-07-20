package org.sniffhu.goodHttp;

import org.junit.Before;
import org.junit.Test;

/**
 * @auth snifferhu
 * @date 2018/7/19 23:12
 */
public class RequestTemplateFactoryTest {

    private RequestTemplateFactory requestTemplateFactory;
    @Before
    public void init(){
        requestTemplateFactory = new RequestTemplateFactory();
    }

    @Test
    public void genRequestTemplate() throws Exception {
        System.out.println(requestTemplateFactory.genRequestTemplate().url("http://www.baidu.com").doGetOne());
    }

}
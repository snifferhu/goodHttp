package org.sniffhu.goodHttp;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

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
        System.out.println(requestTemplateFactory.setStringResponse().genRequestTemplate().url("http://127.0.0.1:2000/add").setParams(new HashMap<String,String>(){{
            put("a","4");
            put("b","1");
        }}).doGetOne());
    }

}
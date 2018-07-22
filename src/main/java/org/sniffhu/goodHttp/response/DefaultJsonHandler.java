package org.sniffhu.goodHttp.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DefaultJsonHandler extends AbstractResponseHandler<JSONObject> {
    private final Logger logger = LoggerFactory.getLogger(DefaultJsonHandler.class);
    private byte[] mBuffer;

    public DefaultJsonHandler() {
        mBuffer = new byte[super.getBufSize()];
    }

    @Override
    protected JSONObject readObject(byte[] transTemp) throws IOException {
        String response = "";
        try {
            response = new String(transTemp, super.getCharset());
            return JSON.parseObject(response);
        } catch (JSONException jsonException) {
            logger.error("response parse failed. response: {}", response);
            throw jsonException;
        }
    }

    @Override
    public DefaultJsonHandler charset(String charset) {
        this.setCharset(charset);
        return this;
    }
}
package org.sniffhu.goodHttp.response;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.IOException;

public class DefaultJsonHandler extends AbstractResponseHandler<JSONObject> {
    private byte[] mBuffer;

    public DefaultJsonHandler() {
        mBuffer = new byte[super.getBufSize()];
    }

    @Override
    protected JSONObject readObject(byte[] transTemp) throws IOException {
        try {
            return JSONObject.fromObject(new String(transTemp, super.getCharset()));
        } catch (JSONException jsonException) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CODE", "4001");
            jsonObject.put("MSG", "JSONException:" + jsonException.getLocalizedMessage() + "|" + new String(transTemp));
            return jsonObject;
        }
    }

    @Override
    public DefaultJsonHandler charset(String charset) {
        this.setCharset(charset);
        return this;
    }
}
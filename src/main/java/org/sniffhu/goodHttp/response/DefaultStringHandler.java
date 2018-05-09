package org.sniffhu.goodHttp.response;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

@Component
public class DefaultStringHandler extends AbstractResponseHandler<String> {
    private byte[] mBuffer;

    public DefaultStringHandler() {
        mBuffer = new byte[super.getBufSize()];
    }

    @Override
    protected String readObject(byte[] transTemp) {
        try {
            return new String(transTemp, super.getCharset());
        } catch (UnsupportedEncodingException jsonException) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CODE", "4001");
            jsonObject.put("MSG", "JSONException:" + jsonException.getLocalizedMessage() + "|" + new String(transTemp));
            return jsonObject.toString();
        }
    }

    @Override
    public DefaultStringHandler charset(String charset) {
        this.setCharset(charset);
        return this;
    }

    private final void close(OutputStream o) {
        if (o != null) {
            try {
                o.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
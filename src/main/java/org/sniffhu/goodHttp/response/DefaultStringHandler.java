package org.sniffhu.goodHttp.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class DefaultStringHandler extends AbstractResponseHandler<String> {
    private final Logger logger = LoggerFactory.getLogger(DefaultStringHandler.class);
    private byte[] mBuffer;

    public DefaultStringHandler() {
        mBuffer = new byte[super.getBufSize()];
    }

    @Override
    protected String readObject(byte[] transTemp) throws UnsupportedEncodingException {
        try {
            return new String(transTemp, super.getCharset());
        } catch (UnsupportedEncodingException e) {
            throw  e;
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
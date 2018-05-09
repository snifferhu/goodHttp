package org.sniffhu.goodHttp.response;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.dom4j.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class AbstractResponseHandler<T> implements ResponseHandler<T> {
    private final static int BUF_SIZE = 500;
    private byte[] mBuffer = new byte[BUF_SIZE];
    private String charset = UTF_8.toString();

    protected int getBufSize() {
        return BUF_SIZE;
    }

    protected String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public interface FailureCallback {
        public void onException(Throwable msg);

        public void onFailure(int statusCode, HttpResponse response);
    }

    private FailureCallback mCallback;

    public void setFailureCallbak(FailureCallback c) {
        mCallback = c;
    }

    protected abstract T readObject(byte[] transTemp) throws IOException, DocumentException;

    public abstract AbstractResponseHandler charset(String charset);

    public T handleResponse(HttpResponse response) {
        if (response != null) {
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                try(InputStream is = response.getEntity().getContent()) {
                    if (is != null) {
                        int bytesRead = -1;
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        while ((bytesRead = is.read(mBuffer)) != -1) {
                            baos.write(mBuffer, 0, bytesRead);
                        }
                        byte[] transTemp = baos.toByteArray();
                        return readObject(transTemp);
                    }
                } catch (Exception e) {
                    if (mCallback != null)
                        mCallback.onException(e);
                    e.printStackTrace();
                }
            } else {
                if (mCallback != null)
                    mCallback.onFailure(statusCode, response);
            }
        } else {
            if (mCallback != null) {
                mCallback.onException(new IllegalArgumentException("responseHandler is null"));
            }
        }
        return null;
    }
}

package org.sniffhu.goodHttp.response;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.UnsupportedEncodingException;

public class DefaultXmlHandler extends AbstractResponseHandler<Document> {

    @Override
    protected Document readObject(byte[] transTemp)
            throws UnsupportedEncodingException, DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(new String(transTemp, super.getCharset()));
    }

    @Override
    public DefaultXmlHandler charset(String charset) {
        this.setCharset(charset);
        return this;
    }
}
package org.sniffhu.goodHttp.config;


public class HttpConfig {
    private Integer poolMaxTotal = 500;
    private Integer defaultConn = 50;
    private Integer socketTimeout = 5000;
    private Integer connTimeout = 5000;
    private Integer connReqTimeout = 5000;


    public Integer getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public HttpConfig setPoolMaxTotal(Integer poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
        return this;
    }

    public Integer getDefaultConn() {
        return defaultConn;
    }

    public HttpConfig setDefaultConn(Integer defaultConn) {
        this.defaultConn = defaultConn;
        return this;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public HttpConfig setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public Integer getConnTimeout() {
        return connTimeout;
    }

    public HttpConfig setConnTimeout(Integer connTimeout) {
        this.connTimeout = connTimeout;
        return this;
    }

    public Integer getConnReqTimeout() {
        return connReqTimeout;
    }

    public HttpConfig setConnReqTimeout(Integer connReqTimeout) {
        this.connReqTimeout = connReqTimeout;
        return this;
    }
}

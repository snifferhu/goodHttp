package org.sniffhu.goodHttp.config;


public class HttpConfig {
    private Integer poolMaxTotal;
    private Integer defaultConn;
    private Integer socketTimeout;
    private Integer connTimeout;
    private Integer connReqTimeout;

    public Integer getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(Integer poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public Integer getDefaultConn() {
        return defaultConn;
    }

    public void setDefaultConn(Integer defaultConn) {
        this.defaultConn = defaultConn;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(Integer connTimeout) {
        this.connTimeout = connTimeout;
    }

    public Integer getConnReqTimeout() {
        return connReqTimeout;
    }

    public void setConnReqTimeout(Integer connReqTimeout) {
        this.connReqTimeout = connReqTimeout;
    }
}

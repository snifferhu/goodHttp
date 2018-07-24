package org.sniffhu.goodHttp.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auth snifferhu
 * @date 2018/7/23 22:00
 */
public class FailureCallbackHandler<T> implements AbstractResponseHandler.FailureCallback<T> {
    private final Logger logger = LoggerFactory.getLogger(DefaultStringHandler.class);

    @Override
    public void onException(Throwable msg, T response) {
        logger.error("failure responseMsg: {}", response, msg);
    }

    @Override
    public void onFailure(int statusCode, T response) {
        logger.error("Response statusCode: {} ,responseMsg: {}", statusCode, response);
    }
}

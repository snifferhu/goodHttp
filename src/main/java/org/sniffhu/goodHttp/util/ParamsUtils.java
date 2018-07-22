package org.sniffhu.goodHttp.util;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniffhu.goodHttp.exception.ParseException;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

/**
 * @auth snifferhu
 * @date 2018/7/22 19:06
 */
public class ParamsUtils {
    private static final Logger logger = LoggerFactory.getLogger(ParamsUtils.class);

    public static String beanToParamURL(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj == null) {
            return StringUtils.EMPTY;
        } else if (obj instanceof Map) {
            return parseToURL((Map) obj);
        } else {
            try {
                return parseToURL(BeanUtils.describe(obj));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.warn("parse failed ", e);
                throw new ParseException(obj.toString(), e);
            }
        }
    }

    private static String parseToURL(Map obj) {
        Object[] keys = obj.keySet().toArray();
        StringBuilder sbuilder = new StringBuilder();
        for (int i = 0, size = keys.length; i < size; i++) {
            if (obj.get(keys[i]) != null) {
                sbuilder.append(keys[i]).append("=").append(String.valueOf(obj.get(keys[i])));
            } else if (obj.get(keys[i]) instanceof Date) {
                sbuilder.append(keys[i]).append("=").append(DateUtils.dateToStr((Date) obj.get(keys[i])));
            }
            if (i != size - 1) {
                sbuilder.append("&");
            }
        }
        return sbuilder.toString();
    }
}

package org.sniffhu.goodHttp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auth snifferhu
 * @date 2018/7/22 19:21
 */
public class DateUtils {
    private static final String yyyyMMddhhmmssSSS = "yyyy-MM-dd hh:mm:ss SSS Z";
    public static String dateToStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMddhhmmssSSS);
        return sdf.format(date);
    }
}

package org.sniffhu.goodHttp.util;

/**
 * Created by Sniff on 2018/5/9.
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}

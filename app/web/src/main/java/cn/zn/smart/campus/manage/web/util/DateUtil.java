package cn.zn.smart.campus.manage.web.util;

import java.util.Date;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/22 00:58
 */
public class DateUtil {
    public DateUtil() {
    }

    public static Date getDurationDate(long duration) {
        long expireTime = System.currentTimeMillis() + duration * 1000L;
        return new Date(expireTime);
    }
}

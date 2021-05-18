package cn.zn.smart.campus.manage.biz.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/16 14:29
 */
public class ObjMapSwapUtil {
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (Objects.nonNull(value)){
                map.put(fieldName, value);
            }
        }
        map.remove("serialVersionUID");
        return map;
    }
}

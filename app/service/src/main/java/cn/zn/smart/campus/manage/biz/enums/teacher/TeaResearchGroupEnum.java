package cn.zn.smart.campus.manage.biz.enums.teacher;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 教师教研组枚举
 * @Author: zhangnan
 * @Date: 2021/05/18 13:43
 */
public enum TeaResearchGroupEnum {
    /**
     */
    PS("小学部","1001"),
    JHS("初中部","1002"),
    SHS("高中部w","1003");

    /**
     * 描述
     */
    private final String value;
    /**
     * 编号
     */
    private final String no;

    private static List<TeaResearchGroupEnum> teaResearchGroupEnums= Arrays.asList(values());

    TeaResearchGroupEnum(String value, String no) {
        this.value = value;
        this.no = no;
    }

    public String getValue() {
        return value;
    }

    public String getNo() {
        return no;
    }

    public static TeaResearchGroupEnum getByValue(String value){
        if (StringUtils.isBlank(value)){
            return null;
        }
        return teaResearchGroupEnums.stream().filter(p -> p.getValue().equals(value)).findFirst().orElse(null);
    }
}

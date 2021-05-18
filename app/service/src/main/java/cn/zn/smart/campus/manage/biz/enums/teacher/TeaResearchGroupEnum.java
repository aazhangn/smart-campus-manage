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
     * 第一位：1小学、2初中、3高中
     * <p>
     * 第二位：1文科、2理科
     * </p>
     * 后两位：01语文、02数学、03英语....
     */
    JHS_CHINESE("初中语文","2101"),
    JHS_MATH("初中数学","2201"),
    JHS_ENGLISH("初中英语","2103");

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

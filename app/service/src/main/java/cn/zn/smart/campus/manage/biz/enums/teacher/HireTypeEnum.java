package cn.zn.smart.campus.manage.biz.enums.teacher;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 聘用类型枚举
 * @Author: zhangnan
 * @Date: 2021/05/17 17:45
 */
public enum HireTypeEnum {
    /**
     * 编制
     */
    PERMANENT("01","编制"),
    /**
     * 合同制
     */
    CONTRACT("02","合同制");

    /**
     * 编号
     */
    private final String no;
    /**
     * 描述
     */
    private final String desc;

    private static List<HireTypeEnum> hireTypeEnums = Arrays.asList(values());

    HireTypeEnum(String no, String desc) {
        this.no = no;
        this.desc = desc;
    }

    public String getNo() {
        return no;
    }

    public String getDesc() {
        return desc;
    }

    public static HireTypeEnum getByDesc(String desc){
        if (StringUtils.isBlank(desc)){
            return null;
        }
        return hireTypeEnums.stream().filter(p -> p.getDesc().equals(desc)).findFirst().orElse(null);
    }
}




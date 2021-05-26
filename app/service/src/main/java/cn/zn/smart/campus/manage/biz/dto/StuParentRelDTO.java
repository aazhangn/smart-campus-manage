package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 15:34
 */
@Data
public class StuParentRelDTO implements Serializable {

    private static final long serialVersionUID = -5390179814902342892L;
    /**
     * 学号
     */
    private String studentId;
    /**
     * 亲子关系
     */
    private String relationshipType;


}

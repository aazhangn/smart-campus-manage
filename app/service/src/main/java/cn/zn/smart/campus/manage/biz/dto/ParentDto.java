package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 17:06
 */
@Data
public class ParentSaveDto {


    /**
     * 家长id
     */
    private String parentId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系电话
     */
    private String phoneNumber;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 亲子关系
     */
    private String relationshipType;
}

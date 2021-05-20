package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 17:47
 */
@Data
public class StudentDTO {

    /**
     * 学号
     */
    private String studentId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 籍贯
     */
    private String nativePalce;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 所属班级id
     */
    private String classId;

    /**
     * 手机号
     */
    private String phoneNumber;
}

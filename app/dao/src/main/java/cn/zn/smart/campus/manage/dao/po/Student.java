package cn.zn.smart.campus.manage.dao.po;

import cn.zn.smart.campus.manage.dao.po.base.BaseLogicDeletePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangnan
 * @since 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

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

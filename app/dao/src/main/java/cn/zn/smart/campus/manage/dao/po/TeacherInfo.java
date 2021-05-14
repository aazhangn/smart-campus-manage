package cn.zn.smart.campus.manage.dao.po;

import cn.zn.smart.campus.manage.dao.po.base.BaseLogicDeletePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangnan
 * @since 2021-05-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherInfo extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    private String teacherId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系方式
     */
    private String phoneNumber;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 职称
     */
    private String title;

    /**
     * 地址
     */
    private String address;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 入职时间
     */
    private Date hiredateTime;

    /**
     * 教授科目
     */
    private String teachSubjects;


}

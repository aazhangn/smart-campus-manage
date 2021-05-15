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
public class ClassInfo extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

    /**
     * 班级id
     */
    private String classId;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 年级
     */
    private String grade;

    /**
     * 班级编号
     */
    private String classNo;

    /**
     * 班主任工号
     */
    private String admiTeacherId;

    /**
     * 课程参数（授课信息）
     */
    private String classParam;


}

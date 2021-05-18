package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/18 16:20
 */
@Data
public class ClassDto implements Serializable {
    private static final long serialVersionUID = 2389950498724532705L;

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

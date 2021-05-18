package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 教师Dto
 * @Author: zhangnan
 * @Date: 2021/05/16 01:28
 */
@Data
public class TeacherDto implements Serializable {

    private static final long serialVersionUID = 1175636479685012395L;
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
     * 聘用类型
     */
    private String hireType;

    /**
     * 所在教研组
     */
    private String teaResearchGroup;

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
    private Date hiredate;

    /**
     * 教授科目
     */
    private String teachSubjects;
}

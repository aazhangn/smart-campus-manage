package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 15:51
 */
@Data
public class AnswerDto implements Serializable {

    private static final long serialVersionUID = -2653636112483315337L;
    /**
     * 学生_作业_id
     */
    private String stuAssRelId;

    /**
     * 作业id
     */
    private String assignmentId;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 答案
     */
    private String answer;

    /**
     * 得分
     */
    private Double score;

    /**
     * 状态
     */
    private Integer status;
}

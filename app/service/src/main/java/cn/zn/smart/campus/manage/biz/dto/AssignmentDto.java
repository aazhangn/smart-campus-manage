package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/18 21:03
 */
@Data
public class AssignmentDto implements Serializable {

    private static final long serialVersionUID = -5459946021130634206L;
    /**
     * 作业id
     */
    private String assignmentId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 所属科目
     */
    private String subject;

    /**
     * 是否客观题
     */
    private Integer isObjective;

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 参考答案
     */
    private String suggestedAnswer;

    /**
     * 发布人工号
     */
    private String teacherId;
}

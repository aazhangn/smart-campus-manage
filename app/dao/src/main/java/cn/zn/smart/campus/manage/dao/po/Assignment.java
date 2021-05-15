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
public class Assignment extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

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
    private String desc;

    /**
     * 参考答案
     */
    private String suggestedAnswer;

    /**
     * 发布人工号
     */
    private String teacherId;


}

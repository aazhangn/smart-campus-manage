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
public class StuAssRel extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

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

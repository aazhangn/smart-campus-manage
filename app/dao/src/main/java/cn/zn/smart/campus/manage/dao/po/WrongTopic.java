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
public class WrongTopic extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

    /**
     * 错题id
     */
    private String wrongTopicId;

    /**
     * 学生_作业id
     */
    private String stuAssRelId;

    /**
     * 类型
     */
    private String type;

    /**
     * 掌握情况
     */
    private Integer mesteryStatus;

    /**
     * 题目id
     */
    private String assignmentId;


}

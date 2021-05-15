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
public class StuParentRel extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

    /**
     * 学生_家长id
     */
    private String stuParRelId;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 家长id
     */
    private String parentId;

    /**
     * 亲子关系
     */
    private String relationshipType;


}

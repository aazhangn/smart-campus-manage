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
public class Facility extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

    /**
     * 设施id
     */
    private String facilityId;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 管理员工号
     */
    private String administratorId;

    /**
     * 借用状态
     */
    private String status;


}

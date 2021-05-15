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
public class Parent extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

    /**
     * 家长id
     */
    private String parentId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系电话
     */
    private String phoneNumber;


}

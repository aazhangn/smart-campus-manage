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
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondHandGood extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

    /**
     * 物品id
     */
    private String goodId;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型
     */
    private String type;

    /**
     * 原价
     */
    private Double originalPrice;

    /**
     * 现价
     */
    private Double currentPrice;

    /**
     * 发布人学号
     */
    private String issueStudentId;

    /**
     * 状态
     */
    private String status;

    /**
     * 详情图url
     */
    private String detailPicUrls;


}

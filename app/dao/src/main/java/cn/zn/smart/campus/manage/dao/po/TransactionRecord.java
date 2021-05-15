package cn.zn.smart.campus.manage.dao.po;

import java.util.Date;
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
public class TransactionRecord extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

    /**
     * 交易id
     */
    private String transactionId;

    /**
     * 物品id
     */
    private String goodId;

    /**
     * 发布人学号
     */
    private String studentId;

    /**
     * 成交时间
     */
    private Date transactionTime;

    /**
     * 成交价格
     */
    private Double transactionPrice;


}

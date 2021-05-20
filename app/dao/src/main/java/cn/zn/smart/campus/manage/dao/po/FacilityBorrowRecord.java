package cn.zn.smart.campus.manage.dao.po;

import cn.zn.smart.campus.manage.dao.po.base.BaseLogicDeletePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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
public class FacilityBorrowRecord extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

    /**
     * 借用记录id
     */
    private String borrowRecordId;

    /**
     * 设施id
     */
    private String facilityId;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 借用时间
     */
    private Date borrowTime;

    /**
     * 归还时间
     */
    private Date returnTime;


}

package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/06/13 13:12
 */
@Data
public class FacilityBorrowRecordDTO implements Serializable {
    private static final long serialVersionUID = -5329287222238920158L;

    private String borrowRecordId;

    /**
     * 设施id
     */
    private String facilityId;

    /**
     * 设施名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;


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

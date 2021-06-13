package cn.zn.smart.campus.manage.web.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/06/12 20:35
 */
@Data
public class FacilityBorrowParam implements Serializable {
    private static final long serialVersionUID = -9140730248851525505L;

    /**
     * 设施id
     */
    private String facilityId;
    /**
     * 借用人学号
     */
    private String studentId;
}

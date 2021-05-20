package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 15:17
 */
@Data
public class FacilityDTO implements Serializable {

    private static final long serialVersionUID = -3681246685763857139L;
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

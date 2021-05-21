package cn.zn.smart.campus.manage.biz.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 16:29
 */
@Data
public class AssignmentMarkParam implements Serializable {

    private static final long serialVersionUID = -4588617241812769326L;
    /**
     * 作业id
     */
    private String stuAssRelId;

    /**
     * 分数
     */
    private Double score;

    /**
     * 备注
     */
    private String extra;
}

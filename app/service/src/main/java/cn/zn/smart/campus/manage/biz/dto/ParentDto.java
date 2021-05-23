package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 17:06
 */
@Data
public class ParentDto implements Serializable {
    private static final long serialVersionUID = 8413135638830986598L;
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

    /**
     * 亲子关系
     */
    private List<StuParentRelDTO> rels;
}

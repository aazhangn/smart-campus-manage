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
public class Notice extends BaseLogicDeletePo {

    private static final long serialVersionUID = 1L;

    /**
     * 通知id
     */
    private String noticeId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 类型
     */
    private String type;

    /**
     * 消息接收参数
     */
    private String noticeReceiveParam;

    /**
     * 发布人工号
     */
    private String teacherId;


}

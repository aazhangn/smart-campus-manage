package cn.zn.smart.campus.manage.biz.dto;

import cn.zn.smart.campus.manage.biz.param.NoticeReceiveParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 11:02
 */
@Data
public class NoticeDTO implements Serializable {


    private static final long serialVersionUID = 1226936603556366078L;
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
    private NoticeReceiveParam noticeReceiveParam;

    /**
     * 发布人工号
     */
    private String teacherId;
}

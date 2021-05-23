package cn.zn.smart.campus.manage.biz.param;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 12:02
 */
@Data
public class NoticeReceiveParam {
    /**
     * {@link cn.zn.smart.campus.manage.biz.enums.notice.ReceiveScopeEnum}
     */
    private String scope;
    /**
     * 具体接收对象
     */
    private List<String> receivers;
}

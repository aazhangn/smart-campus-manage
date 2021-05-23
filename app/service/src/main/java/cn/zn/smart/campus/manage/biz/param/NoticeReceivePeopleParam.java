package cn.zn.smart.campus.manage.biz.param;

import lombok.Data;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 12:29
 */
@Data
public class NoticeReceivePeopleParam {

    /**
     * 接收人角色{@link cn.zn.smart.campus.manage.biz.enums.notice.ReceivePeopleRoleEnum}
     */
    private String role;

    /**
     * 接收人id
     */
    private String receivePeopleId;
}

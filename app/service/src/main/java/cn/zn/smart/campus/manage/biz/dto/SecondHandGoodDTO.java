package cn.zn.smart.campus.manage.biz.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/23 11:12
 */
@Data
public class SecondHandGoodDTO implements Serializable {
    private static final long serialVersionUID = 4009722133157901530L;


    /**
     * 物品id
     */
    private String goodId;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型
     */
    private String type;

    /**
     * 原价
     */
    private Double originalPrice;

    /**
     * 现价
     */
    private Double currentPrice;

    /**
     * 发布人学号
     */
    private String issueStudentId;

    /**
     * 状态
     */
    private String status;

    /**
     * 详情图url
     */
    private String detailPicUrls;
}

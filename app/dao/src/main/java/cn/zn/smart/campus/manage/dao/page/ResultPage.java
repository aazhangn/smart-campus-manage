package cn.zn.smart.campus.manage.dao.page;

import lombok.Data;

import java.util.List;

/**
 * @Description: 查询结果页
 * @Author: zhangnan
 * @Date: 2021/05/16 01:06
 */
@Data
public class ResultPage<T> {
    /**
     * 当前页码
     */
    private long current;
    /**
     * 分页size
     */
    private long size;
    /**
     * 数据总量
     */
    private long total;
    /**
     * 总页数
     */
    private long pages;
    /**
     * 查询结果数据
     */
    private List<T> records;
}

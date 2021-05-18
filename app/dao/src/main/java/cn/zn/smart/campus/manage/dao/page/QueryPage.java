package cn.zn.smart.campus.manage.dao.page;

import lombok.Data;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/16 00:33
 */
@Data
public class QueryPage {
    private static final Integer DEFAULT_CURRENT = 1;
    private static final Integer DEFAULT_SIZE = 10;
    private Integer current;
    private Integer size;

    public QueryPage() {
        this.current = DEFAULT_CURRENT;
        this.size = DEFAULT_SIZE;
    }

    public QueryPage(Integer current) {
        this.current = current;
        this.size = DEFAULT_SIZE;
    }

    public QueryPage(Integer current, Integer size) {
        this.current = current;
        this.size = size;
    }
}

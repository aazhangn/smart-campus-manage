package cn.zn.smart.campus.manage.web.param;

import cn.zn.smart.campus.manage.dao.page.QueryPage;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 12:08
 */
public class PageParam<T> implements Serializable {

    private static final long serialVersionUID = 4788014712391001551L;
    private QueryPage page;
    private  T queryCon;

    public QueryPage getPage() {
        return page;
    }
    public T getQueryCon() {
        return queryCon;
    }
}

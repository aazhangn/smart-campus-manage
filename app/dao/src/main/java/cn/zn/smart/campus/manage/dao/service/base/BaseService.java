package cn.zn.smart.campus.manage.dao.service.base;

import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 数据操作service父类接口
 * @Author: zhangnan
 * @Date: 2021/05/14 18:27
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 根据某个字段批量更新
     * @param entityList
     * @param fieldName
     * @return
     */
    public boolean updateBatchByCondition(List<T> entityList,String fieldName);

    /**
     * 根据实体id批量删除
     * @param entityIdList
     * @param entityIdFieldName
     * @return
     */
    public boolean deleteBatchByEntityId(List<String> entityIdList,String entityIdFieldName);

    /**
     * 根据条件查询
     * @param entityId
     * @param entityIdFieldName
     * @return
     */
    public T getByEntityId(String entityId,String entityIdFieldName);

    /**
     * 实体分页查询
     * @param page
     * @param queryMap
     * @return
     */
    public ResultPage<T> getEntityListByPage(QueryPage page, Map<String,Object> queryMap);

    /**
     * 根据id前缀查询最后一条数据
     * @param idPrefix
     * @param entityIdFieldName
     * @return
     */
    public T getLastOneByIdPrefix(String idPrefix,String entityIdFieldName);

}

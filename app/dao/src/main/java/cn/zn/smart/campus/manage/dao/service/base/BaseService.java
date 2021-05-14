package cn.zn.smart.campus.manage.dao.service.base;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * @param fieldName
     * @return
     */
    public T getByEntityId(String entityId,String entityIdFieldName);

}

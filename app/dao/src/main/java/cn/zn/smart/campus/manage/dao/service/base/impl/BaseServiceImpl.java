package cn.zn.smart.campus.manage.dao.service.base.impl;

import cn.zn.smart.campus.manage.dao.exception.DaoException;
import cn.zn.smart.campus.manage.dao.exception.ExceEnum;
import cn.zn.smart.campus.manage.dao.service.base.BaseService;
import cn.zn.smart.campus.manage.dao.util.StringTool;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @Description: 数据操作service父类实现
 * @Author: zhangnan
 * @Date: 2021/05/14 18:29
 */
@Slf4j
public abstract class BaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public boolean updateBatchByCondition(List<T> entityList,String fieldName){
        if (StringUtils.isBlank(fieldName)){
            throw new DaoException(ExceEnum.SYS_PARAM_EXCE.getCode(),"根据条件操作时，字段名不能为空");
        }
        boolean flag = false;
        UpdateWrapper<T> wrapper = new UpdateWrapper<>();
        for (T obj:entityList) {
            String fieldValue;
            try {
                Field field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                fieldValue = (String) field.get(obj);
            }catch (NoSuchFieldException|IllegalAccessException e){
                throw new DaoException(ExceEnum.SYS_PARAM_EXCE.getCode(),"根据条件操作时，字段取值异常");
            }
            wrapper.eq(StringTool.humpToLine(fieldName),fieldValue);
            boolean temFlag = this.update(obj,wrapper);
            //失败：日志记录
            if (!temFlag){
                log.info("\nbatch update by condition failed："+ obj.getClass().getName()+"\nfieldName:"+fieldName+"--"+JSON.toJSONString(obj));
            //成功：若flag为false则更新为true（只要有一次操作成功，则操作成功）
            }else if (!flag){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean deleteBatchByEntityId(List<String> entityIdList,String entityIdFieldName) {
        if (StringUtils.isBlank(entityIdFieldName)){
            throw new DaoException(ExceEnum.SYS_PARAM_EXCE.getCode(),"根据实体id操作时，字段名不能为空");
        }
        boolean flag = false;
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for (String entityId:entityIdList) {
            wrapper.eq(StringTool.humpToLine(entityIdFieldName),entityId);
            boolean temFlag = this.remove(wrapper);
            //失败：日志记录
            if (!temFlag){
                log.info("\nbatch delete by entityId failed：\nentityIdFieldName:"+entityIdFieldName+"--entityId:"+entityId);
                //成功：若flag为false则更新为true（只要有一次操作成功，则操作成功）
            }else if (!flag){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public T getByEntityId(String entityId,String entityIdFieldName) {
        if (StringUtils.isBlank(entityIdFieldName)){
            throw new DaoException(ExceEnum.SYS_PARAM_EXCE.getCode(),"根据条件操作时，字段名不能为空");
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.eq(StringTool.humpToLine(entityIdFieldName),entityId);
        return this.getOne(wrapper);
    }
}

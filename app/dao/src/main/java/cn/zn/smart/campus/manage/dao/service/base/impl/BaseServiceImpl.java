package cn.zn.smart.campus.manage.dao.service.base.impl;

import cn.zn.smart.campus.manage.dao.exception.DaoException;
import cn.zn.smart.campus.manage.dao.exception.ExceEnum;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.service.base.BaseService;
import cn.zn.smart.campus.manage.dao.util.StringTool;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 数据操作service父类实现
 * @Author: zhangnan
 * @Date: 2021/05/14 18:29
 */
@Slf4j
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public boolean updateBatchByCondition(List<T> entityList, String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            throw new DaoException(ExceEnum.SYS_PARAM_EXCE.getCode(), "根据条件操作时，字段名不能为空");
        }
        boolean flag = false;
        for (T obj : entityList) {
            String fieldValue;
            try {
                Field field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                fieldValue = (String) field.get(obj);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new DaoException(ExceEnum.SYS_PARAM_EXCE.getCode(), "根据条件操作时，字段取值异常");
            }
            UpdateWrapper<T> wrapper = new UpdateWrapper<>();
            wrapper.eq(StringTool.humpToLine(fieldName), fieldValue);
            boolean temFlag = this.update(obj, wrapper);
            //失败：日志记录
            if (!temFlag) {
                log.info("\nbatch update by condition failed：" + obj.getClass().getName() + "\nfieldName:" + fieldName + "--" + JSON.toJSONString(obj));
                //成功：若flag为false则更新为true（只要有一次操作成功，则操作成功）
            } else if (!flag) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean deleteBatchByEntityId(List<String> entityIdList, String entityIdFieldName) {
        if (StringUtils.isBlank(entityIdFieldName)) {
            throw new DaoException(ExceEnum.SYS_PARAM_EXCE.getCode(), "根据实体id操作时，字段名不能为空");
        }
        boolean flag = false;
        for (String entityId : entityIdList) {
            QueryWrapper<T> wrapper = new QueryWrapper<>();
            wrapper.eq(StringTool.humpToLine(entityIdFieldName), entityId);
            boolean temFlag = this.remove(wrapper);
            //失败：日志记录
            if (!temFlag) {
                log.error("\nbatch delete by entityId failed：\nentityIdFieldName:" + entityIdFieldName + "--entityId:" + entityId);
                //成功：若flag为false则更新为true（只要有一次操作成功，则操作成功）
            } else if (!flag) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public T getByEntityId(String entityId, String entityIdFieldName) {
        if (StringUtils.isBlank(entityIdFieldName)) {
            throw new DaoException(ExceEnum.SYS_PARAM_EXCE.getCode(), "根据条件操作时，字段名不能为空");
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.eq(StringTool.humpToLine(entityIdFieldName), entityId);
        return this.getOne(wrapper);
    }

    @Override
    public ResultPage<T> getEntityListByPage(QueryPage queryPage, Map<String, Object> queryMap) {
        if (Objects.isNull(queryPage)) {
            throw new DaoException(ExceEnum.SYS_PARAM_EXCE.getCode(), "分页查询分页参数对象不能为空");
        }
        IPage<T> page = new Page<>(queryPage.getCurrent(), queryPage.getSize());
        QueryWrapper<T> queryWrapper = null;
        if (Objects.nonNull(queryMap) && !CollectionUtils.isEmpty(queryMap)) {
            queryWrapper = new QueryWrapper<>();
            for (String str : queryMap.keySet()) {
                queryWrapper.eq(StringTool.humpToLine(str), queryMap.get(str));
            }
        }
        ResultPage<T> resultPage = new ResultPage<>();
        BeanUtils.copyProperties(this.page(page, queryWrapper), resultPage);
        return resultPage;
    }

    @Override
    public T getLastOneByIdPrefix(String idPrefix, String entityIdFieldName) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringTool.humpToLine(entityIdFieldName), idPrefix + "%")
                .orderByDesc(StringTool.humpToLine(entityIdFieldName));
        List<T> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        T obj = CollectionUtils.firstElement(list);
        if (Objects.isNull(obj)) {
            return null;
        }
        return obj;
    }
}

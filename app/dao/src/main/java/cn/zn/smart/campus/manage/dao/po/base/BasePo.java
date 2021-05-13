package cn.zn.smart.campus.manage.dao.po.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 基础po
 * @Author: zhangnan
 * @Date: 2021/05/13 11:04
 */
public abstract class BasePo extends Model<BasePo> implements Serializable {

    private static final long serialVersionUID = -6110217049640557173L;

    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

    /**
     * 预留字段
     */
    private String extra;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 修改时间
     */
    protected Date modifiedTime;
}

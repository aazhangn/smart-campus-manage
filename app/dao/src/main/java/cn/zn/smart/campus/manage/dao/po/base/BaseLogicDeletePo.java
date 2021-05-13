package cn.zn.smart.campus.manage.dao.po.base;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * @Description: 逻辑删除基础po
 * @Author: zhangnan
 * @Date: 2021/05/13 11:19
 */
@Data
public abstract class BaseLogicDeletePo extends BasePo{
    private static final long serialVersionUID = -9050204997701276208L;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;
}

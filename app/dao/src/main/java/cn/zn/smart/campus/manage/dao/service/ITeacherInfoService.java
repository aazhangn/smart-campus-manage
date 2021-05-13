package cn.zn.smart.campus.manage.dao.service;

import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangnan
 * @since 2021-05-13
 */

public interface ITeacherInfoService extends IService<TeacherInfo> {

    /**
     * 根据teacher_id更新信息
     * @param teacherInfoList
     * @return
     */
    public boolean updateBatchByTeaId(List<TeacherInfo> teacherInfoList);

}

package cn.zn.smart.campus.manage.dao.service.impl;

import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import cn.zn.smart.campus.manage.dao.mapper.TeacherInfoMapper;
import cn.zn.smart.campus.manage.dao.service.ITeacherInfoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangnan
 * @since 2021-05-13
 */
@Service
public class TeacherInfoServiceImpl extends ServiceImpl<TeacherInfoMapper, TeacherInfo> implements ITeacherInfoService {

    @Override
    public boolean updateBatchByTeaId(List<TeacherInfo> teacherInfoList) {
        UpdateWrapper<TeacherInfo> wrapper = new UpdateWrapper<TeacherInfo>();
        for (TeacherInfo tea:teacherInfoList) {
            wrapper.eq("teacher_id",tea.getTeacherId());
            this.update(tea,wrapper);
        }
        return true;
    }
}

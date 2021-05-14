package cn.zn.smart.campus.manage.dao.service.impl;

import cn.zn.smart.campus.manage.dao.mapper.TeacherInfoMapper;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import cn.zn.smart.campus.manage.dao.service.ITeacherInfoService;
import cn.zn.smart.campus.manage.dao.service.base.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
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
public class TeacherInfoServiceImpl extends BaseServiceImpl<TeacherInfoMapper, TeacherInfo> implements ITeacherInfoService {

    public boolean deleteBatchByTeaId(List<String> teacherIdList) {
        QueryWrapper<TeacherInfo> wrapper = new QueryWrapper<>();
        for (String teaId:teacherIdList) {
            if (StringUtils.isBlank(teaId)){
                continue;
            }
            wrapper.eq("teacher_id",teaId);
            this.remove(wrapper);
        }
        return true;
    }


    public TeacherInfo getByTeaId(String teacherId) {
        QueryWrapper<TeacherInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        return this.getOne(wrapper);
    }
}

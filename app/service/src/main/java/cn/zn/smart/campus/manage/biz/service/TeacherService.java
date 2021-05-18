package cn.zn.smart.campus.manage.biz.service;

import cn.zn.smart.campus.manage.biz.dto.TeacherDto;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import cn.zn.smart.campus.manage.dao.page.ResultPage;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;

import java.util.List;

/**
 * @Description: 教师业务逻辑
 * @Author: zhangnan
 * @Date: 2021/05/15 23:49
 */
public interface TeacherService {

    /**
     * 新增教师
     * @param teacher
     * @return
     */
    public boolean save(TeacherDto teacher);

    /**
     * 根据教师TeaId获取信息
     * @param teaId
     * @return
     */
    public TeacherInfo getByTeaId(String teaId);


    /**
     * 条件分页查询
     * @return
     */
    public ResultPage<TeacherInfo> getTeacherListByPage(QueryPage queryPage,TeacherDto teacherDto) throws IllegalAccessException;

    /**
     * 批量更新教师信息
     * @param teacherDtoList
     * @return
     */
    public boolean updateBatchByTeaId(List<TeacherDto> teacherDtoList);

    /**
     * 根据teaId批量删除教师
     * @param teaIdList
     * @return
     */
    public boolean deleteBatchByTeaId(List<String> teaIdList);
}

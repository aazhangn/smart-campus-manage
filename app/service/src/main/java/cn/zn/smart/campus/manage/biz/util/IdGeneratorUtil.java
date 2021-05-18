package cn.zn.smart.campus.manage.biz.util;

import cn.zn.smart.campus.manage.biz.dto.ClassDto;
import cn.zn.smart.campus.manage.biz.dto.TeacherDto;
import cn.zn.smart.campus.manage.biz.enums.teacher.HireTypeEnum;
import cn.zn.smart.campus.manage.biz.enums.teacher.TeaResearchGroupEnum;
import cn.zn.smart.campus.manage.biz.exception.BizException;
import cn.zn.smart.campus.manage.biz.exception.ErrorEnum;
import cn.zn.smart.campus.manage.dao.po.ClassInfo;
import cn.zn.smart.campus.manage.dao.po.TeacherInfo;
import cn.zn.smart.campus.manage.dao.service.IClassInfoService;
import cn.zn.smart.campus.manage.dao.service.ITeacherInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description: id生成工具
 * @Author: zhangnan
 * @Date: 2021/05/17 17:25
 */
@Component
public class IdGeneratorUtil {
    @Autowired
    private ITeacherInfoService iTeacherInfoService;
    @Autowired
    private IClassInfoService iClassInfoService;

    public static IdGeneratorUtil idGeneratorUtil;

    /**
     * 初始化
     */
    @PostConstruct
    public void init(){
        idGeneratorUtil = this;
        idGeneratorUtil.iTeacherInfoService = this.iTeacherInfoService;
        idGeneratorUtil.iClassInfoService = this.iClassInfoService;
    }

    /**
     * 生成教师id
     * @param teacherDto
     * @return
     */
    public static String getTeacherId(TeacherDto teacherDto) {
        if (Objects.isNull(teacherDto) || StringUtils.isBlank(teacherDto.getHireType())) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        String hireTypeNo;
        String teaResearchGroupNo;
        try {
            HireTypeEnum hireTypeEnum = HireTypeEnum.getByDesc(teacherDto.getHireType());
            hireTypeNo = hireTypeEnum.getNo();
            if (StringUtils.isNotBlank(teacherDto.getTeaResearchGroup())) {
                teaResearchGroupNo = TeaResearchGroupEnum.getByValue(teacherDto.getTeaResearchGroup()).getNo();
            }else{
                teaResearchGroupNo = "0000";
            }
        } catch (IllegalArgumentException|NullPointerException e) {
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        String serialNo = getTeaSerialNo(hireTypeNo+teaResearchGroupNo);
        return hireTypeNo+teaResearchGroupNo+serialNo;
    }

    /**
     * 生成班级id
     */
    public static String getClassId(ClassDto classDto) {
        if (Objects.isNull(classDto)||StringUtils.isBlank(classDto.getGrade())||Strings.isBlank(classDto.getClassNo())){
            throw new BizException(ErrorEnum.SYS_PARAM_ERROR);
        }
        String prefix = classDto.getGrade()+classDto.getClassNo();
        String serialNo = getClaSerialNo(prefix);
        return prefix+serialNo;
    }

    /**
     * 生成16位的id
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 生成教师id后三位流水号
     * @param teaIdPrefix
     * @return
     */
    private static String getTeaSerialNo(String teaIdPrefix){
        TeacherInfo teacherInfo = idGeneratorUtil.iTeacherInfoService.getLastOneByIdPrefix(
                teaIdPrefix,"teacherId");
        if (Objects.nonNull(teacherInfo)){
            String lastTeaSerialNo = teacherInfo.getTeacherId().substring(teacherInfo.getTeacherId().length()-3);
            return String.format("%03d",Integer.parseInt(lastTeaSerialNo)+1);
        }
        //无数据，从001开始
        return "001";
    }

    /**
     *
     * @param claIdPrefix
     * @return
     */
    private static String getClaSerialNo(String claIdPrefix){
        ClassInfo classInfo = idGeneratorUtil.iClassInfoService.getLastOneByIdPrefix(
                claIdPrefix,"classId");
        if (Objects.nonNull(classInfo)){
            String lastTeaSerialNo = classInfo.getClassId().substring(classInfo.getClassId().length()-2);
            return String.format("%02d",Integer.parseInt(lastTeaSerialNo)+1);
        }
        //无数据，从01开始
        return "01";
    }
}

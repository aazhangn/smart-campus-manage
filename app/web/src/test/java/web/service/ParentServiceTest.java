package web.service;

import base.BaseTest;
import cn.zn.smart.campus.manage.biz.dto.ParentDto;
import cn.zn.smart.campus.manage.biz.dto.StuParentRelDTO;
import cn.zn.smart.campus.manage.biz.enums.parent.RelationshipTypeEnum;
import cn.zn.smart.campus.manage.biz.service.ParentBizService;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import com.alibaba.fastjson.JSON;
import org.assertj.core.util.Lists;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/21 18:33
 */
public class ParentServiceTest extends BaseTest {
    @Resource
    private ParentBizService parentBizService;
    @Test
    public void saveTest(){
        ParentDto parentDto = new ParentDto();
        parentDto.setName("李大国");
        parentDto.setPhoneNumber("13266557788");
        StuParentRelDTO rel = new StuParentRelDTO();
        rel.setStudentId("2017010102");
        rel.setRelationshipType(RelationshipTypeEnum.FATHER.getValue());
        parentDto.setRels(Lists.newArrayList(rel,rel));
        parentBizService.save(parentDto);
    }

    @Test
    public void getOneTest(){
        System.out.println(JSON.toJSONString(parentBizService.getByParId("par_d354c6cee844424b9bc732ff93a3e1d4")));
    }

    @Test
    public void listTest() throws IllegalAccessException {
        System.out.println(JSON.toJSONString(parentBizService.getListByPage(new QueryPage(),null)));
    }

    @Test
    public void deleteTest(){
        parentBizService.deleteBatch(Lists.newArrayList("par_d354c6cee844424b9bc732ff93a3e1d4"));
    }

    @Test
    public void updateTest(){
        ParentDto parentDto = new ParentDto();
        parentDto.setName("王思");
        parentDto.setParentId("par_60f3aec48a1c4dddb1936ae7078b7f15");
        parentBizService.updateBatchByParId(Lists.newArrayList(parentDto));
    }

    @Test
    public void getStuIdsTest(){
        System.out.println(JSON.toJSONString(parentBizService.getStuIds("par_60f3aec48a1c4dddb1936ae7078b7f15")));;
    }


}

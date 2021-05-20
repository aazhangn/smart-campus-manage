package web.service;

import base.BaseTest;
import cn.zn.smart.campus.manage.biz.dto.FacilityDTO;
import cn.zn.smart.campus.manage.biz.enums.facility.FacilityTypeEnum;
import cn.zn.smart.campus.manage.biz.service.FacilityBizService;
import cn.zn.smart.campus.manage.dao.page.QueryPage;
import com.alibaba.fastjson.JSON;
import org.assertj.core.util.Lists;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 16:52
 */
public class FacilityServiceTest extends BaseTest {
    @Resource
    private FacilityBizService facilityBizService;

    @Test
    public void saveTest(){
        FacilityDTO facility = new FacilityDTO();
        facility.setName("0003");
        facility.setType(FacilityTypeEnum.PORTABLE_ACTIVITY_FACILITY.getType());
        facilityBizService.save(facility);
    }


    @Test
    public void getOneTest(){
        System.out.println(facilityBizService.getByFacId("fal_ad31b8f600634d2791a012b42025dcab"));
    }

    @Test
    public void deleteTest(){
        System.out.println(facilityBizService.deleteBatchByFacId(Lists.newArrayList("fal_ad31b8f600634d2791a012b42025dcab")));
    }

    @Test
    public void listTest() throws IllegalAccessException {
        System.out.println(JSON.toJSONString(facilityBizService.getListByPage(new QueryPage(),null)));
    }

    @Test
    public void borrowTest(){
        facilityBizService.borrowFacility("fac_aceee3b62af9464883e12e72b7201dd6","0001");
    }

    @Test
    public void returnTest(){
        facilityBizService.returnFacility("fac_aceee3b62af9464883e12e72b7201dd6");
    }
}

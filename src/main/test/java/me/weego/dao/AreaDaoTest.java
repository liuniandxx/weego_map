package me.weego.dao;

import me.weego.model.AreaModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ln
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AreaDaoTest {
    @Resource
    private AreaDao areaDao;

    @Test
    public void testQueryArea() {
        String areaId = "53cdca4b8e33d8497e0000cf";
        AreaModel model = areaDao.findById(areaId);
        System.out.println(model);
    }

    @Test
    public void testQueryByName() {
        String name = "史主化街";
        String cityId = "516a35218902ca1936000002";
        List<AreaModel> areas = areaDao.queryByName(name, cityId);
        System.out.println(areas);
    }
}

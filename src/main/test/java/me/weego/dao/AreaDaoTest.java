package me.weego.dao;

import me.weego.model.AreaModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

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
}

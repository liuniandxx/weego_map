package me.weego.dao;

import me.weego.model.AttractionModel;
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
public class AttractionDaoTest {
    @Resource
    private AttractionDao attractionDao;

    @Test
    public void testQueryAttraction() {
        String attractionId = "516cc44ce3c6a60f69000021";
        AttractionModel model = attractionDao.findById(attractionId);
        System.out.println(model);
    }

    @Test
    public void testQueryByName() {
        String name = "纽约证券交易所";
        String cityId = "516a34f958e3511036000001";
        List<AttractionModel> model = attractionDao.queryByName(name, cityId);
        System.out.println(model);
    }
}

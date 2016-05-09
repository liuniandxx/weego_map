package me.weego.dao;

import me.weego.model.AttractionModel;
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
public class AttractionDaoTest {
    @Resource
    private AttractionDao attractionDao;

    @Test
    public void testQueryAttraction() {
        String attractionId = "516cc44ce3c6a60f69000021";
        AttractionModel model = attractionDao.findById(attractionId);
        System.out.println(model);
    }
}

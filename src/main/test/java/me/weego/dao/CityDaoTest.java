package me.weego.dao;

import me.weego.model.CityModel;
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
public class CityDaoTest {

    @Resource
    private CityDao cityDao;

    @Test
    public void testGetCityByid() {
        String cityId = "516a35218902ca1936000004";
        CityModel cityModel = cityDao.findById(cityId);
        System.out.println(cityModel);
    }
}

package me.weego.dao;

import me.weego.model.RestaurantModel;
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
public class RestaurantDaoTest {
    @Resource
    private RestaurantDao restaurantDao;

    @Test
    public void testQueryRest() {
        String id = "5322c08d2fab6f0c1d000002";
        RestaurantModel model = restaurantDao.findById(id);
        System.out.println(model);
    }

    @Test
    public void testQueryByName() {
        String cityId = "516a3519f8a6461636000003";
        String name = "La Cova Fumada";

        List<RestaurantModel> restaurants = restaurantDao.queryByName(name, cityId);
        System.out.println(restaurants);
    }
}

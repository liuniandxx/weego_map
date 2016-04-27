package me.weego.service.impl;

import me.weego.model.RestaurantModel;
import me.weego.service.RestaurantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuniandxx on 16-4-25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RestaurantModelTest {

    @Resource
    private RestaurantService restaurantService;

    @Test
    public void testQueryByName() {
        String name = "Cinnamon";
        List<RestaurantModel> list = restaurantService.queryByName(name, "569c95852fedc1e7190001ea");

        for(RestaurantModel elem : list) {
            System.out.println(elem.toString());
        }
    }
}

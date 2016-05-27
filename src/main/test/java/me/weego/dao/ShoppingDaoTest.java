package me.weego.dao;

import me.weego.model.ShoppingModel;
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
public class ShoppingDaoTest {
    @Resource
    private ShoppingDao shoppingDao;

    @Test
    public void testQueryShopping() {
        String shopId = "5327c20dab18a29415000001";
        ShoppingModel model = shoppingDao.findById(shopId);
        System.out.println(model);
    }

    @Test
    public void testQueryByName() {
        String cityId = "51d3d238e98bbb566a000001";
        String name = "Bosi Artes";

        List<ShoppingModel> shoppings = shoppingDao.queryByName(name, cityId);
        System.out.println(shoppings);
    }
}

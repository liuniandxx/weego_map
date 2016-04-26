package me.weego.service.impl;

import me.weego.model.ShoppingModel;
import me.weego.service.ShoppingService;
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
public class ShoppingServiceImplTest {
    @Resource
    private ShoppingService shoppingService;

    @Test
    public void testQueryByName() {
        List<ShoppingModel> list = shoppingService.queryByName("Zohiko");
        for(ShoppingModel shoppingModel : list) {
            System.out.println(shoppingModel);
            System.out.println(shoppingModel.getId().toString());
        }
    }

}

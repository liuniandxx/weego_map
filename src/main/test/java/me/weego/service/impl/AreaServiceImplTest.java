package me.weego.service.impl;

import me.weego.model.AreaModel;
import me.weego.service.AreaService;
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
public class AreaServiceImplTest {
    @Resource
    private AreaService areaService;

    @Test
    public void testQueryByName() {
        List<AreaModel> list = areaService.queryByName("官也街");
        for(AreaModel areaModel : list) {
            System.out.println(areaModel);
        }
    }
}

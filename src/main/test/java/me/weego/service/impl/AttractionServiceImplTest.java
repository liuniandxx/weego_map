package me.weego.service.impl;

import me.weego.model.AttractionModel;
import me.weego.service.AttractionService;
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
public class AttractionServiceImplTest {
    @Resource
    private AttractionService attractionService;


    @Test
    public void testQueryByName() {
        List<AttractionModel> list = attractionService.queryByName("ling", "569c95852fedc1e7190001ea");
        for(AttractionModel elem : list) {
            System.out.println(elem);
        }
    }

}

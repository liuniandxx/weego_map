package me.weego.service.impl;

import me.weego.service.UberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * uber service test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UberServiceImplTest {
    @Resource
    private UberService uberService;

    @Test
    public void testGetEstimatePrice() {
        System.out.println(uberService.getEstimatePrice("116.3640600000,39.9960970000", "116.3650490000,39.9672990000"));
    }
}

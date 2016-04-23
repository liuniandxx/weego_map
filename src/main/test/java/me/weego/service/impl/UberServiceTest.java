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
public class UberServiceTest {
    @Resource
    private UberService uberService;

    @Test
    public void testGetEstimatePrice() {

    }
}

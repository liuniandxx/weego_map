package me.weego.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author tcl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpiderControllerTest {

    @Resource
    private SpiderController spiderController;

    @Test
    public void testGetKey() throws Exception {
        System.out.println(spiderController.getKey("4"));
    }

    @Test
    public void testGetGoogleTraffic() throws Exception {
        System.out.println(spiderController.getGoogleTraffic("driving", "48.866202,2.310822", "48.857092,2.352226"));
    }
}
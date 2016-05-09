package me.weego.service.impl;

import me.weego.service.SpiderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author tcl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpiderServiceImplTest {

    @Resource
    private SpiderService spiderService;

    @Test
    public void testGetKey() throws Exception {

    }

    @Test
    public void testGetGoogleTraffic() throws Exception {
        System.out.println(spiderService.getGoogleTraffic("48.866202,2.310822", "48.857092,2.352226"));
    }

    @Test
    public void testTranslate() {
        System.out.println(spiderService.translate("Stay hungry, Stay foolish"));
    }
}
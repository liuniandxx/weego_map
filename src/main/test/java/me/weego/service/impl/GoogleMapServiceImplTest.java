package me.weego.service.impl;

import me.weego.service.GoogleMapService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by liuniandxx on 16-4-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class GoogleMapServiceImplTest {
    @Resource
    private GoogleMapService googleMapService;

    @Test
    public void testGetNearbyUrl() {
        System.out.println(googleMapService.getNearBy("40.761433,-73.977622", "纽约现代艺术博物馆"));
    }

    @Test
    public void testGetPlacedetals() {
        System.out.println(googleMapService.getPlaceDetails("ChIJ31zO2-IIAWARw_L-Hkg_4rQ"));
    }

    @Test
    public void testGetPlaceComplete() {
    }
}




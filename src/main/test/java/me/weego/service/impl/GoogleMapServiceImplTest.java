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
        System.out.println(googleMapService.getNearBy("39.90960456049752,116.3972282409668", "天安门  东城区"));
    }

    @Test
    public void testGetPlacedetals() {
        System.out.println(googleMapService.getPlaceDetails("ChIJ2XRD3Jh2YzYRE1lUrcku6io"));
    }
}




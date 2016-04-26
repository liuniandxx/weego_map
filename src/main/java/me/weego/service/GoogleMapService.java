package me.weego.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by liuniandxx on 16-4-23.
 */
public interface GoogleMapService {
    String getNearBy(String location, String name);

    JSONObject getPlaceDetails(String placeId);

    JSONObject getPlaceDetails(String location, String address);

    String getPlaceComplete(String name);

}

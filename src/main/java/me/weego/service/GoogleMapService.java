package me.weego.service;

/**
 * Created by liuniandxx on 16-4-23.
 */
public interface GoogleMapService {
    String getNearBy(String location, String name);

    String getPlaceDetails(String placeId);

    String getPlaceComplete(String name);
}

package me.weego.service;

import com.alibaba.fastjson.JSONObject;
import me.weego.model.PlacePredictModel;

import java.util.List;

/**
 * Created by liuniandxx on 16-4-23.
 */
public interface GoogleMapService {
    String getNearBy(String location, String name);

    JSONObject getPlaceDetails(String placeId);

    JSONObject getPlaceDetails(String location, String address);

    String getPlaceComplete(String name, String location);

    List<PlacePredictModel> getPlacePredict(String name, String location, String cityId);

    List<PlacePredictModel> getSearchHis(String userId, String cityId, String location);
}

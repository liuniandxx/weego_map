package me.weego.service;

import me.weego.model.MapSearchHisModel;

import java.util.List;

/**
 * @author ln
 */
public interface MapSearchHisService {
    List<MapSearchHisModel> getMapSearchHis(String userId, String cityId);

    void deleteSearchHis(String userId, String cityId);
}

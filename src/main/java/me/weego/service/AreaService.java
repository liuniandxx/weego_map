package me.weego.service;

import me.weego.model.AreaModel;

import java.util.List;

/**
 * @author ln
 */
public interface AreaService {
    List<AreaModel> queryByName(String areaName, String cityId);
}

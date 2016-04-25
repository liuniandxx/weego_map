package me.weego.service;

import me.weego.model.RestaurantModel;

import java.util.List;

/**
 * @author tcl
 */
public interface RestaurantService {
    List<RestaurantModel> query();

    List<RestaurantModel> queryByName(String name);
}

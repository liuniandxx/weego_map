package me.weego.service;

import me.weego.model.ShoppingModel;

import java.util.List;

/**
 * @author ln
 */
public interface ShoppingService {
    List<ShoppingModel> queryByName(String name);
}

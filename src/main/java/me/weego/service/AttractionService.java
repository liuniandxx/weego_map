package me.weego.service;

import me.weego.model.AttractionModel;

import java.util.List;

/**
 * @author ln
 */
public interface AttractionService {
     List<AttractionModel> queryByName(String attractions);
}

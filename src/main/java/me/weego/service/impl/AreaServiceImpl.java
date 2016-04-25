package me.weego.service.impl;

import me.weego.model.AreaModel;
import me.weego.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ln
 */
@Service
public class AreaServiceImpl implements AreaService{


    @Override
    public List<AreaModel> queryByName(String areaName) {
        return null;
    }
}

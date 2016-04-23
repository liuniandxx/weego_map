package me.weego.service.impl;

import me.weego.dao.RestaurantDao;
import me.weego.model.RestaurantModel;
import me.weego.service.RestaurantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tcl
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Resource
    private RestaurantDao restaurantDao;

    @Override
    public List<RestaurantModel> query() {
        return restaurantDao.query();
    }
}

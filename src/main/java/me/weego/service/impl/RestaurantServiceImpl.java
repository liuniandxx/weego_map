package me.weego.service.impl;

import me.weego.dao.RestaurantDao;
import me.weego.model.RestaurantModel;
import me.weego.service.RestaurantService;
import me.weego.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

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

    @Override
    public List<RestaurantModel> queryByName(String name, String cityId) {
        checkArgument(StringUtils.isNotBlank(name), "param name should not blank");
        checkArgument(StringUtils.isNotBlank(cityId), "param cityId should not blank");
        LoggerUtil.logBiz("Restaurant query by name", name);
        LoggerUtil.logBiz("Restaurant query by cityId", cityId);
        return restaurantDao.queryByName(name, cityId);
    }
}

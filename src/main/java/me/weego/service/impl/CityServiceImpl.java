package me.weego.service.impl;

import me.weego.dao.CityDao;
import me.weego.model.CityModel;
import me.weego.service.CityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author ln
 */
@Service
public class CityServiceImpl implements CityService {
    @Resource
    private CityDao cityDao;

    @Override
    public CityModel queryCity(String cityId) {
        checkArgument(cityId != null, "cityId should not be blank");
        return cityDao.findById(cityId);
    }
}

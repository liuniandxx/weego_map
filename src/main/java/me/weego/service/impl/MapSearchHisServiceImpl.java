package me.weego.service.impl;

import me.weego.dao.MapSearchHisDao;
import me.weego.model.MapSearchHisModel;
import me.weego.service.MapSearchHisService;
import me.weego.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author ln
 */
@Service
public class MapSearchHisServiceImpl implements MapSearchHisService {
    @Resource
    private MapSearchHisDao mapSearchHisDao;

    @Override
    public List<MapSearchHisModel> getMapSearchHis(String userId, String cityId) {
        checkArgument(StringUtils.isNotBlank(userId), "param userId should not be blank");
        checkArgument(StringUtils.isNotBlank(cityId), "param cityId should not be blank");
        LoggerUtil.logBiz("map search history userId", userId);
        LoggerUtil.logBiz("map search history cityId", cityId);
        return mapSearchHisDao.getMapSearchHis(userId, cityId);
    }

    @Override
    public void deleteSearchHis(String userId, String cityId) {
        checkArgument(StringUtils.isNotBlank(userId), "param userId should not be blank");
        checkArgument(StringUtils.isNotBlank(cityId), "param cityId should not be blank");
        LoggerUtil.logBiz("delete map search history userId", userId);
        LoggerUtil.logBiz("delete map search history cityId", cityId);
        mapSearchHisDao.deleteSearchHis(userId, cityId);
    }
}

package me.weego.service.impl;

import com.alibaba.fastjson.JSONObject;
import me.weego.dao.*;
import me.weego.model.*;
import me.weego.service.GoogleMapService;
import me.weego.service.MapSearchHisService;
import me.weego.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author ln
 */
@Service
public class MapSearchHisServiceImpl implements MapSearchHisService {
    @Resource
    private MapSearchHisDao mapSearchHisDao;

    @Resource
    private AttractionDao attractionDao;

    @Resource
    private RestaurantDao restaurantDao;

    @Resource
    private ShoppingDao shoppingDao;

    @Resource
    private AreaDao areaDao;

    @Resource
    private GoogleMapService googleMapService;

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

    @Override
    public void saveSearchHis(String cityId, String userId, String type, String poiId) {
        if(!mapSearchHisDao.findOne(cityId, userId, type, poiId)) {
            Document document = new Document();
            document.put("city_id", new ObjectId(cityId));
            document.put("user_id", userId);
            document.put("type", type);
            document.put("poi_id", poiId);
            document.put("is_poi", true);

            if("0".equals(type)) {
                AttractionModel model = attractionDao.findById(poiId);
                checkArgument(model != null, "Attraction is not exist");
                document.put("name", model.getAttractions());
                document.put("place_id", model.getPlaceId());
                document.put("address", model.getAddress());
                document.put("latitude", model.getLatitude());
                document.put("longitude", model.getLongitude());
                document.put("image",model.getCoverImage());
                document.put("tag", model.getTag());
                document.put("last_modify_time", new Date());
                mapSearchHisDao.saveSearchHis(document);
            } else if("1".equals(type)) {
                RestaurantModel model = restaurantDao.findById(poiId);
                checkArgument(model != null, "Restaurant is not exist");
                document.put("name", model.getName());
                document.put("place_id", model.getPlaceId());
                document.put("address", model.getAddress());
                document.put("latitude", model.getLatitude());
                document.put("longitude", model.getLongitude());
                document.put("image",model.getCoverImage());
                document.put("tag", model.getTag());
                document.put("last_modify_time", new Date());
                mapSearchHisDao.saveSearchHis(document);
            } else if("2".equals(type)) {
                ShoppingModel model = shoppingDao.findById(poiId);
                checkArgument(model != null, "Shopping is not exist");
                document.put("mame", model.getName());
                document.put("place_id", model.getPlaceId());
                document.put("address", model.getAddress());
                document.put("latitude", model.getLatitude());
                document.put("longitude", model.getLongitude());
                document.put("image",model.getCoverImage());
                document.put("tag", model.getTag());
                document.put("last_modify_time", new Date());
                mapSearchHisDao.saveSearchHis(document);
            } else if("3".equals(type)) {
                AreaModel model = areaDao.findById(poiId);
                checkArgument(model != null, "Area is not exist");
                document.put("name", model.getAreaName());
                document.put("place_id", model.getPlaceId());
                document.put("address", model.getAddress());
                document.put("latitude", model.getLatitude());
                document.put("longitude", model.getLongitude());
                document.put("image",model.getCoverImage());
                document.put("tag", model.getTag());
                document.put("last_modify_time", new Date());
                mapSearchHisDao.saveSearchHis(document);
            }
        }
    }

    @Override
    public void saveSearchHis(String cityId, String userId, String placeId) {
        checkArgument(StringUtils.isNotBlank(placeId), "param placeId should not be blank");

        if(!mapSearchHisDao.findOne(cityId, userId, placeId)) {
            Document document = new Document();
            document.put("city_id", new ObjectId(cityId));
            document.put("user_id", userId);
            document.put("type", "");
            document.put("poi_id", "");
            document.put("is_poi", false);

            JSONObject placeDetail = googleMapService.getPlaceDetails(placeId);
            JSONObject result = placeDetail.getJSONObject("result");

            String desc = result.getString("name");
            String address = result.getString("formatted_address");
            JSONObject placeLocation = result.getJSONObject("geometry").getJSONObject("location");
            String lat = placeLocation.getString("lat");
            String lng = placeLocation.getString("lng");
            document.put("name", desc);
            document.put("address", address);
            document.put("place_id", placeId);
            document.put("latitude", lat);
            document.put("longitude", lng);
            document.put("image","");
            document.put("tag", "");
            document.put("last_modify_time", new Date());
            mapSearchHisDao.saveSearchHis(document);
        }
    }

}

package me.weego.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.weego.constant.SpiderKeyEnum;
import me.weego.model.*;
import me.weego.service.*;
import me.weego.util.DistanceUtil;
import me.weego.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
/**
 * @author ln
 */
@Service
public class GoogleMapServiceImpl implements GoogleMapService {
    @Resource
    private BaseService baseService;

    @Resource
    private AttractionService attractionService;

    @Resource
    private RestaurantService restaurantService;

    @Resource
    private ShoppingService shoppingService;

    @Resource
    private AreaService areaService;

    @Resource
    private MapSearchHisService mapSearchHisService;

    @Resource
    private CityService cityService;

    @Override
    public String getNearBy(String location, String name) {
        LoggerUtil.logBiz("***** Google nearby search start *****", null);
        checkArgument(StringUtils.isNotBlank(location)
                        && location.contains(","), "error param location");
        String nearByUrl = getNearbyUrl(location, name);
        LoggerUtil.logBiz("***** nearbyUrl", nearByUrl);
        return baseService.getHttpRequest(nearByUrl);
    }

    @Override
    public String getPlaceDetails(String placeId) {
        LoggerUtil.logBiz("***** Google place details search start *****", null);
        checkArgument(StringUtils.isNotBlank(placeId), "param placeId should not blank");
        String detailsUrl = getDetailsUrl(placeId);
        LoggerUtil.logBiz("***** detailsUrl", detailsUrl);
        return baseService.getHttpRequest(detailsUrl);
    }

    @Override
    public String getPlaceDetails(String location, String address) {
        LoggerUtil.logBiz("get place details by location and address", null);
        String nearBys = getNearBy(location, address);
        JSONObject nearByJson = JSONObject.parseObject(nearBys);
        JSONArray results = nearByJson.getJSONArray("results");
        if(results != null && results.size() > 0) {
            JSONObject firstPlace = results.getJSONObject(0);
            String placeId = firstPlace.getString("place_id");
            String result = getPlaceDetails(placeId);
            JSONObject placeDetails = JSONObject.parseObject(result);
            placeDetails.put("isGoogle", true);
            placeDetails.put("address", address);
            return placeDetails.toString();
        } else {
            JSONObject placeDetails = new JSONObject();
            placeDetails.put("isGoogle", false);
            placeDetails.put("address", address);
            return placeDetails.toString();
        }
    }

    @Override
    public String getPlaceComplete(String name, String location) {
        LoggerUtil.logBiz("**** Google place autocomplete start ****", null);
        checkArgument(StringUtils.isNotBlank(name), "param name should not be blank");
        String placeCompleteUrl = getPlaceCompleteUrl(name, location);
        return baseService.getHttpRequest(placeCompleteUrl);
    }

    @Override
    public List<PlacePredictModel> getPlacePredict(String name, String cityId) {
        LoggerUtil.logBiz("**** Google place predict start ****", null);
        LoggerUtil.logBiz("param name", name);
        checkArgument(StringUtils.isNotBlank(name), "param name should not be blank");

        CityModel cityModel = cityService.queryCity(cityId);
        String location = cityModel.getLatitude() + "," + cityModel.getLongitude();

        LoggerUtil.logBiz("获取匹配的景点地标", null);
        List<PlacePredictModel> list = Lists.newArrayList();
        Set<String> nameSet = Sets.newHashSet();
        List<AttractionModel> attractionModels = attractionService.queryByName(name, cityId);
        for(AttractionModel model : attractionModels) {
            LoggerUtil.logBiz("Attraction model", model.toString());
            PlacePredictModel placePredictModel = new PlacePredictModel();
            placePredictModel.setIsPoi(true);
            placePredictModel.setPoiId(model.getId().toString());
            placePredictModel.setType(model.getType());
            placePredictModel.setName(model.getAttractions());
            placePredictModel.setAddress(model.getAddress());
            placePredictModel.setImage(model.getCoverImage());
            placePredictModel.setTag(model.getTag());
            String dest = model.getLatitude() + "," + model.getLongitude();
            placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
            placePredictModel.setPlaceId(model.getPlaceId());
            placePredictModel.setLatitude(model.getLatitude());
            placePredictModel.setLongitude(model.getLongitude());
            list.add(placePredictModel);
            nameSet.add(model.getAttractions());
            nameSet.add(model.getAttractionsEn());
        }

        LoggerUtil.logBiz("获取匹配的餐厅", null);
        List<RestaurantModel> restaurantModels = restaurantService.queryByName(name, cityId);
        for(RestaurantModel model : restaurantModels) {
            LoggerUtil.logBiz("Restaurant model", model.toString());
            PlacePredictModel placePredictModel = new PlacePredictModel();
            placePredictModel.setIsPoi(true);
            placePredictModel.setPoiId(model.getId().toString());
            placePredictModel.setType(model.getType());
            placePredictModel.setName(model.getName());
            placePredictModel.setAddress(model.getAddress());
            placePredictModel.setImage(model.getCoverImage());
            placePredictModel.setTag(model.getTag());
            String dest = model.getLatitude() + "," + model.getLongitude();
            placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
            placePredictModel.setPlaceId(model.getPlaceId());
            placePredictModel.setLatitude(model.getLatitude());
            placePredictModel.setLongitude(model.getLongitude());
            list.add(placePredictModel);
            nameSet.add(model.getName());
        }

        LoggerUtil.logBiz("获取匹配的购物", null);
        List<ShoppingModel> shoppingModels = shoppingService.queryByName(name, cityId);
        for(ShoppingModel model : shoppingModels) {
            PlacePredictModel placePredictModel = new PlacePredictModel();
            placePredictModel.setIsPoi(true);
            placePredictModel.setPoiId(model.getId().toString());
            placePredictModel.setType(model.getType());
            placePredictModel.setName(model.getName());
            placePredictModel.setAddress(model.getAddress());
            placePredictModel.setImage(model.getCoverImage());
            placePredictModel.setTag(model.getTag());
            String dest = model.getLatitude() + "," + model.getLongitude();
            placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
            placePredictModel.setPlaceId(model.getPlaceId());
            placePredictModel.setLatitude(model.getLatitude());
            placePredictModel.setLongitude(model.getLongitude());
            list.add(placePredictModel);
            nameSet.add(model.getName());
        }

        LoggerUtil.logBiz("获取匹配的购物圈", null);
        List<AreaModel> areaModels = areaService.queryByName(name, cityId);
        for(AreaModel model : areaModels) {
            LoggerUtil.logBiz("Area model", model);
            PlacePredictModel placePredictModel = new PlacePredictModel();
            placePredictModel.setIsPoi(true);
            placePredictModel.setPoiId(model.getId().toString());
            placePredictModel.setType(model.getType());
            placePredictModel.setName(model.getAreaName());
            placePredictModel.setAddress(model.getAddress());
            placePredictModel.setImage(model.getCoverImage());
            placePredictModel.setTag(model.getTag());
            String dest = model.getLatitude() + "," + model.getLongitude();
            placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
            placePredictModel.setPlaceId(model.getPlaceId());
            placePredictModel.setLatitude(model.getLatitude());
            placePredictModel.setLongitude(model.getLongitude());
            list.add(placePredictModel);

            nameSet.add(model.getAreaName());
            nameSet.add(model.getAreaEnName());
        }

        String googlePlaces = getNearBy(location, name);
        JSONObject placeJson = JSON.parseObject(googlePlaces);
        if("OK".equals(placeJson.getString("status"))) {
            JSONArray results = placeJson.getJSONArray("results");
            for(int i = 0; i < results.size();i++) {
                JSONObject result = results.getJSONObject(i);
                String placeId = result.getString("place_id");

                String posName = result.getString("name");

                if(nameSet.contains(posName)) {
                    continue;
                } else {
                    nameSet.add(posName);
                }
                String vicinity = result.getString("vicinity");
                JSONObject pos = result.getJSONObject("geometry").getJSONObject("location");
                String lat = pos.getDouble("lat").toString();
                String lng = pos.getDouble("lng").toString();
                String dest = lat + "," + lng;

                PlacePredictModel placePredictModel = new PlacePredictModel();
                placePredictModel.setIsPoi(false);
                placePredictModel.setPoiId("");
                placePredictModel.setName(posName);
                placePredictModel.setAddress(vicinity);
                placePredictModel.setType("");
                placePredictModel.setPlaceId(placeId);
                placePredictModel.setImage("");
                placePredictModel.setTag("");
                placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
                placePredictModel.setLatitude(lat);
                placePredictModel.setLongitude(lng);
                list.add(placePredictModel);
            }
        }
        return list;
    }

    @Override
    public List<PlacePredictModel> getSearchHis(String userId, String cityId) {
        LoggerUtil.logBiz("*********search history***********", null);
        CityModel cityModel = cityService.queryCity(cityId);
        String location = cityModel.getLatitude() + "," + cityModel.getLongitude();
        List<MapSearchHisModel> hisModels = mapSearchHisService.getMapSearchHis(userId, cityId);
        List<PlacePredictModel> predictModels = Lists.newArrayList();
        for(MapSearchHisModel his : hisModels) {
            predictModels.add(convertToPredict(his, location));
        }
        return predictModels;
    }


    private String getDetailsUrl(String placeId) {
        StringBuilder detailsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
        detailsUrl.append("?placeid=");
        detailsUrl.append(placeId);
        detailsUrl.append("&review_summary=true");
        detailsUrl.append("&language=zh-CN");
        detailsUrl.append("&key=");
        detailsUrl.append(baseService.getKey(SpiderKeyEnum.GOOGLE_PLACE.getType()));
        return detailsUrl.toString();
    }


    private String getNearbyUrl(String location, String name) {
        StringBuilder nearbyUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json");
        nearbyUrl.append("?location=");
        nearbyUrl.append(location);
        nearbyUrl.append("&name=");
        nearbyUrl.append(name);
        nearbyUrl.append("&radius=50000");
        nearbyUrl.append("&distance=true");
        nearbyUrl.append("&key=");
        nearbyUrl.append(baseService.getKey(SpiderKeyEnum.GOOGLE_PLACE.getType()));
        nearbyUrl.append("&language=zh-CN");
        return nearbyUrl.toString();
    }

    private String getPlaceCompleteUrl(String input, String location) {
        StringBuilder placeCompleteUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json");
        placeCompleteUrl.append("?input=");
        placeCompleteUrl.append(input);
        placeCompleteUrl.append("&radius=50000");
        placeCompleteUrl.append("&location=");
        placeCompleteUrl.append(location);
        placeCompleteUrl.append("&key=");
        placeCompleteUrl.append(baseService.getKey(SpiderKeyEnum.GOOGLE_PLACE.getType()));
        placeCompleteUrl.append("&language=zh-CN");
        return placeCompleteUrl.toString();
    }

    private PlacePredictModel convertToPredict(MapSearchHisModel his, String location) {
        PlacePredictModel placePredictModel = new PlacePredictModel();
        placePredictModel.setIsPoi(his.getIsPoi());
        placePredictModel.setPoiId(his.getPoiId());
        placePredictModel.setName(his.getName());
        placePredictModel.setAddress(his.getAddress());
        placePredictModel.setImage(his.getImage());
        placePredictModel.setTag(his.getTag());
        placePredictModel.setType(his.getType());
        placePredictModel.setPlaceId(his.getPlaceId());
        String dest = his.getLatitude() + "," + his.getLongitude();
        placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
        placePredictModel.setLatitude(his.getLatitude());
        placePredictModel.setLongitude(his.getLongitude());
        return placePredictModel;
    }
}

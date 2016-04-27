package me.weego.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import me.weego.constant.SpiderKeyEnum;
import me.weego.model.*;
import me.weego.service.*;
import me.weego.util.DistanceUtil;
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
    public JSONObject getPlaceDetails(String placeId) {
        LoggerUtil.logBiz("***** Google place details search start *****", null);
        checkArgument(StringUtils.isNotBlank(placeId), "param placeId should not blank");
        String detailsUrl = getDetailsUrl(placeId);
        LoggerUtil.logBiz("***** detailsUrl", detailsUrl);
        return JSONObject.parseObject(baseService.getHttpRequest(detailsUrl));
    }

    @Override
    public JSONObject getPlaceDetails(String location, String address) {
        LoggerUtil.logBiz("get place details by location and address", null);
        String nearBys = getNearBy(location, address);
        JSONObject nearByJson = JSONObject.parseObject(nearBys);
        JSONArray results = nearByJson.getJSONArray("results");
        if(results != null && results.size() > 0) {
            JSONObject firstPlace = results.getJSONObject(0);
            String placeId = firstPlace.getString("place_id");
            JSONObject placeDetails = getPlaceDetails(placeId);
            placeDetails.put("isGoogle", true);
            placeDetails.put("address", address);
            return placeDetails;
        } else {
            JSONObject placeDetails = new JSONObject();
            placeDetails.put("isGoogle", false);
            placeDetails.put("address", address);
            return placeDetails;
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
    public List<PlacePredictModel> getPlacePredict(String name, String location) {
        LoggerUtil.logBiz("**** Google place predict start ****", null);
        checkArgument(StringUtils.isNotBlank(name), "param name should not be blank");
        LoggerUtil.logBiz("获取匹配的景点地标", null);
        List<PlacePredictModel> list = Lists.newArrayList();
        List<AttractionModel> attractionModels = attractionService.queryByName(name);
        for(AttractionModel model : attractionModels) {
            PlacePredictModel placePredictModel = new PlacePredictModel();
            placePredictModel.setIsPoi(true);
            placePredictModel.setPoiId(model.getId().toString());
            placePredictModel.setType(model.getType());
            placePredictModel.setName(model.getAttractions());
            placePredictModel.setAddress(model.getAddress());
            placePredictModel.setImage(model.getCoverImage());
            placePredictModel.setTag(model.getTag());

            String dest = model.getLongitude() + "," + model.getLatitude();
            placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
            placePredictModel.setPlaceId(model.getPlaceId());
            list.add(placePredictModel);
        }

        LoggerUtil.logBiz("获取匹配的餐厅", null);
        List<RestaurantModel> restaurantModels = restaurantService.queryByName(name);
        for(RestaurantModel model : restaurantModels) {
            PlacePredictModel placePredictModel = new PlacePredictModel();
            placePredictModel.setIsPoi(true);
            placePredictModel.setPoiId(model.getId().toString());
            placePredictModel.setType(model.getType());
            placePredictModel.setName(model.getName());
            placePredictModel.setImage(model.getCoverImage());
            placePredictModel.setTag(model.getTag());
            String dest = model.getLongitude() + "," + model.getLatitude();
            placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
            placePredictModel.setPlaceId(model.getPlaceId());
            list.add(placePredictModel);
        }

        LoggerUtil.logBiz("获取匹配的购物", null);
        List<ShoppingModel> shoppingModels = shoppingService.queryByName(name);
        for(ShoppingModel model : shoppingModels) {
            PlacePredictModel placePredictModel = new PlacePredictModel();
            placePredictModel.setIsPoi(true);
            placePredictModel.setPoiId(model.getId().toString());
            placePredictModel.setType(model.getType());
            placePredictModel.setName(model.getName());
            placePredictModel.setImage(model.getCoverImage());
            placePredictModel.setTag(model.getTag());
            String dest = model.getLongitude() + "," + model.getLatitude();
            placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
            placePredictModel.setPlaceId(model.getPlaceId());
            list.add(placePredictModel);
        }

        LoggerUtil.logBiz("获取匹配的购物圈", null);
        List<AreaModel> areaModels = areaService.queryByName(name);
        for(AreaModel model : areaModels) {
            PlacePredictModel placePredictModel = new PlacePredictModel();
            placePredictModel.setIsPoi(true);
            placePredictModel.setPoiId(model.getId().toString());
            placePredictModel.setType(model.getType());
            placePredictModel.setName(model.getAreaName());
            placePredictModel.setImage(model.getCoverImage());
            placePredictModel.setTag(model.getTag());
            String dest = model.getLongitude() + "," + model.getLatitude();
            placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
            placePredictModel.setPlaceId(model.getPlaceId());
            list.add(placePredictModel);
        }

        String googlePlaces = getPlaceComplete(name, location);
        JSONObject placesJson = JSON.parseObject(googlePlaces);
        if("OK".equals(placesJson.getString("status"))) {
            JSONArray predictions = placesJson.getJSONArray("predictions");
            if(predictions != null && predictions.size() > 0) {
                for(int i = 0; i < predictions.size(); i++) {
                    JSONObject prediction = predictions.getJSONObject(i);
                    String placeId = prediction.getString("place_id");
//                    String desc = prediction.getString("description");
                    JSONObject placeDetail = getPlaceDetails(placeId);
                    String desc = placeDetail.getString("name");
                    String address = placeDetail.getString("formatted_address");
                    JSONObject placeLocation = placeDetail.getJSONObject("geometry").getJSONObject("location");
                    String lat = placeLocation.getString("lat");
                    String lng = placeLocation.getString("lng");
                    String dest = lng + "," + lat;

                    PlacePredictModel placePredictModel = new PlacePredictModel();
                    placePredictModel.setIsPoi(false);
                    placePredictModel.setPoiId("");
                    placePredictModel.setName(desc);
                    placePredictModel.setAddress(address);
                    placePredictModel.setType("");
                    placePredictModel.setPlaceId(placeId);
                    placePredictModel.setImage("");
                    placePredictModel.setTag("");
                    placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
                    list.add(placePredictModel);
                }
            }
        }

        return list;
    }

    @Override
    public List<PlacePredictModel> getSearchHis(String userId, String cityId, String location) {
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
        nearbyUrl.append("&radius=500");
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
        String dest = his.getLongitude() + "," + his.getLatitude();
        placePredictModel.setDistance(DistanceUtil.formatDistance(location, dest));
        return placePredictModel;
    }
}

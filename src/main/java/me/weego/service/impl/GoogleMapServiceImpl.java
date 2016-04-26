package me.weego.service.impl;

import me.weego.constant.SpiderKeyEnum;
import me.weego.service.BaseService;
import me.weego.service.GoogleMapService;
import me.weego.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javax.annotation.Resource;

import static com.google.common.base.Preconditions.checkArgument;
/**
 * @author ln
 */
@Service
public class GoogleMapServiceImpl implements GoogleMapService {
    @Resource
    private BaseService baseService;

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
    public String getPlaceComplete(String name) {
        return null;
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
}

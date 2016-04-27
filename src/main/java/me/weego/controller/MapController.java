package me.weego.controller;

import com.alibaba.fastjson.JSONObject;
import me.weego.model.MapSearchHisModel;
import me.weego.model.PlacePredictModel;
import me.weego.pojo.ResBody;
import me.weego.service.GoogleMapService;
import me.weego.service.MapSearchHisService;
import me.weego.util.EncodingTool;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ln
 */
@RestController
@RequestMapping("/map")
public class MapController {
    @Resource
    private GoogleMapService googleMapService;

    @Resource
    private MapSearchHisService mapSearchHisService;

    @RequestMapping(value = "place/details", method = RequestMethod.GET)
    public ResBody<JSONObject> getPlaceDetails(@RequestParam String location,
                                      @RequestParam String address) {
        address = EncodingTool.encodeStr(address);
        return ResBody.returnSuccess(googleMapService.getPlaceDetails(location, address));
    }

    @RequestMapping(value = "placeId/details", method = RequestMethod.GET)
    public ResBody<JSONObject> getPlaceDetails(@RequestParam String placeId) {
        return ResBody.returnSuccess(googleMapService.getPlaceDetails(placeId));
    }

    @RequestMapping(value = "search/history", method = RequestMethod.GET)
    public ResBody<List<MapSearchHisModel>> getSearchHistory(@RequestParam String userId,
                                                             @RequestParam String cityId) {
        return ResBody.returnSuccess(mapSearchHisService.getMapSearchHis(userId, cityId));
    }

    @RequestMapping(value = "place/predict", method = RequestMethod.GET)
    public ResBody<List<PlacePredictModel>> getPlacePredict(@RequestParam String place,
                                                            @RequestParam String location) {
        place = EncodingTool.encodeStr(place);
        return ResBody.returnSuccess(googleMapService.getPlacePredict(place, location));
    }

    @RequestMapping(value = "history/poi/add", method = RequestMethod.POST)
    public void saveSearchHis(@RequestParam String userId,
                              @RequestParam String cityId,
                              @RequestParam String poiId,
                              @RequestParam String type) {
        mapSearchHisService.saveSearchHis(cityId, userId, type, poiId);

    }

    @RequestMapping(value = "history/google/add", method = RequestMethod.POST)
    public void saveSearchHis(@RequestParam String userId,
                              @RequestParam String cityId,
                              @RequestParam String placeId) {
        mapSearchHisService.saveSearchHis(cityId, userId, placeId);
    }



    @RequestMapping(value = "search/history/del", method = RequestMethod.DELETE)
    public void deleteSearchHis(@RequestParam String userId,
                                @RequestParam String cityId) {
        mapSearchHisService.deleteSearchHis(userId, cityId);
    }
}

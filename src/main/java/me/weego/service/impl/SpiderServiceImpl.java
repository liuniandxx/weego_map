package me.weego.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.weego.constant.SpiderKeyEnum;
import me.weego.service.BaseService;
import me.weego.service.SpiderService;
import me.weego.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author tcl
 */
@Service
public class SpiderServiceImpl  implements SpiderService {

    @Resource
    private BaseService baseService;

    public String translate(String text) {
        String key= baseService.getKey("4");
        key=key.replaceAll("\\&","");
        String baiduUrl = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id="+key+"&q="+text+"&from=auto&to=zh";
        return baseService.getHttpRequest(baiduUrl);
    }


    public String getGoogleTraffic(String mode, String origin, String dest) {
        checkArgument(StringUtils.isNotBlank(mode), "param should with mode");
        checkArgument(StringUtils.isNotBlank(origin)
                && origin.contains(","), "error param origin");
        checkArgument(StringUtils.isNotBlank(dest)
                && dest.contains(","), "error param destination");
        String googleUrl = getGoogleTrafficUrl(mode, origin, dest);
        return baseService.getHttpRequest(googleUrl);
    }

    public String getGoogleTraffic(String origin, String dest) {
        try{
            LoggerUtil.logBiz("beginnig fetch traffic **************",null);
            String bus = getGoogleTraffic("transit", origin, dest);
            String driving= getGoogleTraffic("driving", origin, dest);
            String walking = getGoogleTraffic("walking", origin, dest);
            LoggerUtil.logBiz("bus traffic **************",bus);
            LoggerUtil.logBiz("driving traffic **************", driving);
            LoggerUtil.logBiz("walking traffic **************", walking);
            if("".equals(bus) || "".equals(driving)){
                return "";
            }
            JSONObject backObj = new JSONObject(); //

            JSONObject busBackJson = parseRoutes(bus);        //
            backObj.put("bus",busBackJson);

            JSONObject drivingBackJson = parseRoutes(driving);
            backObj.put("driver",drivingBackJson);

            JSONObject walkBackJson = parseRoutes(walking);
            backObj.put("walk", walkBackJson);
            return backObj.toString();
        }catch (Exception e){
            return "";
        }
    }

    private JSONObject parseRoutes(String result) {
        JSONObject resultJson = JSONObject.parseObject(result);     //
        String status = resultJson.getString("status");
        JSONObject backJson = new JSONObject();        //
        if("OK".equals(status)){
            JSONArray routesJson = resultJson.getJSONArray("routes");
            for(int i = 0; i < routesJson.size(); i++) {
                JSONObject routeJson = routesJson.getJSONObject(i);
                JSONObject legsJson = (JSONObject) routeJson.getJSONArray("legs").get(0);
                backJson.put("routes", routesJson);
                backJson.put("duration", legsJson.getJSONObject("duration").get("value"));
                backJson.put("distance", legsJson.getJSONObject("distance").get("value"));
            }
            backJson.put("status", "OK");
        }
        else if("ZERO_RESULTS".equals(status)){
            backJson.put("routes","{html: 'Zero results errors'}");
            backJson.put("duration","0");
            backJson.put("distance","0");
            backJson.put("status", "ZERO_RESULTS");
        }else{
            backJson.put("routes","{html: 'Google Not Found'}");
            backJson.put("duration","0");
            backJson.put("distance","0");
            backJson.put("status", "NO_FOUND");
        }
        return backJson;
    }


    private String getGoogleTrafficUrl(String mode, String origin, String dest) {
        String googleUrl = "https://maps.googleapis.com/maps/api/directions/json";
        googleUrl += "?origin=" + origin;
        googleUrl += "&destination=" + dest;
        googleUrl += "&mode=" + mode;
        googleUrl += "&sensor=false";
        googleUrl += "&alternatives=true";
        googleUrl += "&key=" + baseService.getKey(SpiderKeyEnum.GOOGLE_ONLINE.getType());
        googleUrl += "&language=zh-CN";
        return googleUrl;
    }
}

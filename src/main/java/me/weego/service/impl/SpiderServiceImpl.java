package me.weego.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import me.weego.constant.SpiderKeyEnum;
import me.weego.service.BaseService;
import me.weego.service.SpiderService;
import me.weego.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author tcl
 */
@Service
public class SpiderServiceImpl  implements SpiderService {

    @Resource
    private BaseService baseService;

    public String translate(String text) {
        String key=baseService.getKey("4");
        key=key.replaceAll("\\&","");
        String baiduUrl = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id="+key+"&q="+text+"&from=auto&to=zh";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baiduUrl)
                .build();

        String result = "";
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                result = response.body().string();
            }
        } catch (IOException e) {
            LoggerUtil.logError("google http error", e);
        }
        return result;
    }


    public String getGoogleTraffic(String mode, String origin, String dest) {
        checkArgument(StringUtils.isNotBlank(mode), "param should with mode");
        checkArgument(StringUtils.isNotBlank(origin)
                && origin.contains(","), "error param origin");
        checkArgument(StringUtils.isNotBlank(dest)
                && dest.contains(","), "error param destination");
        String googleUrl = getGoogleTrafficUrl(mode, origin, dest);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(googleUrl)
                .build();

        String result = "";
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                result = response.body().string();
            }
        } catch (IOException e) {
            LoggerUtil.logError("google http error", e);
        }
        return result;
    }


    public String getGoogleTraffic(String origin, String dest) {
        try{
            LoggerUtil.logBiz("beginnig fetch traffic **************",null);
            String bus=getGoogleTraffic("transit", origin, dest);
            String driving=getGoogleTraffic("driving", origin, dest);
            LoggerUtil.logBiz("bus traffic **************",bus);
            LoggerUtil.logBiz("driving traffic **************",driving);
            if("".equals(bus) || "".equals(driving)){
                return "";
            }
            JSONObject backObj=new JSONObject(); //
            JSONObject busJson=new JSONObject(bus);     //
            String busStatus=busJson.getString("status");
            JSONObject busBackJson=new JSONObject();        //
            if("OK".equals(busStatus)){
                busJson=(JSONObject)busJson.getJSONArray("routes").get(0);
                busJson=(JSONObject) busJson.getJSONArray("legs").get(0);
                System.out.println(busJson.toString());
                busBackJson.put("steps",busJson.getJSONArray("steps"));
                busBackJson.put("duration",busJson.getJSONObject("duration").get("value"));
                busBackJson.put("distance",busJson.getJSONObject("distance").get("value"));
            }
            else if("ZERO_RESULTS".equals(busStatus)){
                busBackJson.put("steps","{html: 'Zero results errors'}");
                busBackJson.put("duration","0");
                busBackJson.put("distance","0");
            }else{
                busBackJson.put("steps","{html: 'Google Not Found'}");
                busBackJson.put("duration","0");
                busBackJson.put("distance","0");
            }
            backObj.put("bus",busBackJson);
            JSONObject drivingJson=new JSONObject(driving);     //
            String drivingStatus=drivingJson.getString("status");
            JSONObject drivingBackJson=new JSONObject();        //
            if("OK".equals(drivingStatus)){
                drivingJson=(JSONObject)drivingJson.getJSONArray("routes").get(0);
                drivingJson=(JSONObject) drivingJson.getJSONArray("legs").get(0);
                drivingBackJson.put("steps", drivingJson.getJSONArray("steps"));
                drivingBackJson.put("duration", drivingJson.getJSONObject("duration").get("value"));
                drivingBackJson.put("distance", drivingJson.getJSONObject("distance").get("value"));
            }
            else if("ZERO_RESULTS".equals(drivingStatus)){
                drivingBackJson.put("steps", "{html: 'Zero results errors'}");
                drivingBackJson.put("duration", "0");
                drivingBackJson.put("distance", "0");
            }else{
                drivingBackJson.put("steps", "{html: 'Google Not Found'}");
                drivingBackJson.put("duration", "0");
                drivingBackJson.put("distance", "0");
            }
            backObj.put("driver",drivingBackJson);
            System.out.println(backObj.toString());
            return backObj.toString();
        }catch (Exception e){
            return "";
        }
    }

    private String getGoogleTrafficUrl(String mode, String origin, String dest) {
        String googleUrl = "https://maps.googleapis.com/maps/api/directions/json";
        googleUrl += "?origin=" + origin;
        googleUrl += "&destination=" + dest;
        googleUrl += "&mode=" + mode;
        // var departure_time = Math.round(new Date().getTime()/1000);
     //   googleUrl += "&departure_time=" + 1415878654;//departure_time;
        googleUrl += "&sensor=false";
        googleUrl += "&key=" + baseService.getKey(SpiderKeyEnum.GOOGLE_ONLINE.getType());
        googleUrl += "&language=zh-CN";
        System.out.println(googleUrl);

        return googleUrl;
    }
}

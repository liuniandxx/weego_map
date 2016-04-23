package me.weego.service.impl;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import me.weego.constant.SpiderKeyEnum;
import me.weego.service.BaseService;
import me.weego.service.UberService;
import me.weego.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by  on 16-4-23.
 */

@Service
public class UberServiceImpl implements UberService {

    @Resource
    private BaseService baseService;

    @Override
    public String getEstimatePrice(String start, String end) {
        return getEstimatePrice(start, end, 2);
    }

    @Override
    public String getEstimatePrice(String start, String end, int seatCount) {
        checkArgument(start.contains(","), "error param start");
        checkArgument(end.contains(","), "error param end");
        String[] startPoint = start.split(",", 2);
        String[] endPoint = end.split(",", 2);

        return getEstimatePrice(startPoint[1], startPoint[0], endPoint[1], endPoint[0], seatCount);
    }

    private String getEstimatePrice(String startLatitude, String startLongitude, String endLatitude, String endLongitude, int seatCount) {
        checkArgument(numericalValidation(startLatitude), "param should with startLatitude and numerical");
        checkArgument(numericalValidation(startLongitude), "param should with startLongitude and numerical");
        checkArgument(numericalValidation(endLatitude), "param should with endLatitude and numerical");
        checkArgument(numericalValidation(endLongitude), "param should with endLongitude and numerical");
        checkArgument(seatCount <= 2, "param seatCount should leq 2");

        String uberUrl = getUberEstimatePriceUrl(startLatitude, startLongitude, endLatitude, endLongitude, seatCount);
        LoggerUtil.logBiz("request uber url", uberUrl);
        LoggerUtil.logBiz("beginnig fetch estimates price **************",null);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(uberUrl)
                .build();

        String result = "";
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                result = response.body().string();
            }
        } catch (IOException e) {
            LoggerUtil.logError("uber estimates price http error", e);
        }
        LoggerUtil.logBiz("ending fetch estimates price **************",null);
        LoggerUtil.logBiz("estimates price result", result);
        return result;
    }

    private String getUberEstimatePriceUrl(String startLatitude, String startLongitude, String endLatitude, String endLongitude, int seatCount) {
        String uberUrl = "https://api.uber.com.cn/v1/estimates/price";
        uberUrl += "?start_latitude=" + startLatitude;
        uberUrl += "&start_longitude=" + startLongitude;
        uberUrl += "&end_latitude=" + endLatitude;
        uberUrl += "&end_longitude=" + endLongitude;
        uberUrl += "&seat_count=" + seatCount;
        uberUrl += "&server_token=" + baseService.getKey(SpiderKeyEnum.UBER_SERVERTOKEN.getType());
        System.out.println(uberUrl);
        return uberUrl;
    }

    private boolean numericalValidation(String value) {
        return value.matches("^[0-9]+(\\.[0-9]+)?$");
    }
}

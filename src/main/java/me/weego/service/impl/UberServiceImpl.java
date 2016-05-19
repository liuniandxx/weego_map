package me.weego.service.impl;

import me.weego.constant.SpiderKeyEnum;
import me.weego.service.BaseService;
import me.weego.service.UberService;
import me.weego.util.LoggerUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

        return getEstimatePrice(startPoint[0], startPoint[1], endPoint[0], endPoint[1], seatCount);
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
        String result = baseService.getHttpRequest(uberUrl);
        LoggerUtil.logBiz("ending fetch estimates price **************",null);
        LoggerUtil.logBiz("estimates price result", result);
        return result;
    }

    private String getUberEstimatePriceUrl(String startLatitude, String startLongitude, String endLatitude, String endLongitude, int seatCount) {
        String uberUrl = "https://api.uber.com/v1/estimates/price";
        uberUrl += "?start_latitude=" + startLatitude;
        uberUrl += "&start_longitude=" + startLongitude;
        uberUrl += "&end_latitude=" + endLatitude;
        uberUrl += "&end_longitude=" + endLongitude;
        uberUrl += "&seat_count=" + seatCount;
        uberUrl += "&server_token=" + baseService.getKey(SpiderKeyEnum.UBER_SERVERTOKEN.getType());
        return uberUrl;
    }

    private boolean numericalValidation(String value) {
        return value.matches("^[+-]?[0-9]+(\\.[0-9]+)?$");
    }
}

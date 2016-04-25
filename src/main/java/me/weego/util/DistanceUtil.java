package me.weego.util;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by liuniandxx on 16-4-23.
 */
public class DistanceUtil {

    private static final double EARTH_RADIUS = 6378137.0;



    public static double getDistance(String lat1, String lon1, String lat2, String lon2) {
        checkArgument(StringUtils.isNotBlank(lat1) && StringUtils.isNotBlank(lon1), "error param location1");
        checkArgument(StringUtils.isNotBlank(lat2) && StringUtils.isNotBlank(lon2), "error param location2");

        double radLat1 = getRad(Double.parseDouble(lat1));
        double radLat2 = getRad(Double.parseDouble(lat2));

        double a = radLat1 - radLat2;
        double b = getRad(Double.parseDouble(lon1)) - getRad(Double.parseDouble(lon2));


        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        // s = Math.round(s * 10000) / 10000.0;
        s = Math.round(s);
        return s;
    }

    private static double getRad(double d) {
        return d * Math.PI / 180.0;
    }

    public static void main(String[] args) {
        System.out.println(getDistance("39.997224", "116.479589", "39.90960456049752", "116.3972282409668"));
    }
}

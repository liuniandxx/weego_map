package me.weego.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.types.ObjectId;

/**
 * @author ln
 */
public class CityModel {
    private ObjectId cityId;

    private String cityName;

    private String latitude;

    private String longitude;

    public ObjectId getCityId() {
        return cityId;
    }

    public void setCityId(ObjectId cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("cityId", cityId)
                .append("cityName", cityName)
                .append("latitude", latitude)
                .append("longitude", longitude)
                .toString();
    }
}

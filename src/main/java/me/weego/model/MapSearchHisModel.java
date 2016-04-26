package me.weego.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * @author ln
 */
public class MapSearchHisModel {

    private ObjectId id;

    private String userId;

    private ObjectId cityId;

    private boolean isPoi;

    private String poiId;

    private String type;

    private String placeId;

    private int distance;

    private String name;

    private String address;

    private Date lastModifyTime;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ObjectId getCityId() {
        return cityId;
    }

    public void setCityId(ObjectId cityId) {
        this.cityId = cityId;
    }

    public boolean getIsPoi() {
        return isPoi;
    }

    public void setIsPoi(boolean isPoi) {
        isPoi = isPoi;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                    .append("id", id)
                    .append("userId", userId)
                    .append("cityId", cityId)
                    .append("isPoi", isPoi)
                    .append("poiId", poiId)
                    .append("type", type)
                    .append("name", name)
                    .append("address", address)
                    .append("distance", distance)
                    .append("lastModifyTime", lastModifyTime)
                    .toString();
    }
}

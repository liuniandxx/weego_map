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

    private String latitude;

    private String longitude;

    private String name;

    private String address;

    private String image;

    private String tag;

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
        this.isPoi = isPoi;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
                    .append("latitude", latitude)
                    .append("longitude", longitude)
                    .append("lastModifyTime", lastModifyTime)
//                    .append("lastMon")
                    .toString();
    }
}

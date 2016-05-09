package me.weego.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author ln
 */
public class PlacePredictModel {
    private String isPoi;

    private String poiId;

    private String type;

    private String placeId;

    private String distance;

    private String name;

    private String address;

    private String latitude;

    private String longitude;

    private String image;

    private String tag;


    public String getIsPoi() {
        return isPoi;
    }

    public void setIsPoi(String poi) {
        this.isPoi = poi;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
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
                    .append("isPoi", isPoi)
                    .append("poiId", poiId)
                    .append("type", type)
                    .append("placeId", placeId)
                    .append("distance", distance)
                    .append("name", name)
                    .append("address", address)
                    .append("latitude", latitude)
                    .append("longitude", longitude)
                    .append("image", image)
                    .append("tag")
                    .toString();
    }
}

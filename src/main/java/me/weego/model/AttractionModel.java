package me.weego.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.types.ObjectId;

/**
 * @author ln
 * 景点
 */
public class AttractionModel {
    private ObjectId id;

    private String attractions;

    private String attractionsEn;

    private String latitude;

    private String longitude;

    private String address;

    private String type;

    private String placeId;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAttractions() {
        return attractions;
    }

    public void setAttractions(String attractions) {
        this.attractions = attractions;
    }

    public String getAttractionsEn() {
        return attractionsEn;
    }

    public void setAttractionsEn(String attractionsEn) {
        this.attractionsEn = attractionsEn;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("attractions", attractions)
                .append("attractionsEn", attractionsEn)
                .append("latitude", latitude)
                .append("longitude", longitude)
                .append("address", address)
                .append("type", type)
                .append("pla", placeId)
                .toString();
    }
}




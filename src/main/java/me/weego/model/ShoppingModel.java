package me.weego.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.types.ObjectId;

/**
 * @author ln
 * 购物
 */
public class ShoppingModel {
    private ObjectId id;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                    .append("name", name)
                    .append("latitude", latitude)
                    .append("longitude", longitude)
                    .append("address", address)
                    .append("type", type)
                    .append("placeId", placeId)
                    .toString();
    }
}

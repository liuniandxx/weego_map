package me.weego.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author tcl
 * 餐厅
 */
public class RestaurantModel {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}

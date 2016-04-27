package me.weego.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import me.weego.model.RestaurantModel;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author tcl
 */
@Repository
public class RestaurantDao {
    @Resource
    private Mongo mongo;
    private MongoCollection<Document> collection;

    @PostConstruct
    private void init() {
        this.collection = mongo.getCollection("restaurants");
    }

    public List<RestaurantModel> query() {
        FindIterable<Document> iterable = collection.find().limit(1);
        final List<RestaurantModel> list = Lists.newArrayList();
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setName(iterable.first().getString("city_name"));
        list.add(restaurantModel);
        return list;
    }

    public List<RestaurantModel> queryByName(String name) {
        Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject();
        query.put("name", pattern);
        BasicDBObject sort = new BasicDBObject();
        sort.put("name", 1);
        final List<RestaurantModel> list = Lists.newArrayList();
        FindIterable<Document> iterable = collection.find(query).sort(sort);
        for (Document elem : iterable) {
            list.add(convertToRestaurant(elem));
        }
        return list;
    }

    public RestaurantModel findById(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        FindIterable<Document> iterable = collection.find(query);
        for(Document elem : iterable) {
            return convertToRestaurant(elem);
        }
        return null;
    }

    private RestaurantModel convertToRestaurant(Document elem) {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(elem.getObjectId("_id"));
        restaurantModel.setName(elem.getString("name"));
        restaurantModel.setAddress(Strings.nullToEmpty(elem.getString("address")));
        restaurantModel.setLatitude(Strings.nullToEmpty(elem.getString("latitude")));
        restaurantModel.setLongitude(Strings.nullToEmpty(elem.getString("longitude")));
        restaurantModel.setType(Strings.nullToEmpty(elem.getString("type")));
        restaurantModel.setPlaceId(Strings.nullToEmpty(elem.getString("place_id")));
        restaurantModel.setCoverImage(Strings.nullToEmpty(elem.getString("cover_image")));

        String json = elem.toJson();
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray category = jsonObject.getJSONArray("category");
        if(category != null && category.size() > 0) {
            for(int i= 0; i < category.size(); i++) {
                if(!Strings.isNullOrEmpty(category.getJSONObject(i).getString("name"))) {
                    restaurantModel.setTag(category.getJSONObject(i).getString("name"));
                }
            }
        } else {
            restaurantModel.setTag("");
        }
        return restaurantModel;
    }
}

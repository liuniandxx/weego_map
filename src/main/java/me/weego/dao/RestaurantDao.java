package me.weego.dao;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import me.weego.model.RestaurantModel;
import me.weego.util.LoggerUtil;
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

    public List<RestaurantModel> queryByName(String name, String cityId) {
        Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject();
        query.put("name", pattern);
        query.put("city_id", new ObjectId(cityId));
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
        System.out.println(elem.getObjectId("_id"));
        restaurantModel.setName(elem.getString("name"));
        restaurantModel.setAddress(Strings.nullToEmpty(elem.getString("address")));

        restaurantModel.setType(Strings.nullToEmpty(elem.getString("type")));
        restaurantModel.setPlaceId(Strings.nullToEmpty(elem.getString("place_id")));
        restaurantModel.setCoverImage(Strings.nullToEmpty(elem.getString("cover_image")));

        try {
            restaurantModel.setLatitude(Strings.nullToEmpty(elem.getString("latitude")));
        } catch(Exception e) {
            LoggerUtil.logBiz("cast exception", e);
            Double lat = elem.getDouble("latitude");
            restaurantModel.setLatitude(lat.toString());
        }

        try {
            restaurantModel.setLongitude(Strings.nullToEmpty(elem.getString("longitude")));
        } catch(Exception e) {
            LoggerUtil.logBiz("cast exception", e);
            Double lng = elem.getDouble("longitude");
            restaurantModel.setLatitude(lng.toString());
        }

        restaurantModel.setTag("");
        List<Document> category = (List<Document>)elem.get("category");
        if(category != null && category.size() > 0) {
            for(Document cata : category) {
                if(!Strings.isNullOrEmpty(cata.getString("name"))) {
                    restaurantModel.setTag(cata.getString("name"));
                    break;
                }
            }
        }
        return restaurantModel;
    }
}

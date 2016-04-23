package me.weego.dao;

import com.google.common.collect.Lists;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import me.weego.model.RestaurantModel;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

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
}

package me.weego.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import me.weego.model.ShoppingModel;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author ln
 */
@Repository
public class ShoppingDao {
    @Resource
    private Mongo mongo;
    private MongoCollection<Document> collection;

    @PostConstruct
    private void init() {
        this.collection = mongo.getCollection("shoppings");
    }

    public List<ShoppingModel> queryByName(String name) {
        Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject();
        query.put("name", pattern);
        BasicDBObject sort = new BasicDBObject();
        sort.put("name", 1);
        final List<ShoppingModel> list = Lists.newArrayList();
        FindIterable<Document> iterable = collection.find(query).sort(sort);
        for(Document elem : iterable) {
            list.add(convertToShopping(elem));
        }
        return list;
    }

    public ShoppingModel findById(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        FindIterable<Document> iterable = collection.find(query);
        for(Document elem : iterable) {
            return convertToShopping(elem);
        }
        return null;
    }

    private ShoppingModel convertToShopping(Document elem) {
        ShoppingModel shoppingModel = new ShoppingModel();
        shoppingModel.setId(elem.getObjectId("_id"));
        shoppingModel.setName(elem.getString("name"));
        shoppingModel.setLatitude(Strings.nullToEmpty(elem.getString("latitude")));
        shoppingModel.setLongitude(Strings.nullToEmpty(elem.getString("longitude")));
        shoppingModel.setAddress(Strings.nullToEmpty(elem.getString("address")));
        shoppingModel.setType(Strings.nullToEmpty(elem.getString("type")));
        shoppingModel.setPlaceId(Strings.nullToEmpty(elem.getString("place_id")));
        shoppingModel.setCoverImage(Strings.nullToEmpty(elem.getString("cover_image")));
        String json = elem.toJson();
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray category = jsonObject.getJSONArray("category");
        if(category != null && category.size() > 0) {
            for(int i= 0; i < category.size(); i++) {
                if(!Strings.isNullOrEmpty(category.getJSONObject(i).getString("name"))) {
                    shoppingModel.setTag(category.getJSONObject(i).getString("name"));
                }
            }
        } else {
            shoppingModel.setTag("");
        }

        return shoppingModel;
    }
}

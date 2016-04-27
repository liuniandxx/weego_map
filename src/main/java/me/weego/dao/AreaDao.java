package me.weego.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import me.weego.model.AreaModel;
import me.weego.util.LoggerUtil;
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
public class AreaDao {
    @Resource
    private Mongo mongo;
    private MongoCollection<Document> collection;

    @PostConstruct
    private void init() {
        this.collection = mongo.getCollection("areas");
    }

    public List<AreaModel> queryByName(String name, String cityId) {
        Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        values.add(new BasicDBObject("area_enname", pattern));
        values.add(new BasicDBObject("area_name", pattern));
        query.put("$or", values);
        query.put("city_id", new ObjectId(cityId));
        final List<AreaModel> list = Lists.newArrayList();
        FindIterable<Document> iterable = collection.find(query);
        for(Document elem : iterable) {
            list.add(convertToArea(elem));
        }
        return list;
    }

    public AreaModel findById(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        FindIterable<Document> iterable = collection.find(query);
        for(Document elem : iterable) {
            return convertToArea(elem);
        }
        return null;
    }

    private AreaModel convertToArea(Document elem) {
        AreaModel areaModel = new AreaModel();
        areaModel.setId(elem.getObjectId("_id"));
        areaModel.setAreaName(Strings.nullToEmpty(elem.getString("area_name")));
        areaModel.setAreaEnName(Strings.nullToEmpty(elem.getString("area_enname")));

        try {
            areaModel.setLatitude(Strings.nullToEmpty(elem.getString("latitude")));
            areaModel.setLongitude(Strings.nullToEmpty(elem.getString("longitude")));
        } catch (Exception e) {
            LoggerUtil.logBiz("cast exception", e);
            Double lat = elem.getDouble("latitude");
            Double lng = elem.getDouble("longitude");
            areaModel.setLatitude(lat.toString());
            areaModel.setLongitude(lng.toString());
        }

        areaModel.setAddress(Strings.nullToEmpty(elem.getString("address")));
        areaModel.setType(Strings.nullToEmpty(elem.getString("type")));
        areaModel.setPlaceId(Strings.nullToEmpty(elem.getString("place_id")));
        areaModel.setCoverImage(Strings.nullToEmpty(elem.getString("cover_image")));

        String json = elem.toJson();
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray tags = jsonObject.getJSONArray("tags");
        if(tags != null && tags.size() > 0) {
            areaModel.setTag(tags.get(0).toString());
        } else {
            areaModel.setTag("");
        }

        return areaModel;
    }
}

package me.weego.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import me.weego.model.AttractionModel;
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
public class AttractionDao {
    @Resource
    private Mongo mongo;
    private MongoCollection<Document> collection;

    @PostConstruct
    private void init() {
        this.collection = mongo.getCollection("latestattractions");
    }

    public List<AttractionModel> queryByName(String attractions) {
        Pattern pattern = Pattern.compile("^.*" + attractions + ".*$", Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        values.add(new BasicDBObject("attractions", pattern));
        values.add(new BasicDBObject("attractions_en", pattern));
        query.put("$or", values);
        final List<AttractionModel> list = Lists.newArrayList();
        FindIterable<Document> iterator = collection.find(query);
        for(Document elem : iterator) {
            list.add(convertToAttraction(elem));
        }
        return list;
    }

    public AttractionModel findById(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        FindIterable<Document> iterable = collection.find(query);
        for(Document elem : iterable) {
            return convertToAttraction(elem);
        }
        return null;
    }

    private AttractionModel convertToAttraction(Document elem) {
        AttractionModel attractionModel = new AttractionModel();
        attractionModel.setId(elem.getObjectId("_id"));
        attractionModel.setAttractions(Strings.nullToEmpty(elem.getString("attractions")));
        attractionModel.setAttractionsEn(Strings.nullToEmpty(elem.getString("attractions_en")));
        attractionModel.setAddress(Strings.nullToEmpty(elem.getString("address")));
        attractionModel.setLatitude(Strings.nullToEmpty(elem.getString("latitude")));
        attractionModel.setLongitude(Strings.nullToEmpty(elem.getString("longitude")));
        attractionModel.setPlaceId(Strings.nullToEmpty(elem.getString("place_id")));
        attractionModel.setCoverImage(Strings.nullToEmpty(elem.getString("coverImageName")));
        attractionModel.setType(Strings.nullToEmpty(elem.getString("type")));
        String json = elem.toJson();
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray subLabelNew = jsonObject.getJSONArray("subLabelNew");
        if(subLabelNew != null && subLabelNew.size() > 0) {
            for(int i= 0; i < subLabelNew.size(); i++) {
                if(!Strings.isNullOrEmpty(subLabelNew.getJSONObject(i).getString("label"))) {
                    attractionModel.setTag(subLabelNew.getJSONObject(i).getString("label"));
                }
            }
        } else {
            attractionModel.setTag("");
        }
        return attractionModel;
    }
}

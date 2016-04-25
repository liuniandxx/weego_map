package me.weego.dao;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import me.weego.model.AttractionModel;
import org.bson.Document;
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
            AttractionModel attractionModel = new AttractionModel();
            attractionModel.setId(elem.getObjectId("_id"));
            attractionModel.setAttractions(Strings.nullToEmpty(elem.getString("attractions")));
            attractionModel.setAttractionsEn(Strings.nullToEmpty(elem.getString("attractions_en")));
            attractionModel.setAddress(Strings.nullToEmpty(elem.getString("address")));
            attractionModel.setLatitude(Strings.nullToEmpty(elem.getString("latitude")));
            attractionModel.setLongitude(Strings.nullToEmpty(elem.getString("longitude")));
            attractionModel.setPlaceId(Strings.nullToEmpty(elem.getString("place_id")));
            list.add(attractionModel);
        }
        return list;
    }
}

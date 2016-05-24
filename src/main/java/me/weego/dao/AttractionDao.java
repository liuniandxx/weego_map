package me.weego.dao;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import me.weego.model.AttractionModel;
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
public class AttractionDao {
    @Resource
    private Mongo mongo;
    private MongoCollection<Document> collection;

    @PostConstruct
    private void init() {
        this.collection = mongo.getCollection("latestattractions");
    }

    public List<AttractionModel> queryByName(String attractions, String cityId) {
        Pattern pattern = Pattern.compile("^.*" + attractions + ".*$", Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        values.add(new BasicDBObject("attractions", pattern));
        values.add(new BasicDBObject("attractions_en", pattern));
        query.put("$or", values);
        query.put("city_id", new ObjectId(cityId));
        query.put("show_flag", 1);
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
        try {
            attractionModel.setLatitude(Strings.nullToEmpty(elem.getString("latitude")));
        } catch (Exception e) {
            LoggerUtil.logBiz("cast exception", e);
            Double lat = elem.getDouble("latitude");
            attractionModel.setLatitude(lat.toString());
        }

        try {
            attractionModel.setLongitude(Strings.nullToEmpty(elem.getString("longitude")));
        } catch (Exception e) {
            LoggerUtil.logBiz("cast exception", e);
            Double lng = elem.getDouble("longitude");
            attractionModel.setLongitude(lng.toString());
        }

        attractionModel.setPlaceId(Strings.nullToEmpty(elem.getString("place_id")));
        attractionModel.setCoverImage(Strings.nullToEmpty(elem.getString("coverImageName")));
        attractionModel.setType(Strings.nullToEmpty(elem.getString("type")));

        attractionModel.setTag("");
        List<Document> subLabelNew = (List<Document>)elem.get("subLabelNew");
        if(subLabelNew != null && subLabelNew.size() > 0) {
            for(Document labelNew : subLabelNew) {
                if(!Strings.isNullOrEmpty(labelNew.getString("label"))) {
                    attractionModel.setTag(labelNew.getString("label"));
                    break;
                }
            }
        }
        return attractionModel;
    }
}

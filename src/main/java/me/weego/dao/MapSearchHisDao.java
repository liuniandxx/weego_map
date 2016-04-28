package me.weego.dao;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import me.weego.model.MapSearchHisModel;
import me.weego.util.LoggerUtil;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author ln
 */
@Repository
public class MapSearchHisDao {
    @Resource
    private Mongo mongo;
    private MongoCollection<Document> collection;

    @PostConstruct
    private void init() {
        this.collection = mongo.getCollection("mapsearchhis");
    }

    public List<MapSearchHisModel> getMapSearchHis(String userId, String cityId) {
        BasicDBObject query = new BasicDBObject();
        query.put("user_id", userId);
        query.put("city_id", new ObjectId(cityId));
        BasicDBObject sort = new BasicDBObject();
        sort.put("last_modify_time", -1);
        FindIterable<Document> iterable = collection.find(query).sort(sort);
        List<MapSearchHisModel> list = Lists.newArrayList();
        for(Document elem : iterable) {
            MapSearchHisModel mapSearchHisModel = new MapSearchHisModel();
            mapSearchHisModel.setId(elem.getObjectId("_id"));
            mapSearchHisModel.setName(elem.getString("name"));
            mapSearchHisModel.setAddress(Strings.nullToEmpty(elem.getString("address")));
            mapSearchHisModel.setIsPoi(elem.getBoolean("is_poi"));
            mapSearchHisModel.setPoiId(elem.getString("poi_id"));
            mapSearchHisModel.setUserId(elem.getString("user_id"));
            mapSearchHisModel.setCityId(elem.getObjectId("city_id"));
            mapSearchHisModel.setType(Strings.nullToEmpty(elem.getString("type")));
            mapSearchHisModel.setLatitude(elem.getString("latitude"));
            mapSearchHisModel.setLongitude(elem.getString("longitude"));
            mapSearchHisModel.setImage(Strings.nullToEmpty(elem.getString("image")));
            mapSearchHisModel.setTag(Strings.nullToEmpty(elem.getString("tag")));
            mapSearchHisModel.setPlaceId(Strings.nullToEmpty(elem.getString("place_id")));
            list.add(mapSearchHisModel);
        }
        return list;
    }

    public void deleteSearchHis(String userId, String cityId) {
        BasicDBObject query = new BasicDBObject();
        query.put("user_id", userId);
        query.put("city_id", new ObjectId(cityId));

        DeleteResult results =  collection.deleteMany(query);

        LoggerUtil.logBiz("delete search history count ", results.getDeletedCount());
    }

    public boolean findOne(String cityId, String userId, String type, String poiId) {
        boolean res = false;
        BasicDBObject query = new BasicDBObject();
        query.put("user_id", userId);
        query.put("city_id", new ObjectId(cityId));
        query.put("is_poi", true);
        query.put("type", type);
        query.put("poi_id", poiId);

        FindIterable<Document> iterable = collection.find(query);
        for(Document elem : iterable) {
            res = true;
            collection.updateMany(query, new Document("$set", new Document("last_modify_time", new Date())));
            break;
        }
        return res;
    }

    public boolean findOne(String cityId, String userId, String placeId) {
        boolean res = false;
        BasicDBObject query = new BasicDBObject();
        query.put("user_id", userId);
        query.put("city_id", new ObjectId(cityId));
        query.put("is_poi", false);
        query.put("placeId", placeId);

        FindIterable<Document> iterable = collection.find(query);
        for(Document elem : iterable) {
            res = true;
            collection.updateMany(query, new Document("$set", new Document("last_modify_time", new Date())));
            break;
        }
        return res;
    }

    public void saveSearchHis(Document elem) {
        collection.insertOne(elem);
    }
}

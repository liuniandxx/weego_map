package me.weego.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import me.weego.model.CityModel;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author ln
 */
@Repository
public class CityDao {
    @Resource
    private Mongo mongo;
    private MongoCollection<Document> collection;

    @PostConstruct
    private void init() {
        this.collection = mongo.getCollection("latestcity");
    }

    public CityModel findById(String cityId) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(cityId));
        FindIterable<Document> iterable = collection.find(query);
        for(Document elem : iterable) {
            return convertToCity(elem);
        }
        return null;
    }

    public CityModel convertToCity(Document elem) {
        CityModel cityModel = new CityModel();

        cityModel.setCityId(elem.getObjectId("_id"));
        cityModel.setCityName(elem.getString("cityname"));
        cityModel.setLatitude(elem.getString("latitude"));
        cityModel.setLongitude(elem.getString("longitude"));
        return cityModel;
    }

}

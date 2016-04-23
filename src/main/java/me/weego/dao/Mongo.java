package me.weego.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * @author tcl
 */
public class Mongo {
    private String server;
    private String DB;

    private MongoClient mongoClient;

    private void init() {
        this.mongoClient = new MongoClient(server);
    }

    private void destroy() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public MongoCollection<Document> getCollection(String collection) {
        return mongoClient.getDatabase(DB).getCollection(collection);
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDB() {
        return DB;
    }

    public void setDB(String DB) {
        this.DB = DB;
    }

}

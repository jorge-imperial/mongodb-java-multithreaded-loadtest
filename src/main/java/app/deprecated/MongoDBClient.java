package app.deprecated;

/**
 * Created by jorgeimperial on 9/29/2020.
 */

import app.utils.MongoClientSingleton;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MongoDBClient {
    private static final Logger LOG = LoggerFactory.getLogger(MongoDBClient.class);

    final String MONGODB_PID = "com.mongodb.tse.examples";

    MongoClient mongoClient = null;

    public Boolean storeData(String path, Date date, Boolean status, Boolean mailstatus,
                             ConfigurationAdmin configAdmin){
        LOG.debug("method=storeData step=start the mongo connection");
        try {
            mongoClient =  MongoClientSingleton.getInstance();
            Configuration config = configAdmin.getConfiguration(MONGODB_PID);
            String databaseName = (String) config.getProperties().get("mongoDatabase");
            String collectionName = (String) config.getProperties().get("mongoCollection");

            MongoDatabase db = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = db.getCollection(collectionName);

            LOG.debug("Get the collection from mongo db: {}/{}",
                    collectionName,
                    collection.count());
            Date now = new Date();
            path = now.toString() + this.mongoClient;
            Document doc = new Document("path", path)
                    .append("pubstatus", status).append("pubdate", now.toString())
                    .append("mailstatus", false);

            collection.insertOne(doc);

        } catch (IOException e) {
            LOG.error("IOException", e);
        }
        finally {

            if (mongoClient != null) {
                LOG.debug("Not closing the mongo connection");
            }
        }
        LOG.debug("End");
        return false;
    }


    public List<String> retrieveData(ConfigurationAdmin configAdmin) {
        LOG.debug("method=retrieveData step=start the mongo connection");
        List<String> paths = new ArrayList<String>();
        try {
            mongoClient =  MongoClientSingleton.getInstance();
            Configuration config = configAdmin.getConfiguration(MONGODB_PID);
            String databaseName = (String) config.getProperties().get("mongoDatabase");
            DB db = mongoClient.getDB(databaseName);
            DBCollection collection = db.getCollection("lgl-publishednodes");
            LOG.debug(
                    "method=retrieveData step=Get the collection from mongo db retrieve data method: {}",
                    collection.count());
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.append("pubstatus", true ).append("mailstatus", false);
            Cursor cursor = collection.find(whereQuery);
            try {
                while (cursor.hasNext()) {
                    paths.add((String) cursor.next().get("path"));
                }
            } finally {
                cursor.close();
            }
        } catch (UnknownHostException e) {

            LOG.error(
                    "method=retrieveData step=Unable to retrieve data due to Unknown Host",
                    e);
        } catch (IOException e) {
            LOG.error("method=updateStatus step=Unable to retrieve data due to IOException",e);
        } finally {
            /* No
            if (mongoClient != null) {
                mongoClient.close();
                LOG.debug("method=retrieveData step=close the mongo connection");
            }
            */

        }
        LOG.debug("method=retrieveData step=end");
        return paths;
    }

    public void updateStatus(ConfigurationAdmin configAdmin) {
        LOG.debug("method=updateStatus step=start");
        try {
            mongoClient = MongoClientSingleton.getInstance();
            Configuration config = configAdmin.getConfiguration(MONGODB_PID);
            String databaseName = (String) config.getProperties().get("mongoDatabase");
            DB db = mongoClient.getDB(databaseName);
            DBCollection collection = db.getCollection("lgl-publishednodes");
            BasicDBObject newDocument = new BasicDBObject().append("$set",
                    new BasicDBObject().append("mailstatus", true));
            BasicDBObject searchQuery = new BasicDBObject().append("mailstatus", false);
            collection.updateMulti(searchQuery, newDocument);
            LOG.debug("The Collection is: {}", collection);
        } catch (IOException e) {
            LOG.error("method=updateStatus step=Unable to upadate status due to IOException",e);
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
                LOG.debug("method=updateStatus step=close the mongo connection");
            }
        }
        LOG.debug("method=updateStatus step=end");
    }
}


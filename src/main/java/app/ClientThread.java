package app;

import app.utils.MongoClientSingleton;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.osgi.service.cm.Configuration;

import java.util.Date;

/**
 * Created by jorgeimperial on 9/28/2020.
 */
public class ClientThread extends Thread {

    MongoClient mongoClient ;
    final String MONGODB_PID = "com.mongodb.tse.examples";
    private Configuration config;


    public ClientThread(MongoClientSingleton singleton, Configuration config) {
        mongoClient = singleton;
        this.config   = config;
    }


    public void run() {

        try {
            String databaseName = (String) config.getProperties().get("mongoDatabase");
            String collectionName = (String) config.getProperties().get("mongoCollection");

            MongoDatabase db = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> coll = db.getCollection(collectionName);
            Date now = new Date();
            String path = now.toString() + this.mongoClient;

            // Document to be inserted here
            Document doc = new Document("path", path)
                    .append("pubstatus", "1").append("pubdate", now.toString())
                    .append("mailstatus", false);

            coll.insertOne(doc);

        }
        catch (MongoException me) {
            System.out.print( "ERROR: " + me.getMessage());
        }


    }
}

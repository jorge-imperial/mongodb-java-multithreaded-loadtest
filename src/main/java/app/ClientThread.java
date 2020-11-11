package app;

import app.utils.MongoClientSingleton;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import app.utils.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by jorgeimperial on 9/28/2020.
 */
public class ClientThread extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(ClientThread.class);
    MongoClientSingleton mongoClient ;
    final String MONGODB_PID = "com.mongodb.tse.examples";
    private final Configuration config;
    private final int iterations;

    public ClientThread(MongoClientSingleton singleton, Configuration config, int inserts) {
        mongoClient = singleton;
        this.config   = config;
        this.iterations = inserts;
    }


    public void run() {

        try {
            String databaseName = (String) config.getProperties().get("mongoDatabase");
            String collectionName = (String) config.getProperties().get("mongoCollection");

            MongoDatabase db = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> coll = db.getCollection(collectionName);

            for (int i=0;i<iterations;++i) {
                Date now = new Date();

                // Document to be inserted here
                Document doc = new Document("path", "meh")
                        .append("pubstatus", "1").append("pubdate", now.toString())
                        .append("mailstatus", false);

                coll.insertOne(doc);
            }

        }
        catch (MongoException me) {
            LOG.error("ERROR: " + me.getMessage());
        }


    }
}

package app.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.osgi.service.cm.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class MongoClientSingleton extends MongoClient {

    private static final String MONGODB_PID = "com.mongodb.tse.examples";
    private static MongoClientSingleton single_instance = null;
    private final Logger LOG = LoggerFactory.getLogger(MongoClientSingleton.class);

    private MongoClientSingleton(MongoClientURI uri) {
        super(uri);
    }

    public static MongoClientSingleton getInstance() throws IOException {
        if (single_instance == null) {
            ConfigurationAdmin configAdmin = new ConfigurationAdmin();
            Configuration config = configAdmin.getConfiguration(MONGODB_PID);

            String connString = (String) config.getProperties().get("mongoDBConnString");
            MongoClientURI uri = new MongoClientURI(connString);
            single_instance = new MongoClientSingleton(uri);
        }
        return single_instance;
    }
}

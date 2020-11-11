package app.deprecated;

import app.utils.ConfigurationAdmin;
import app.utils.MongoClientSingleton;

import java.util.Date;

/**
 * Created by jorgeimperial on 10/2/2020.
 */
public class WorkerThread extends Thread{

    ConfigurationAdmin config;
    WorkerThread(MongoClientSingleton singleton) {

    }
    @Override
    public void run() {
        super.run();

        config = new ConfigurationAdmin();
        MongoDBClient   c = new MongoDBClient();

        Date d = new Date();
        c.storeData( "Path", d, Boolean.TRUE, Boolean.TRUE, config);
        c.retrieveData(config);
        //c.updateStatus(config);

    }
}

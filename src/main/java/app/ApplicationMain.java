/**
 * Created by jorgeimperial on 9/13/2020.
 */
package app;

import app.utils.Configuration;
import app.utils.ConfigurationAdmin;
import app.utils.MongoClientSingleton;
import com.google.devtools.common.options.OptionsParser;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.PropertyConfigurator;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Thread.sleep;

public class ApplicationMain {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);
    final String MONGODB_PID = "com.mongodb.tse.examples";

    public ApplicationMain(String[] args) throws IOException {

        OptionsParser parser = OptionsParser.newOptionsParser(CmdLineOptions.class);
        parser.parseAndExitUponError(args);
        CmdLineOptions options = parser.getOptions(CmdLineOptions.class);

        PropertyConfigurator.configure("log4j.properties");
        ConfigurationAdmin configAdmin = new ConfigurationAdmin();

        Configuration config = configAdmin.getConfiguration(MONGODB_PID);
        LOG.info("Starting process with " + options.startThreads+ " threads, incrementing by "+options.deltaThreads +
                        " until " + options.maxThreads + " for " + options.iterations +
                        " inserts. Waiting " + options.sleepMs+ " ms between batches of threads." );

        // Cuenta los registros
        String databaseName = (String) config.getProperties().get("mongoDatabase");
        String collectionName = (String) config.getProperties().get("mongoCollection");

        MongoDatabase db = MongoClientSingleton.getInstance().getDatabase(databaseName);
        MongoCollection<Document> coll = db.getCollection(collectionName);

        long documentsStart = coll.countDocuments();
        LOG.info("There are " + documentsStart + " documents in " + databaseName + "." + collectionName);


        //for (int i = 0; i < options.iterations; ++i) {

        Thread [] threadGroup = new Thread[options.maxThreads];
        int i = 0;
        for (int thread_count = 0;   thread_count < options.maxThreads; thread_count += options.deltaThreads) {

            for (int j = 0; j < options.deltaThreads; ++j,  ++i) {

                threadGroup[i] = new ClientThread(MongoClientSingleton.getInstance(), config, options.iterations);
                threadGroup[i].run();
            }
            LOG.info("Running " + thread_count + " threads");
            try {
                sleep(options.sleepMs);
            } catch (InterruptedException e) {
                LOG.error("Thread sleep interrupted after starting threads.");
            }
        }

        try {
            Thread.sleep(10000); // 10 second wait
        } catch (InterruptedException e) {
           LOG.error("Thread sleep interrupted before join()!!!");
        }

        LOG.info("Waiting for threads to finish");

        // We should wait for threads to finish.
        List<Thread> allThreads = new ArrayList<Thread>();
        for(Thread thread : threadGroup){
            if(null != thread){
                if(thread.isAlive()){
                    allThreads.add(thread);
                }
            }
        }

        while(!allThreads.isEmpty()){
            Iterator<Thread> ite = allThreads.iterator();
            while(ite.hasNext()){
                Thread thread = ite.next();
                if(!thread.isAlive()){
                    ite.remove();
                }
            }
        }

        //
        long documentsEnd = coll.countDocuments();
        LOG.info("There are now " + documentsEnd + " documents in " + databaseName + "." + collectionName + " started with " + documentsStart);

        // Close client.
        MongoClientSingleton.getInstance().close();
    }

    public static void main(String[] args) throws IOException {
        ApplicationMain app = new ApplicationMain(args);
    }

    private static void printUsage(OptionsParser parser) {
    }
}

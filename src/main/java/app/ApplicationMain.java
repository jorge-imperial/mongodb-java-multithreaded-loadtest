/**
 * Created by jorgeimperial on 9/13/2020.
 */
package app;


import app.utils.CmdLineOptions;
import app.utils.ConfigurationAdmin;
import app.utils.MongoClientSingleton;
import com.google.devtools.common.options.OptionsParser;
import org.apache.log4j.PropertyConfigurator;
import org.osgi.service.cm.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class ApplicationMain {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);
    final String MONGODB_PID = "com.mongodb.tse.examples";

    public ApplicationMain(String[] args) throws IOException {

        OptionsParser parser = OptionsParser.newOptionsParser(CmdLineOptions.class);
        parser.parseAndExitUponError(args);
        CmdLineOptions options = parser.getOptions(CmdLineOptions.class);

        LOG.info("Starting process with %d threads, incrementing by %d until %d for %d iterations. Waiting %d ms between iterations.\n",
                options.startThreads, options.deltaThreads, options.maxThreads,
                options.iterations, options.sleepMs);

        PropertyConfigurator.configure("log4j.properties");
        ConfigurationAdmin configAdmin = new ConfigurationAdmin();
        Configuration config = configAdmin.getConfiguration(MONGODB_PID);

        int threads = options.startThreads;

        for (int i = 0; i < options.iterations; ++i) {

            for (int j = 0; j < threads; ++j) {
                ClientThread t = new ClientThread(MongoClientSingleton.getInstance(), config);
                t.run();
            }
            System.out.println("Running " + threads + " threads");
            try {
                sleep(options.sleepMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (threads < options.maxThreads)
                threads += options.deltaThreads;
        }
    }

    public static void main(String[] args) throws IOException {
        ApplicationMain app = new ApplicationMain(args);
    }

    private static void printUsage(OptionsParser parser) {
    }
}

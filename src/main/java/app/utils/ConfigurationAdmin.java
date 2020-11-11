package app.utils;


import java.io.IOException;


/**
 * Created by jorgeimperial on 10/2/2020.
 */
public class ConfigurationAdmin implements org.osgi.service.cm.ConfigurationAdmin {


    Configuration cfg;

    public ConfigurationAdmin() {
        cfg = new Configuration();
    }
    public ConfigurationAdmin(String s) {
        cfg = new Configuration();
    }

    @Override
    public Configuration createFactoryConfiguration(String s) throws IOException {
        return null;
    }

        @Override
        public Configuration createFactoryConfiguration(String s, String s1) throws IOException {
        return null;
    }

        @Override
        public Configuration getConfiguration(String s, String s1) throws IOException {
        return null;
    }

        @Override
        public Configuration getConfiguration(String s) throws IOException {
        return cfg;
    }

        @Override
        public Configuration[] listConfigurations(String s) throws IOException {
        return new Configuration[0];
    }

}

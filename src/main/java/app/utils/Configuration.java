package app.utils;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;



/**
 * Created by jorgeimperial on 10/2/20.
 */
public class Configuration implements org.osgi.service.cm.Configuration {

    Dictionary <String,Object> dict;

    public Configuration() {
        dict = new Hashtable<String, Object>();
        //  Cambiar connection string
        String connStr = "mongodb+srv://root:mr4Ydl0cKhgeRgDh@belisarius.om7f7.mongodb.net/test?retryWrites=true&w=majority";
        dict.put("mongoDBConnString", connStr);
        dict.put("mongoDatabase", "test");
        dict.put("mongoCollection", "homologacion");

    }

    @Override
    public String getPid() {
        return null;
    }

    @Override
    public Dictionary<String, Object> getProperties() {
        return dict;
    }

    @Override
    public void update(Dictionary<String, ?> dictionary) throws IOException {

    }

    @Override
    public void delete() throws IOException {

    }

    @Override
    public String getFactoryPid() {
        return null;
    }

    @Override
    public void update() throws IOException {

    }

    @Override
    public void setBundleLocation(String s) {

    }

    @Override
    public String getBundleLocation() {
        return null;
    }

    @Override
    public long getChangeCount() {
        return 0;
    }
}



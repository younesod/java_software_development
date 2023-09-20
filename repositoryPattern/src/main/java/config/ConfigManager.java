package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private ConfigManager(){
        prop= new Properties();
        url = getClass().getClassLoader().getResource(FILE).getFile();
    }
    private static final String FILE= "./config/config.properties";
    private final Properties prop;

    private final String url;

    public void load() throws IOException{
        try(InputStream input= new FileInputStream(url)){
            prop.load(input);
        }catch(IOException e){
            throw new IOException("Chargement de configuration impossible"+e.getMessage());
        }
    }

    public String getProperties(String name) {
        return prop.getProperty(name);
    }

    public static ConfigManager getInstance(){
        return ConfigManagerHolder.INSTANCE;
    }

    private static class ConfigManagerHolder{
        private static final ConfigManager INSTANCE= new ConfigManager();
    }
}

package org.rmj.gcardappfx.views;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Properties;
 
public class Config{
    String result = "";
    public String getPropValues() {
        Properties prop = new Properties();
            try {
                InputStream propFileName = new FileInputStream("D:/GGC_Java_Systems/config/gcard.properties");
                prop.load(propFileName);
                result = prop.getProperty("app.main.office");
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            return result;
    }
}

package org.rmj.gcardappfx.applications;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javafx.application.Application;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.gcardappfx.views.GCardAppFx;


public class Login {
    
    private static GRider poGRider = null;
    
    public void setGRider(GRider foGRider){poGRider = foGRider;}
    
    public static void main(String [] args){       
        String path;
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            path = "D:/GGC_Java_Systems";
        }
        else{
            path = "/srv/mac/GGC_Java_Systems";
        }
        System.setProperty("sys.default.path.config", path);
        
        if (!loadProperties()){
            System.err.println("Unable to load config.");
            System.exit(1);
        } else System.out.println("Config file loaded successfully.");
        
        String lsProdctID;
        String lsUserIDxx;
        
        if (System.getProperty("app.debug.mode").equals("1")){
            lsProdctID = System.getProperty("app.product.id");
            lsUserIDxx = System.getProperty("user.id");
        } else {
            lsProdctID = args[0];
            lsUserIDxx = args[1];
        }
        
        GRider poGRider = new GRider(lsProdctID);
        
        if (!poGRider.loadUser(lsProdctID, lsUserIDxx)){
            System.out.println(poGRider.getMessage() + poGRider.getErrMsg());
            System.exit(1);
        }
        
        String lsVal = CommonUtils.getConfiguration(poGRider, "QRActVldtn");
        if (lsVal != null) System.setProperty("app.gcard.digital.1", lsVal);      
        
        GCardAppFx Login = new GCardAppFx();
        Login.setGRider(poGRider);
        
        Application.launch(Login.getClass());
    }    
    
    private static boolean loadProperties(){
        try {
            Properties po_props = new Properties();
            po_props.load(new FileInputStream("D:\\GGC_Java_Systems\\config\\gcard.properties"));
            
            //User and Application Info 
            System.setProperty("user.id", po_props.getProperty("user.id"));
            System.setProperty("app.product.id", po_props.getProperty("app.product.id"));
            System.setProperty("app.main.office", po_props.getProperty("app.main.office"));
            System.setProperty("app.debug.mode", po_props.getProperty("app.debug.mode"));
            
            //G-Card Information
            System.setProperty("app.card.connected", "");
            System.setProperty("app.gcard.no", "");
            System.setProperty("app.gcard.holder", "");
            System.setProperty("app.card.no", "");
            System.setProperty("app.device.type", "");
            System.setProperty("app.device.data", "");
            
            //validation
            System.setProperty("app.gcard.digital.1", po_props.getProperty("app.gcard.digital.1"));
            
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

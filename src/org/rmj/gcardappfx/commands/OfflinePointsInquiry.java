package org.rmj.gcardappfx.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import org.json.simple.JSONObject;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.FileUtil;
import org.rmj.gcard.trans.agentFX.XMGCOffPoints;
import org.rmj.gcard.trans.agentFX.XMGCard;

public class OfflinePointsInquiry {
    //0-productid;1-userid;2-sGCardNox;3-ORNo;4-date
    public static void main (String [] args){
        final String RESULT_DIR = "D:/GGC_Java_Systems/temp/res.TMP";
        
        JSONObject loJSON = new JSONObject();
        
        if (args.length != 5){ 
            loJSON.put("result", "error");
            loJSON.put("message", "Invalid parameters detected.");
            FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());
            
            System.exit(1); 
        }
        
        if (!loadProperties()){
            loJSON.put("result", "error");
            loJSON.put("message", "Unable to load config.");
            FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());
            
            System.exit(1); 
        } else System.out.println("Config file loaded successfully.");
        
        String lsProdctID = args[0];
        String lsUserIDxx = args[1];
        
        GRider poGRider = new GRider(lsProdctID);
        
        if (!poGRider.loadUser(lsProdctID, lsUserIDxx)){            
            loJSON.put("result", "error");
            loJSON.put("message", poGRider.getErrMsg());
            FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());
            
            System.exit(1); 
        }
            
        XMGCOffPoints instance = new XMGCOffPoints(poGRider, poGRider.getBranchCode(), false);
        
        if (instance.newTransaction()){                  
            if (CommonUtils.isDate(args[4], SQLUtil.FORMAT_SHORT_DATE))
                instance.setMaster("dTransact", SQLUtil.toDate(args[4], SQLUtil.FORMAT_SHORT_DATE));
            else {
                loJSON.put("result", "error");
                loJSON.put("message", "Invalid date parameter detected.");
                FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());

                System.exit(1); 
            }
            
            if (instance.searchField("sGCardNox", args[2])){ //pass the sGCardNox
                XMGCard loCard = instance.getGCard();
                
                if (loCard != null){
                    if (instance.searchField("sSourceCd", "M02910000004")){ //monthly payment
                        instance.setMaster("sSourceNo",  args[3]); //pass OR number 
           
                        if (instance.getMaster("nTranAmtx").equals(0.0)){
                            loJSON.put("nTranAmtx", 0.00);
                            loJSON.put("nPointsxx", 0.00);
                        } else{
                            loJSON.put("nTranAmtx", (Double) instance.getMaster("nTranAmtx"));
                            loJSON.put("nPointsxx", (Double) instance.getMaster("nPointsxx"));
                        }
                        
                        loJSON.put("sGCardNox", (String) instance.getMaster("sGCardNox"));
                        loJSON.put("sSourceNo", (String) instance.getMaster("sSourceNo"));
                        loJSON.put("sSourceCd", (String) instance.getMaster("sSourceCd"));
                        loJSON.put("dTransact", SQLUtil.dateFormat((Date) instance.getMaster("dTransact"), SQLUtil.FORMAT_SHORT_DATE));
                        
                        loJSON.put("result", "success");
                        
                        FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());
                        System.exit(0);
                    } else {
                        loJSON.put("result", "error");
                        loJSON.put("message", instance.getMessage());
                    }
                } else {
                    loJSON.put("result", "error");
                    loJSON.put("message", "Unable to load card.");
                }
            } else {
                loJSON.put("result", "error");
                loJSON.put("message", instance.getMessage());
            }
        } else {
            loJSON.put("result", "error");
            loJSON.put("message", instance.getMessage());
        }
        
        FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());
        System.exit(1); 
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

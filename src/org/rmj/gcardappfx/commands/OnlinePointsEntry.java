package org.rmj.gcardappfx.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.FileUtil;
import org.rmj.gcard.trans.agentFX.XMGCOnPoints;
import org.rmj.gcard.trans.agentFX.XMGCard;

public class OnlinePointsEntry {
    public static void main (String [] args){
        final String RESULT_DIR = "D:/GGC_Java_Systems/temp/res.TMP";
        
        JSONObject loJSON = new JSONObject();
        
        if (args.length != 6){ 
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
        
        String lsVal = CommonUtils.getConfiguration(poGRider, "QRActVldtn");
        if (lsVal != null) System.setProperty("app.gcard.digital.1", lsVal);    
        
        OnlineEntry instance = new OnlineEntry(poGRider, args[2], args[3], args[4], args[5]);
        if (instance.NewTransaction()){
            if (instance.SaveTransaction()){
                loJSON.put("result", "success");
                loJSON.put("message", "Transaction ONLINE POINTS was encoded successfully.");
                FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());
                
                System.exit(0); 
            } else {
                loJSON.put("result", "error");
                loJSON.put("message", instance.getMessage());
                FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());

                System.exit(1); 
            }
        } else{
            loJSON.put("result", "error");
            loJSON.put("message", instance.getMessage());
            FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());

            System.exit(1); 
        }
    }    
    
    public static class OnlineEntry{
        final String RESULT_DIR = "D:/GGC_Java_Systems/temp/res.TMP";
        
        GRider poGRider;
        XMGCOnPoints poTrans;
        XMGCard poCard;
        
        String psGCardNox;
        String psDigitalx;
        String psSourceNo;
        String psSourceCd;
        
        String psMessage;
        
        public String getMessage(){return psMessage;}

        public OnlineEntry(GRider foGRider, String fsGCardNox, String fsDigitalx, String fsReferNox, String fsSourceCd){
            JSONObject loJSON = new JSONObject();
            
            if (foGRider == null){                
                loJSON.put("result", "error");
                loJSON.put("message", "Application driver is not set.");
                FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());
                System.exit(1); 
            }
            
            if (fsGCardNox.isEmpty()){
                loJSON.put("result", "error");
                loJSON.put("message", "G-Card number must not be empty.");
                FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());
                System.exit(1); 
            }
            
            if (fsDigitalx.isEmpty()){
                loJSON.put("result", "error");
                loJSON.put("message", "G-Card digital must not be empty.");
                FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());
                System.exit(1); 
            }
            
            if (fsReferNox.isEmpty()){
                System.err.println("Reference number must not be empty.");
                System.exit(1);
            }
            
            if (fsSourceCd.isEmpty()){
                loJSON.put("result", "error");
                loJSON.put("message", "Transaction code must not be empty.");
                FileUtil.fileWrite(RESULT_DIR, (String) loJSON.toJSONString());
                System.exit(1); 
            }
            
            poGRider = foGRider;
            poTrans = new XMGCOnPoints(poGRider, poGRider.getBranchCode(), false);
            
            psGCardNox = fsGCardNox;
            psDigitalx = fsDigitalx;
            psSourceNo = fsReferNox;
            psSourceCd = fsSourceCd;
        }
        
        public boolean NewTransaction(){
            if (poTrans.newTransaction()){
                poTrans.setMaster("dTransact", poGRider.getServerDate());
                
                System.setProperty("app.device.type", psDigitalx);
                System.setProperty("app.card.no", getCardNo());
            
                if (poTrans.connectCard()){
                    poCard = poTrans.getGCard();
                      
                    if (poCard != null){
                        if (poTrans.searchField("sSourceCd", psSourceCd)){ //monthly payment
                            poTrans.setMaster("sSourceNo",  psSourceNo); //pass OR number 
                            return true;
                        } else psMessage = poTrans.getMessage();
                    } else psMessage = "Card information is empty.";
                } else psMessage = poTrans.getMessage();
            } else psMessage = poTrans.getMessage();
            
            return false;
        }
        
        public boolean SaveTransaction(){
            if (!poTrans.saveUpdate()){
                psMessage = poTrans.getMessage();
                return false;
            }
            
            return true;
        }
        
        private String getCardNo(){
            String lsSQL = "SELECT sCardNmbr FROM G_Card_Master WHERE sGCardNox = " + SQLUtil.toSQL(psGCardNox);

            ResultSet loRS = poGRider.executeQuery(lsSQL);

            try {
                if (loRS.next()) return loRS.getString("sCardNmbr");
            } catch (SQLException ex) {
                Logger.getLogger(OnlinePointsEntry.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "";
        }    
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
        /*
        public boolean SaveTransaction(){
            //Only regular GCard and/or Member card earn points...
            if(((String) poTrans.getGCard().getMaster("cMainGrpx")).contentEquals("1")){
                psMessage = "Mother GCard cannot earn points!";
                return false;
            }
            
            String validator = poGRider.getValidator("GCardPoint", poTrans.getData().getSourceCd());
            
            if(validator == null){
                psMessage = "Invalid validator detected... Verification of transaction failed!";
                return false;
            }

            long point = 0;
            GCardPoint valid = (GCardPoint) MiscUtil.createInstance(validator);
            valid.setGRider(poGRider);
            valid.setData(poTrans.getData());
            valid.checkSource();
            if(valid.getPoints() <= 0){
                if(valid.getMessage().length() > 0){
                    psMessage = valid.getMessage() + "\nVerification of transaction failed!";
                } else
                    psMessage = "Verification of transaction failed!";      
                
                return false;
            }

            point = valid.getPoints();
            if(isInGCOnline()){
                point = 0;
            }
            else if(isInGCOffline()){
                point = 0;
            }
            
            if((double) point != poTrans.getData().getPoints()){
                psMessage = "Points validation failed... Verification of transaction failed!";
                return false;
            }
            
            
            String lcCardType = (String) poCard.getMaster("cDigitalx");
            String lsCardNmbr = "";
            
            GCardDevice loGCDevice = null;
                        
            switch(lcCardType){
                case "0":
                    loGCDevice = GCardDeviceFactory.make(GCardDeviceFactory.DeviceType.SMARTCARD);
                    break;
                case "1":
                    loGCDevice = GCardDeviceFactory.make(GCardDeviceFactory.DeviceType.QRCODE);
                    loGCDevice.setGRider(poGRider);
                    break;
                case "2":
                    loGCDevice = GCardDeviceFactory.make(GCardDeviceFactory.DeviceType.NONE);
                    loGCDevice.setGRider(poGRider);
                    break;
            }
            
            if(null == loGCDevice.UIDeviceType()){
                poTrans.getData().setOTPassword("");
            } else switch (loGCDevice.UIDeviceType()) {
                case QRCODE:
                    poTrans.getData().setOTPassword(String.format("%06d", MiscUtil.getRandom(999999)));
                    break;
                case NONE:
                    if (!CommonUtils.isURLOnline(CommonUtils.getConfiguration(poGRider, "WebSvr"))){
                        psMessage = "Main office server cannot be reached and device use is a NON-CHIP CARD. Please encode this entry as OFFLINE POINTS ENTRY.";
                        return false;
                    }
                    
                    poTrans.getData().setOTPassword(String.format("%06d", MiscUtil.getRandom(999999)));
                    break;
                default:
                    poTrans.getData().setOTPassword("");
                    break;
            }

            if (lcCardType.equals("0") || lcCardType.equals("1")){
                 loGCDevice.isLoadInfo(false);

                if(!loGCDevice.read()){
                    psMessage = "readCard: Can't connect card. " + loGCDevice.getMessage();
                    return false; 
                }

                lsCardNmbr = (String) loGCDevice.getCardInfo("sCardNmbr");
                
                loGCDevice.release();
            }else {                         
                JSONObject poJson = new JSONObject();
                poJson.put("SOURCE", "ONLINE");
                poJson.put("sOTPasswd", (String) poTrans.getData().getValue("sOTPasswd"));

                JSONArray poArr = new JSONArray();
                JSONObject poDtl = new JSONObject();
                poDtl.put("sTransNox", poTrans.getData().getTransNo());
                poDtl.put("dTransact", poTrans.getData().getTransactDate());
                poDtl.put("sSourceNo", poTrans.getData().getSourceNo());
                poDtl.put("sSourcexx", poTrans.getData().getSourceCd());
                poDtl.put("sSourceCD", (String) poTrans.getSource().getMaster("sDescript"));
                poDtl.put("nTranAmtx", poTrans.getData().getTranAmount());
                poDtl.put("nPointsxx", poTrans.getData().getPoints());

                poArr.add(poDtl);
                poJson.put("DETAIL", poArr);

                try {
                    ValidateOTP loOTP = new ValidateOTP();
                    loOTP.setGRider(poGRider);
                    loOTP.setTransaction(poJson);
                    
                    javafx.application.Application.launch(loOTP.getClass());
                    
                    if (!loOTP.isOkay()){                                
                        psMessage = "OTP may be incorrect or entry was cancelled.";
                        return false;
                    }
                } catch (Exception ex) {
                    psMessage = ex.getMessage();
                    return false;
                }
            }
            
            return true;
            
        }
        
        private boolean isInGCOnline(){
        boolean bGC = false;

        Connection loCon = poGRider.getConnection();
        ResultSet loRS = null;

        String lsSQL = "SELECT IFNULL(SUM(nPointsxx), 0) nPointsxx"  + 
                        " FROM G_Card_Detail" + 
                        " WHERE sTransNox LIKE " + SQLUtil.toSQL(poTrans.getData().getTransNo().substring(0, 4) + "%") +
                            " AND dTransact = " + SQLUtil.toSQL(SQLUtil.dateFormat(poTrans.getData().getTransactDate(), "yyyy-MM-dd")) + 
                            " AND sSourceNo = " + SQLUtil.toSQL(poTrans.getData().getSourceNo()) + 
                            " AND sSourceCD = " + SQLUtil.toSQL(poTrans.getData().getSourceCd());
        try {
            System.out.println("Before Execute");

            System.out.println(lsSQL);
            loRS = loCon.createStatement().executeQuery(lsSQL);

            if(loRS.next()){
                if(loRS.getDouble("nPointsxx") > 0 )
                    bGC = true;
            }
        } catch (SQLException ex) {
            psMessage = ex.getMessage();
            bGC = true;
        }
        finally{
            MiscUtil.close(loRS);
        }

        return bGC;
    }

    private boolean isInGCOffline(){
        boolean bGC = false;

        Connection loCon = poGRider.getConnection();
        ResultSet loRS = null;

        String lsSQL = "SELECT IFNULL(SUM(nPointsxx), 0) nPointsxx"  + 
                        " FROM G_Card_Detail_Offline" + 
                        " WHERE sTransNox LIKE " + SQLUtil.toSQL(poTrans.getData().getTransNo().substring(0, 4) + "%") +
                            " AND dTransact = " + SQLUtil.toSQL(SQLUtil.dateFormat(poTrans.getData().getTransactDate(), "yyyy-MM-dd")) + 
                            " AND sSourceNo = " + SQLUtil.toSQL(poTrans.getData().getSourceNo()) + 
                            " AND sSourceCD = " + SQLUtil.toSQL(poTrans.getData().getSourceCd()) +
                            " AND cTranStat IN ('0', '1', '2')";
        try {
            System.out.println("Before Execute");

            System.out.println(lsSQL);
            loRS = loCon.createStatement().executeQuery(lsSQL);

            if(loRS.next()){
               if(loRS.getDouble("nPointsxx") > 0 )
                  bGC = true;
            }
        } catch (SQLException ex) {
            psMessage = ex.getMessage();           
            bGC = true;
        }
        finally{
            MiscUtil.close(loRS);
        }

        return bGC;
    }*/
}

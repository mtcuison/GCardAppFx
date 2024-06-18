package org.rmj.gcardappfx.commands;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.MySQLAESCrypt;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.StringHelper;
import org.rmj.webcamfx.ui.ScanPin;
import org.rmj.webcamfx.ui.Webcam;

public class GCardOTP {
    public static void main(String [] args){      
        System.exit(getOTP(args[0]) ? 0 : 1);
    }
    
    public static boolean getOTP(String fsCardNmbr){
        String lsOTP = StringHelper.prepad(String.valueOf(MiscUtil.getRandom(1, 999999)), 6, '0') ;
        fsCardNmbr = "OTP»" + fsCardNmbr + "»" + lsOTP + "»" + SQLUtil.dateFormat(Calendar.getInstance().getTime(), SQLUtil.FORMAT_TIMESTAMP);
        fsCardNmbr = MySQLAESCrypt.Encrypt(fsCardNmbr, "20190625");
        
        String qrpin;
        try{
            ScanPin loPin = new ScanPin();
            loPin.setData(fsCardNmbr);

            javafx.application.Application.launch(loPin.getClass());

            qrpin = loPin.getData();
        }catch (Exception ex){
            qrpin = Webcam.getPIN(fsCardNmbr);
        }    

        return qrpin.equalsIgnoreCase(lsOTP);
    }
}

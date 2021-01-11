/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rmj.gcardappfx.views;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.F3;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.CustomTextField;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.agentfx.ui.showFXDialog;
import org.rmj.appdriver.constants.GCDeviceType;
import org.rmj.gcard.device.ui.GCardDeviceFactory;
import org.rmj.gcard.trans.agentFX.XMGCConnect;

/**
 * FXML Controller class
 *
 * @author Mac
 */
public class CardConnectController implements Initializable {  
    @FXML
    private Label lblTitle;
    @FXML
    private CustomTextField txtSeeks00;
    @FXML
    private CustomTextField txtSeeks01;
    @FXML
    private Label lblUnposted;
    @FXML
    private ComboBox cmbDeviceType;

    private GCardAppFxController mainController ;
    private GRider poGRider;
    private XMGCConnect poConnect;
    
    private ObservableList<String> sDeviceType = FXCollections.observableArrayList("Smartcard", "Digital(App QR Code)", "Non-Chip Card");
    private final String pxeModuleName = this.getClass().getSimpleName();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        poConnect = new XMGCConnect(poGRider);
        poConnect.setDeviceType(GCardDeviceFactory.DeviceType.NONE);
        
        mainController.rootPane.setOnKeyReleased(this::handleKeyCode);
        
        mainController.cmdButton00.setOnAction(this::cmdButton_Click);
        mainController.cmdButton01.setOnAction(this::cmdButton_Click);
        mainController.cmdButton02.setOnAction(this::cmdButton_Click);
        mainController.cmdButton03.setOnAction(this::cmdButton_Click);
        mainController.cmdButton04.setOnAction(this::cmdButton_Click);
        mainController.cmdButton05.setOnAction(this::cmdButton_Click);
        mainController.cmdButton06.setOnAction(this::cmdButton_Click);
        mainController.cmdButton07.setOnAction(this::cmdButton_Click);
        mainController.cmdButton08.setOnAction(this::cmdButton_Click);
        mainController.cmdButton09.setOnAction(this::cmdButton_Click);
        mainController.cmdButton10.setOnAction(this::cmdButton_Click);
        mainController.cmdButton11.setOnAction(this::cmdButton_Click);
        
        
        txtSeeks00.setOnKeyPressed(this::txtField_KeyPressed);
        txtSeeks01.setOnKeyPressed(this::txtField_KeyPressed);
        
        loadGui();
    }    
    
    public void setMainController(GCardAppFxController mainController) {
        this.mainController = mainController ;
    }

    public void setGRider(GRider foGRider){this.poGRider = foGRider;}

    @FXML
    private void cmbDeviceType_Change(ActionEvent event) {
        switch (cmbDeviceType.getSelectionModel().getSelectedIndex()){
            case 2:
                poConnect.setDeviceType(GCardDeviceFactory.DeviceType.NONE);
                txtSeeks00.setDisable(false);
                txtSeeks01.setDisable(false);
                txtSeeks00.setText(System.getProperty("app.card.no"));
                txtSeeks01.setText(System.getProperty("app.gcard.holder"));
                txtSeeks00.requestFocus();
                break;
            case 1:
                txtSeeks00.setDisable(false);
                txtSeeks01.setDisable(false);
                txtSeeks00.setText(System.getProperty("app.card.no"));
                txtSeeks01.setText(System.getProperty("app.gcard.holder"));
                poConnect.setDeviceType(GCardDeviceFactory.DeviceType.QRCODE);
                break;
            case 0:
                txtSeeks00.setDisable(true);
                txtSeeks01.setDisable(true);
                txtSeeks00.setText(System.getProperty("app.card.no"));
                txtSeeks01.setText(System.getProperty("app.gcard.holder"));
                poConnect.setDeviceType(GCardDeviceFactory.DeviceType.SMARTCARD);
                break;
            case 3:
                txtSeeks00.setDisable(true);
                txtSeeks01.setDisable(true);
                txtSeeks00.setText(System.getProperty("app.card.no"));
                txtSeeks01.setText(System.getProperty("app.gcard.holder"));
                poConnect.setDeviceType(GCardDeviceFactory.DeviceType.QRBARCODE);
        }
    }
    
    public void txtField_KeyPressed(KeyEvent event) {
        TextField txtField = (TextField)event.getSource();        
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        String lsField = "";
        
        if (event.getCode() == ENTER || event.getCode() == F3) {
            switch (lnIndex){
                case 0:
                    lsField = "sCardNmbr";
                    
                    if (lsValue.equals("")){
                        ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                        mainController.setFocus(txtField);
                        return;
                    }
                    
                    if (poConnect.SearchMaster(lsField, lsValue)){
                        txtSeeks00.setText(System.getProperty("app.card.no"));
                        txtSeeks01.setText(System.getProperty("app.gcard.holder"));
                        
                        cmbDeviceType.getSelectionModel().select(Integer.parseInt(System.getProperty("app.device.type")));
                        txtSeeks00.setDisable(System.getProperty("app.device.type").equals("0"));
                        txtSeeks01.setDisable(System.getProperty("app.device.type").equals("0"));
                    } else {
                        txtSeeks00.setText("");
                        txtSeeks01.setText("");
                    }
                    break;
                case 1:
                    lsField = "sClientNm";
                    
                    if (lsValue.equals("")){
                        ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                        mainController.setFocus(txtField);
                        return;
                    }
                    
                    if (poConnect.SearchMaster(lsField, lsValue)){
                        txtSeeks00.setText(System.getProperty("app.card.no"));
                        txtSeeks01.setText(System.getProperty("app.gcard.holder"));
                        
                        cmbDeviceType.getSelectionModel().select(Integer.parseInt(System.getProperty("app.device.type")));
                        txtSeeks00.setDisable(System.getProperty("app.device.type").equals("0"));
                        txtSeeks01.setDisable(System.getProperty("app.device.type").equals("0"));
                    } else {
                        txtSeeks00.setText("");
                        txtSeeks01.setText("");
                    }
                    break;
                default:
                    return;
            }
        }       

        if (event.getCode() == DOWN || event.getCode() == ENTER){
             CommonUtils.SetNextFocus(txtField);                  
        }
        if (event.getCode() == UP){
             CommonUtils.SetPreviousFocus(txtField);                 
        }
    }
    
    private void cmdButton_Click(ActionEvent event) {
        String lsButton = ((Button)event.getSource()).getId().toLowerCase();
        String lsName = ((Button)event.getSource()).getText();
        switch (lsButton){
            case "cmdbutton00":
                mainController.dataPane.getChildren().clear();
                mainController.buttonClick();
                mainController.loadMainGui();
                mainController.mainKeyCode();
                break;
            case "cmdbutton01":
                if (lsName.equalsIgnoreCase("connect")){                   
                    if (poConnect.Connect()){
                        mainController.setSiteMap(18);
                        txtSeeks00.setText(System.getProperty("app.card.no"));
                        txtSeeks01.setText(System.getProperty("app.gcard.holder"));
                        System.err.println("ClientID: " + System.getProperty("app.client.id"));
                        
                        ShowMessageFX.Information(null, "Success", poConnect.getMessage());
                            
                        //loadGui();
                        //go back to the home screen
                        mainController.dataPane.getChildren().clear();
                        mainController.buttonClick();
                        mainController.loadMainGui();
                        mainController.mainKeyCode();
                    } else{
                        System.setProperty("app.card.connected", "");
                        System.setProperty("app.gcard.no", "");
                        System.setProperty("app.gcard.holder", "");
                        System.setProperty("app.card.no", "");
                        System.setProperty("app.device.type", "");
                        System.setProperty("app.device.data", "");
                        System.setProperty("app.client.id", "");
                        System.setProperty("app.gcard.online", "");
                        
                        ShowMessageFX.Warning(poConnect.getMessage(), "Failed", "Unable to connect.");

                        loadGui();
                    }
                } else {
                    if (poConnect.Disconnect()){
                        ShowMessageFX.Information(null, "Success", poConnect.getMessage());
                        loadGui();
                    } else{
                        ShowMessageFX.Warning(null, "Failed", poConnect.getMessage());
                    }
                }
                break;
            case "cmdbutton02":
                if (poConnect.Connect()){
                    ShowMessageFX.Information(null, "Success", "Connection was refreshed.");

                    //loadGui();
                    //go back to the home screen
                    mainController.dataPane.getChildren().clear();
                    mainController.buttonClick();
                    mainController.loadMainGui();
                    mainController.mainKeyCode();
                } else{
                    ShowMessageFX.Warning(null, "Failed", "Unable to refresh connection.");

                    if (poConnect.getDeviceType() == GCardDeviceFactory.DeviceType.NONE){
                        txtSeeks00.requestFocus();
                    }
                }
                break;
            case "cmdbutton03":
                String lsClientID = System.getProperty("app.client.id");
                String lsMobileNo = System.getProperty("app.gcard.mobile");
                
                JSONObject loJSON = showFXDialog.updateMobileNo(poGRider, lsClientID, lsMobileNo);
                String lsResult = (String) loJSON.get("result");
                
                if (lsResult.equalsIgnoreCase("success")){
                    if (poConnect.Connect()) loadGui();
                    
                    ShowMessageFX.Information((String) loJSON.get("message"), "Success", "Update Success");
                } else {                    
                    try {
                        loJSON = (JSONObject) new JSONParser().parse(loJSON.get("error").toString());
                        
                        ShowMessageFX.Warning((String) loJSON.get("message"), "Failed - " + (String) loJSON.get("code"), "Update Failed");
                    } catch (ParseException ex) {
                        Logger.getLogger(CardConnectController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "cmdbutton04":
            case "cmdbutton05":
            case "cmdbutton06":
            case "cmdbutton07":
            case "cmdbutton08":
            case "cmdbutton10":
            case "cmdbutton11":
                break;
            default:
                ShowMessageFX.Warning(null, pxeModuleName, "Button with name " + lsButton + " not registered.");
        }
    }
    
    public void handleKeyCode(KeyEvent event) {
        switch(event.getCode()){
            case F1:
            case F2:
            case F3:
            case F4:
            case F5:
            case F6:
            case F7:
            case F8:
            case F9:
            case F10:
            case F11:
            case F12:
            case ESCAPE:
                break; 
        }
    }
    
    private void loadGui(){
        cmbDeviceType.setItems(sDeviceType);

        mainController.cmdButton00.setText("HOME");
        
        if (System.getProperty("app.card.connected").equals("1")){
            mainController.cmdButton01.setText("DISCONNECT");
            mainController.cmdButton02.setDisable(false);
            mainController.cmdButton03.setDisable(!CommonUtils.getConfiguration(poGRider, "BrCPNoUpdt").equals("1"));
            txtSeeks00.setDisable(true);
            txtSeeks01.setDisable(true);
            txtSeeks00.setText(System.getProperty("app.card.no"));
            txtSeeks01.setText(System.getProperty("app.gcard.holder"));
            cmbDeviceType.setDisable(true);
            
            switch (System.getProperty("app.device.type")){
                case GCDeviceType.QRCODE:
                    cmbDeviceType.getSelectionModel().select(1);
                    poConnect.setDeviceType(GCardDeviceFactory.DeviceType.QRCODE);
                    break;
                case GCDeviceType.SMARTCARD:
                    cmbDeviceType.getSelectionModel().select(0);
                    poConnect.setDeviceType(GCardDeviceFactory.DeviceType.SMARTCARD);
                    break;
                default:
                    poConnect.setDeviceType(GCardDeviceFactory.DeviceType.NONE);
                    cmbDeviceType.getSelectionModel().select(2);
            }
        } else{
            mainController.cmdButton01.setText("CONNECT");
            mainController.cmdButton02.setDisable(true);
            mainController.cmdButton03.setDisable(true);
            txtSeeks00.setText("");
            txtSeeks01.setText("");
            txtSeeks00.setDisable(true);
            txtSeeks01.setDisable(true);
            cmbDeviceType.setDisable(false);
            
            poConnect.setDeviceType(GCardDeviceFactory.DeviceType.SMARTCARD);
            cmbDeviceType.getSelectionModel().select(0);
        }
            
        mainController.cmdButton02.setText("REFRESH");
        mainController.cmdButton03.setText(CommonUtils.getConfiguration(poGRider, "BrCPNoUpdt").equals("1") ? "UPDT MOBILE" : "");
        mainController.cmdButton04.setText("");
        mainController.cmdButton05.setText("");
        mainController.cmdButton06.setText("");
        mainController.cmdButton07.setText("");
        mainController.cmdButton08.setText("");
        mainController.cmdButton09.setText("");
        mainController.cmdButton10.setText("");
        mainController.cmdButton11.setText("");
        
        mainController.tooltip00.setText("F1");
        mainController.tooltip01.setText("F2");
        mainController.tooltip02.setText("F3");
        mainController.tooltip03.setText("F4");
        mainController.tooltip04.setText("x");
        mainController.tooltip05.setText("x");
        mainController.tooltip06.setText("x");
        mainController.tooltip07.setText("x");
        mainController.tooltip08.setText("x");
        mainController.tooltip09.setText("x");
        mainController.tooltip10.setText("x");
        mainController.tooltip11.setText("x"); 

        mainController.cmdButton00.setDisable(false);
        mainController.cmdButton01.setDisable(false);
        mainController.cmdButton04.setDisable(true);
        mainController.cmdButton05.setDisable(true);
        mainController.cmdButton06.setDisable(true);
        mainController.cmdButton07.setDisable(true);
        mainController.cmdButton08.setDisable(true);
        mainController.cmdButton09.setDisable(true);
        mainController.cmdButton10.setDisable(true);
        mainController.cmdButton11.setDisable(true);
        
        mainController.setSiteMap(18);
    }
}

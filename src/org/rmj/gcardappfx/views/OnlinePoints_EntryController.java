package org.rmj.gcardappfx.views;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.textfield.CustomTextField;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.agentfx.ui.showFXDialog;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.appdriver.constants.GCDeviceType;
import org.rmj.client.agent.XMClient;
import org.rmj.gcard.device.ui.GCardDevice;
import org.rmj.gcard.trans.agentFX.XMGCOnPoints;
import org.rmj.gcard.trans.agentFX.XMGCard;

public class OnlinePoints_EntryController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private GridPane NodePane00;
    @FXML
    private TextField txtField00;
    @FXML
    private TextField txtField01;
    @FXML
    private TextField txtField02;
    @FXML
    private TextField txtField03;
    @FXML
    private TextField txtField04;
    @FXML
    private TextField txtField07;
    @FXML
    private RadioButton rbtn00;
    @FXML
    private RadioButton rbtn01;
    @FXML
    private TextField txtField08;
    @FXML
    private CheckBox chk00;
    @FXML
    private TextField txtField09;
    @FXML
    private TextField txtField10;
    @FXML
    private TextField txtField05;
    @FXML
    private Label lblTranStatus;
    @FXML
    private Label lblServerStat;
    @FXML
    private CustomTextField txtField06;
    @FXML
    private GridPane NodePane001;
    @FXML
    private CustomTextField txtSeeks00;
    @FXML
    private CustomTextField txtSeeks01;
    @FXML
    private ComboBox cmbCardClass;
    
    private GCardAppFxController mainController ;
    public static final Image search = new Image("org/rmj/gcardappfx/images/search.png");
    private final String pxeModuleName = "Online Points Entry";
    public final static String pxeOnlinePoints_Register = "OnlinePoints_Register.fxml";
    public final static String pxeCardApplicationB = "CardApplicationB.fxml";
    public final static String pxeOfflinePoints_EntryB = "OfflinePoints_EntryB.fxml";
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    private static GRider poGRider;
    private XMGCOnPoints poTrans;
    private final String pxeDateFormat = "yyyy-MM-dd";
    private String pxeCurrentDate;
    private String psBranchCd = "";
    private int pnIndex = -1;
    private int pnEditMode = -1;
    private boolean pbLoaded = false;
    
    private ObservableList<String> sDeviceType = FXCollections.observableArrayList("Smartcard", "App QR Code", "Non-Chip Card");  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(poGRider == null){
            ShowMessageFX.Warning("GhostRider Application not set..", pxeModuleName, "Please inform MIS department.");
            System.exit(0);
        }
        pxeCurrentDate = poGRider.getServerDate().toString();
        
        if (psBranchCd.equals("")) psBranchCd = poGRider.getBranchCode();
        
        poTrans = new XMGCOnPoints(poGRider,psBranchCd, false);
        
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
        
        txtField00.focusedProperty().addListener(txtField_Focus);
        txtField01.focusedProperty().addListener(txtField_Focus);
        txtField02.focusedProperty().addListener(txtField_Focus);
        txtField03.focusedProperty().addListener(txtField_Focus);
        txtField04.focusedProperty().addListener(txtField_Focus);
        txtField05.focusedProperty().addListener(txtField_Focus);
        txtField06.focusedProperty().addListener(txtField_Focus);
        txtField07.focusedProperty().addListener(txtField_Focus);
        txtField08.focusedProperty().addListener(txtField_Focus);
        txtField09.focusedProperty().addListener(txtField_Focus);
        txtField10.focusedProperty().addListener(txtField_Focus);
        
        txtField00.setOnKeyPressed(this::txtField_KeyPressed);
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
        txtField02.setOnKeyPressed(this::txtField_KeyPressed);
        txtField03.setOnKeyPressed(this::txtField_KeyPressed);
        txtField04.setOnKeyPressed(this::txtField_KeyPressed);
        txtField05.setOnKeyPressed(this::txtField_KeyPressed);
        txtField06.setOnKeyPressed(this::txtField_KeyPressed);
        txtField07.setOnKeyPressed(this::txtField_KeyPressed);
        txtField08.setOnKeyPressed(this::txtField_KeyPressed);
        txtField09.setOnKeyPressed(this::txtField_KeyPressed);
        txtField10.setOnKeyPressed(this::txtField_KeyPressed);
        txtField06.setLeft(new ImageView(search));
        
        txtSeeks00.focusedProperty().addListener(txtSeeks_Focus);
        txtSeeks01.focusedProperty().addListener(txtSeeks_Focus);
       
        txtSeeks00.setOnKeyPressed(this::txtSeeks_Keypressed);
        txtSeeks01.setOnKeyPressed(this::txtSeeks_Keypressed);
        
        txtSeeks00.setOnKeyPressed(this::txtSeeks_Keypressed);
        txtSeeks01.setOnKeyPressed(this::txtSeeks_Keypressed);
        
        poTrans.ClearGCardProperty();
        cmbCardClass.setItems(sDeviceType);
        
        clearFields();
        loadGui();
        
        cmbCardClass.getSelectionModel().select(0);
        txtSeeks00.setDisable(true);
        txtSeeks01.setDisable(true);

        pbLoaded = true;
    } 
    
    public void setMainController(GCardAppFxController mainController) {
    this.mainController = mainController ;
    }
    
    public void setBranchCd(String fsBranchCd) {
        this.psBranchCd = fsBranchCd;
    }

    public void loadGui(){
        mainController.cmdButton00.setText("HOME");
        mainController.cmdButton01.setText("CONNECT");
        mainController.cmdButton02.setText("SEARCH");
        mainController.cmdButton03.setText("SAVE");
        mainController.cmdButton04.setText("HISTORY");
        mainController.cmdButton05.setText("");
        mainController.cmdButton06.setText("");
        mainController.cmdButton07.setText("");
        mainController.cmdButton08.setText("");
        mainController.cmdButton09.setText("");
        mainController.cmdButton10.setText("");
        mainController.cmdButton11.setText(CommonUtils.getConfiguration(poGRider, "BrCPNoUpdt").equals("1") ? "UPDT MOBILE" : "");

        mainController.tooltip00.setText("F1");
        mainController.tooltip01.setText("F2");
        mainController.tooltip02.setText("F3");
        mainController.tooltip03.setText("F4");
        mainController.tooltip04.setText("F5");
        mainController.tooltip05.setText("x");
        mainController.tooltip06.setText("x");
        mainController.tooltip07.setText("x");
        mainController.tooltip08.setText("x");
        mainController.tooltip09.setText("x");
        mainController.tooltip10.setText("x");
        mainController.tooltip11.setText("F12"); 

        mainController.cmdButton00.setDisable(false);
        mainController.cmdButton01.setDisable(false);
        mainController.cmdButton02.setDisable(false);
        mainController.cmdButton03.setDisable(false);
        mainController.cmdButton04.setDisable(false);
        mainController.cmdButton05.setDisable(true);
        mainController.cmdButton06.setDisable(true);
        mainController.cmdButton07.setDisable(true);
        mainController.cmdButton08.setDisable(true);
        mainController.cmdButton09.setDisable(true);
        mainController.cmdButton10.setDisable(true);
        mainController.cmdButton11.setDisable(true);

        mainController.setSiteMap(11);
    }
    
    private void txtSeeks_Keypressed(KeyEvent event){
        TextField txtSeeks = (TextField)event.getSource();        
        int lnIndex = Integer.parseInt(txtSeeks.getId().substring(8, 10));
        String lsValue = txtSeeks.getText();
        
        if (event.getCode() == ENTER || event.getCode() == KeyCode.F3) {
            switch (lnIndex){
               case 0:
                   if (lsValue.equals("")){
                       ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                       return;
                   }

                   if (poTrans.searchField("sCardNmbr", lsValue.replace("-", "")) ==true){                            
                        txtSeeks00.setText(System.getProperty("app.card.no"));
                        txtSeeks01.setText(System.getProperty("app.gcard.holder"));

                        cmbCardClass.getSelectionModel().select(Integer.parseInt(System.getProperty("app.device.type")));
                        txtSeeks00.setDisable(System.getProperty("app.device.type").equals("0"));
                        txtSeeks01.setDisable(System.getProperty("app.device.type").equals("0"));
                    }else{
                        ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                        return;
                    }
                  break;
               case 1:
                   if (lsValue.equals("")){
                       ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                       return;
                   }
                   if (poTrans.searchField("sClientNm", lsValue) == true){
                        txtSeeks00.setText(System.getProperty("app.card.no"));
                        txtSeeks01.setText(System.getProperty("app.gcard.holder"));

                        cmbCardClass.getSelectionModel().select(Integer.parseInt(System.getProperty("app.device.type")));
                        txtSeeks00.setDisable(System.getProperty("app.device.type").equals("0"));
                        txtSeeks01.setDisable(System.getProperty("app.device.type").equals("0"));
                    }else{
                        ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                        return;
                    }
                   break;
            }
        }        
        if (event.getCode() == DOWN || event.getCode() == ENTER){
             CommonUtils.SetNextFocus(txtSeeks);                  
        }
        if (event.getCode() == UP){
             CommonUtils.SetPreviousFocus(txtSeeks);                 
        }
    }
    
    public void loadRecord(){        
        txtSeeks00.setText(System.getProperty("app.card.no"));
        txtSeeks01.setText(System.getProperty("app.gcard.holder"));
        
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        XMClient loClient = (XMClient) poTrans.getGCard().getClient();
        XMGCard loGCard = (XMGCard) poTrans.getGCard();
        GCardDevice loDevice = (GCardDevice) poTrans.getDeviceInfo();        
        
        if ((Boolean)loDevice.getCardInfo("bIsOnline")){
            lblTranStatus.setText("ONLINE");
            lblTranStatus.setStyle("-fx-text-fill: green");
        }else{
            lblTranStatus.setText("OFFLINE");
            lblTranStatus.setStyle("-fx-text-fill: red");
        }
        
        txtField00.setText((String) poTrans.getMaster("sTransNox").toString().substring(1, 12).replaceFirst("(\\w{5})(\\w{6})", "$1-$2"));
        txtField01.setText((String) loDevice.getCardInfo("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
        txtField02.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
        txtField03.setText((String) loClient.getMaster("sAddressx") + ", " +loClient.getTown().getMaster("sTownName") + " " +loClient.getTown().getProvince().getMaster("sProvName") +" "+ loClient.getTown().getMaster("sZippCode"));
        txtField04.setText("N-O-N-E");
        txtField05.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
        txtField06.setText("");
        txtField07.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nTranAmtx").toString()), "###0.0"));
        txtField08.setText(CommonUtils.NumberFormat(Double.valueOf(loDevice.getCardInfo("nAvlPoint").toString()), "0.0"));
        txtField09.setText((String) poTrans.getMaster("sSourceNo"));
        txtField10.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nPointsxx").toString()), "0.0"));

        if (loDevice.getCardInfo("cCardType").toString().equals("0")){
            rbtn00.setSelected(true);
        }else{
            rbtn01.setSelected(true);
        }
        
        mainController.lblCardNmbr.setText(System.getProperty("app.card.no"));
        mainController.lblMobileNo.setText(System.getProperty("app.gcard.mobile"));

        if (null == System.getProperty("app.device.type")){
            mainController.lblDeviceType.setText("UNKNOWN");
        } else switch (System.getProperty("app.device.type")) {
            case GCDeviceType.NONE:
                mainController.lblDeviceType.setText("NON-CHIP CARD");
                break;
            case GCDeviceType.SMARTCARD:
                mainController.lblDeviceType.setText("SMART CARD");
                break;
            case GCDeviceType.QRCODE:
                mainController.lblDeviceType.setText("APP QR CODE");
                break;
            default:
                mainController.lblDeviceType.setText("UNKNOWN");
                break;
        }
        
        mainController.cmdButton11.setDisable(!CommonUtils.getConfiguration(poGRider, "BrCPNoUpdt").equals("1"));
        
        loGCard = null;
        loClient = null;
        loDevice =null;

        pnEditMode = poTrans.getEditMode();
    }

    private void cmdButton_Click(ActionEvent event) {
        String lsButton = ((Button)event.getSource()).getId().toLowerCase();
        switch (lsButton){
            case "cmdbutton00":
                mainController.dataPane.getChildren().clear();
                mainController.buttonClick();
                mainController.loadMainGui(); 
                mainController.mainKeyCode();
                break;
            case "cmdbutton01":
                if (poTrans.newTransaction()){
                    if (poTrans.connectCard() ==  true){
                        loadRecord();
                    }else{
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                    break;
                }
                break;
            case "cmdbutton02":
                switch (pnIndex){
                    case 6:
                        if (poTrans.searchField("sDescript", txtField06.getText()) == true){
                            loadParameter(6);
                        } else
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                }
                break;
            case "cmdbutton03":
                if (poTrans.getMaster("sSourceCd").equals("") || poTrans.getMaster("sSourceNo").equals("")){
                    ShowMessageFX.Warning(null, pxeModuleName, "Incomplete transaction information!");
                    return;
                }
                
                if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to save this transaction?")== true){
                    if (poTrans.saveUpdate() ==true){
                        if (chk00.isSelected() == true) poTrans.setHasBonus(true);
                        
                        //disconnect the card
                        poTrans.releaseCard();
                        
                        ShowMessageFX.Information(null, pxeModuleName, "Successfully Saved!");
                        clearFields();
                    }else{
                        ShowMessageFX.Warning(poTrans.getMessage(), pxeModuleName, "Unable to save transaction.");
                    }
                }
                break;
            case "cmdbutton04":
                mainController.loadModule(pxeOnlinePoints_Register);
                break;
            case "cmdbutton05":
            case "cmdbutton06":
            case "cmdbutton07":
            case "cmdbutton08":
            case "cmdbutton09":
            case "cmdbutton10":
                break;
            case "cmdbutton11":
                String lsClientID = System.getProperty("app.client.id");
                String lsMobileNo = System.getProperty("app.gcard.mobile");
                
                JSONObject loJSON = showFXDialog.updateMobileNo(poGRider, lsClientID, lsMobileNo);
                String lsResult = (String) loJSON.get("result");
                
                if (lsResult.equalsIgnoreCase("success")){
                    mainController.lblMobileNo.setText((String) loJSON.get("mobile"));
                    System.setProperty("app.gcard.mobile", (String) loJSON.get("mobile"));
                    
                    //mac 2020.08.22
                    //reload the information
                    if (poTrans.connectCard() ==  true) loadRecord();
                    
                    ShowMessageFX.Information((String) loJSON.get("message"), "Success", "Update Success");
                } else {                    
                    try {
                        loJSON = (JSONObject) new JSONParser().parse(loJSON.get("error").toString());
                        
                        ShowMessageFX.Warning((String) loJSON.get("message"), "Failed - " + (String) loJSON.get("code"), "Update Failed");
                    } catch (org.json.simple.parser.ParseException ex) {
                        Logger.getLogger(CardConnectController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
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
    
    public void loadParameter(int fnValue){
        switch (fnValue){
            case 0:
                poTrans.newTransaction();
                txtField00.setText((String) poTrans.getMaster("sTransNox").toString().substring(1, 12).replaceFirst("(\\w{5})(\\w{6})", "$1-$2"));
                break;
            case 6:
                txtField06.setText((String) poTrans.getSource().getMaster("sDescript"));
                break;
        }
        
    }
    
    public void initButton(int fnValue) {
       boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
       if (lbShow) mainController.setFocus(txtField06);
       
       txtField00.setMouseTransparent(lbShow);
       txtField01.setDisable(lbShow);
       txtField02.setDisable(lbShow);
       txtField03.setDisable(lbShow);
       txtField04.setDisable(lbShow);
       txtField05.setDisable(lbShow);
       txtField08.setDisable(lbShow);
       txtField10.setDisable(lbShow);
       
       rbtn00.setMouseTransparent(lbShow);
       rbtn01.setMouseTransparent(lbShow);
       
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    public void txtField_KeyPressed(KeyEvent event) {
        TextField txtField = (TextField)event.getSource();        
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (event.getCode() == ENTER || event.getCode() == KeyCode.F3) {
                switch (lnIndex){
                    case 6:
                        if (poTrans.searchField("sDescript", lsValue) == true){
                            loadParameter(6);
                        } else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                        }
                            
                        break;
                }
        }        
        if (event.getCode() == DOWN || event.getCode() == ENTER){
             CommonUtils.SetNextFocus(txtField);                  
        }
        if (event.getCode() == UP){
             CommonUtils.SetPreviousFocus(txtField);                 
        }
    }
    
    public void clearFields() {        
        mainController.lblCardNmbr.setText("N-O-N-E");
        mainController.lblDeviceType.setText("UNKNOWN");
        mainController.lblMobileNo.setText("N-O-N-E"); 
        
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        txtField00.setText("");
        txtField01.setText("");
        txtField02.setText("");
        txtField03.setText("");
        txtField04.setText("N-O-N-E");
        txtField05.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField06.setText("");
        txtField07.setText("0.0");
        txtField08.setText("0.0");
        txtField09.setText("");
        txtField10.setText("0.0");
        lblTranStatus.setText("UNKNOWN");
        lblTranStatus.setStyle("-fx-text-fill: black");
        chk00.setSelected(false);
        loadParameter(0);
        
        pnEditMode = poTrans.getEditMode();
        mainController.cmdButton11.setDisable(true);
        
        initButton(pnEditMode);
    }
    
    final ChangeListener<? super Boolean> txtField_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){ /*Lost Focus*/
            switch (lnIndex){    
               case 5:
                   if (CommonUtils.isDate(lsValue, pxeDateFormat)){
                        poTrans.setMaster("dTransact", CommonUtils.toDate(txtField.getText()));
                    } else{
                        ShowMessageFX.Warning("Invalid date entry.", pxeModuleName, "Date format must be yyyy-MM-dd (e.g. 1991-07-07)");
                        poTrans.setMaster("dTransact", CommonUtils.toDate(pxeCurrentDate));
                    }
                    /*get the value from the class*/
                    txtField.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
                    break;
               case 6:
                   if(!lsValue.equals(poTrans.getSource().getMaster("sDescript"))){
                     poTrans.setMaster("sSourceCd", "");
                     txtField.setText("");
                   }
                   
                   txtField10.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nPointsxx"), "0.0"));
                   break;
               case 7:
                    /*double nTranAmtx = 0.0;
                    try {
                        nTranAmtx = Double.parseDouble(lsValue);
                    } catch (Exception e) {
                        nTranAmtx = 0.0;
                        txtField.setText("0.0");
                    }
                    poTrans.setMaster("nTranAmtx",  nTranAmtx);
                    txtField.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nTranAmtx"), "0.0"));
                    txtField10.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nPointsxx"), "0.0"));
                    */
                    break;
               case 9:
                    poTrans.setMaster("sSourceNo",  lsValue);
                    if (poTrans.getMaster("nTranAmtx").equals(0.0) && !lsValue.equals("")){
                        ShowMessageFX.Warning(null, pxeModuleName, "Invalid Source No. detected!");
                        txtField07.setText("0.0");
                        txtField10.setText("0.0");
                        txtField09.setText("");
                        return;
                    }
                    txtField.setText((String) poTrans.getMaster("sSourceNo"));
                    txtField07.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nTranAmtx"), "0.0"));
                    txtField10.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nPointsxx"), "0.0"));
                    break;
               case 8:
               case 10:
                break;
                  
                default:
                   ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
                   return;
            }
           
        } else {
            switch (lnIndex){
                case 5: /*dTransact*/
                    try{
                        txtField.setText(CommonUtils.xsDateShort(lsValue));
                    }catch(ParseException e){
                        ShowMessageFX.Error(e.getMessage(), pxeModuleName, null);
                    }
                    txtField.selectAll();
                    break;
                default:
            }
            pnIndex = lnIndex;
            txtField.selectAll();
        }
    };    

    @FXML
    private void cmbCardClass_Change(ActionEvent event) {
        mainController.lblCardNmbr.setText("N-O-N-E");
        mainController.lblDeviceType.setText("UNKNOWN");
        mainController.lblMobileNo.setText("N-O-N-E");
        
        poTrans.ClearGCardProperty();
                
        txtSeeks00.setText("");
        txtSeeks01.setText("");
        
        clearFields();
        
        switch (cmbCardClass.getSelectionModel().getSelectedIndex()){
            case 0:                
                txtSeeks00.setDisable(true);
                txtSeeks01.setDisable(true);
                break;
            case 1:
            case 2:
                txtSeeks00.setDisable(false);
                txtSeeks01.setDisable(false);
                txtSeeks00.requestFocus();
        }
        
        System.setProperty("app.device.type", String.valueOf(cmbCardClass.getSelectionModel().getSelectedIndex()));
    }
    
    final ChangeListener<? super Boolean> txtSeeks_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtSeeks = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtSeeks.getId().substring(8, 10));
        String lsValue = txtSeeks.getText();
        
        if (lsValue == null) return;
            
        if(!nv){ /*Lost Focus*/
            switch (lnIndex){
               case 0:
               case 1:
                break;
                  
                default:
                   ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtSeeks.getId() + " not registered.");
                   return;
            }
           
        } else {
        }
        pnIndex = lnIndex;
        txtSeeks.selectAll(); 
    };
}

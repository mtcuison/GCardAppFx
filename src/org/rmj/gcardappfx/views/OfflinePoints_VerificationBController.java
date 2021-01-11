package org.rmj.gcardappfx.views;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.F3;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.textfield.CustomTextField;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.agentfx.ui.showFXDialog;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.appdriver.constants.GCDeviceType;
import org.rmj.client.agent.XMClient;
import org.rmj.gcard.device.ui.GCardDevice;
import org.rmj.gcard.trans.agentFX.XMGCOffPoints;
import org.rmj.gcard.trans.agentFX.XMGCard;


public class OfflinePoints_VerificationBController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private GridPane NodePane01;
    @FXML
    private TextField txtField03;
    @FXML
    private TextField txtField04;
    @FXML
    private TextField txtField05;
    @FXML
    private TextField txtField06;
    @FXML
    private TextField txtField07;
    @FXML
    private TextField txtField08;
    @FXML
    private TextField txtField09;
    @FXML
    private TextField txtField10;
    @FXML
    private RadioButton rbtn00;
    @FXML
    private RadioButton rbtn01;
    @FXML
    private TextField txtField11;
    @FXML
    private TextField txtField12;
    @FXML
    private TextField txtField13;
    @FXML
    private ImageView imageview;
    @FXML
    private GridPane NodePane00;
    
    private GCardAppFxController mainController ;
    public static final Image search = new Image("org/rmj/gcardappfx/images/search.png");
    private final String pxeModuleName = "G-Card Offline Points Verification";;
    public final static String pxeOthers = "CardOthers.fxml";
    public final static String pxeCardApplicationB = "CardApplicationB.fxml";
    public final static String pxeOfflinePoints_EntryB = "OfflinePoints_EntryB.fxml";
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    private int pnIndex = -1;
    private int pnEditMode = -1;
    private boolean pbLoaded = false;
    private static GRider poGRider;
    private final String pxeDateFormat = "yyyy-MM-dd";
    private String psBranchCd = "";
    private XMGCOffPoints poTrans;
    private String pxeCurrentDate;
    @FXML
    private TextArea txtField14;
    @FXML
    private TextField txtField02;
    @FXML
    private CustomTextField txtField00;
    @FXML
    private CustomTextField txtField01;
    @FXML
    private Label lblServerStat;
    @FXML
    private Label lblTranStatus;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (poGRider == null){
            ShowMessageFX.Warning("GhostRider Application not set..",pxeModuleName, "Please Inform MIS/SEG" );
            System.exit(0);
        }
        
        pxeCurrentDate = poGRider.getServerDate().toString();
        if (psBranchCd.equals("")) psBranchCd = poGRider.getBranchCode();
        
        poTrans = new XMGCOffPoints(poGRider, psBranchCd, false);
        
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
        
        txtField00.setOnKeyPressed(this::txtField_KeyPressed);
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
        txtField00.setLeft(new ImageView(search));
        txtField01.setLeft(new ImageView(search));
        
        clearFields();
        loadGui();
                
        pbLoaded = true;
    }
    
    public void setMainController(GCardAppFxController mainController) {
    this.mainController = mainController ;
    }
    
    public void initButton(int fnValue) {
       boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
       
       if (lbShow) mainController.setFocus(txtField00);
       
       txtField02.setMouseTransparent(lbShow);
       txtField03.setDisable(lbShow);
       txtField04.setDisable(lbShow);
       txtField05.setDisable(lbShow);
       txtField06.setDisable(lbShow);
       txtField07.setDisable(lbShow);
       txtField08.setDisable(lbShow);
       txtField09.setDisable(lbShow);
       txtField10.setDisable(lbShow);
       txtField11.setDisable(lbShow);
       txtField12.setDisable(lbShow);
       txtField13.setDisable(lbShow);
       txtField14.setDisable(lbShow);
       
       rbtn00.setMouseTransparent(lbShow);
       rbtn01.setMouseTransparent(lbShow);
    }
    
    public void setBranchCd(String fsBranchCd) {
        this.psBranchCd = fsBranchCd;
    }
    
    public void txtField_KeyPressed(KeyEvent event) {
        TextField txtField = (TextField)event.getSource();        
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (event.getCode() == ENTER || event.getCode() == KeyCode.F3) {
                switch (lnIndex){
                    case 0:
                        if (lsValue.equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            mainController.setFocus(txtField00);
                            return;
                        }
                        
                        if (poTrans.searchWithCondition("sClientNm", lsValue, "a.sTransNox LIKE " + SQLUtil.toSQL(psBranchCd + '%') + " AND a.cTranStat = '0'") ==true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField00);
                        }
                       break;
                    case 1:
                        if (lsValue.equals("")){
                            mainController.setFocus(txtField01);
                            return;
                        }
                        
                        if (poTrans.searchWithCondition("sSourceNo", lsValue, "a.sTransNox LIKE " + SQLUtil.toSQL(psBranchCd + '%') + " AND a.cTranStat = '0'") ==true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField01);
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
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        txtField00.setText("");
        txtField01.setText("");
        txtField02.setText("");
        txtField03.setText("");
        txtField04.setText("");
        txtField05.setText("");
        txtField06.setText("N-O-N-E");
        txtField07.setText("0.0");
        txtField08.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField09.setText("");
        txtField10.setText("0.0");
        txtField11.setText("0.0");
        txtField12.setText("");
        txtField13.setText("0.0");
        txtField14.setText("");
        
        lblTranStatus.setText("UNKNOWN");
        lblTranStatus.setStyle("-fx-text-fill: black");
        
        if (CommonUtils.isURLOnline(CommonUtils.getConfiguration(poGRider, "WebSvr"))){         
            lblTranStatus.setText("ONLINE");
            lblTranStatus.setStyle("-fx-text-fill: green");
        }else{
            lblTranStatus.setText("OFFLINE");
            lblTranStatus.setStyle("-fx-text-fill: red");
        }   
        
        loadNewTransaction(0);
        pnEditMode = poTrans.getEditMode();
        initButton(pnEditMode);
        
        mainController.cmdButton11.setDisable(true);
        
        setTransTat("-1");
    }
    
    private void loadNewTransaction(int fnValue){
        switch(fnValue){
            case 0:
                poTrans.newTransaction();
                txtField02.setText((String) poTrans.getMaster("sTransNox").toString().substring(1, 12).replaceFirst("(\\w{5})(\\w{6})", "$1-$2"));
                break;
        }
        
    }
    
    private void setTransTat(String fsValue){
        switch(fsValue){
            case "0":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/open.png")); break;
            case "1":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/closed.png")); break;
            case "2":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/posted.png")); break;
            case "3":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/cancelled.png")); break;
            case "4":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/void.png")); break;
            default:
                imageview.setImage(new Image("org/rmj/gcardappfx/images/unknown.png"));      
        }
    }
    
    public void loadTransaction(){
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        XMClient loClient = (XMClient) poTrans.getGCard().getClient();
        XMGCard loGCard = (XMGCard) poTrans.getGCard();
        
        txtField00.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
        txtField01.setText((String) poTrans.getMaster("sSourceNo"));
        txtField02.setText((String) poTrans.getMaster("sTransNox").toString().substring(1, 12).replaceFirst("(\\w{5})(\\w{6})", "$1-$2"));
        txtField03.setText((String) loGCard.getMaster("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
        txtField04.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
        txtField05.setText((String) loClient.getMaster("sAddressx") + ", " +loClient.getTown().getMaster("sTownName") + " " +loClient.getTown().getProvince().getMaster("sProvName") +" "+ loClient.getTown().getMaster("sZippCode"));
        txtField06.setText("N-O-N-E");
        txtField07.setText(CommonUtils.NumberFormat(Double.valueOf(loGCard.getMaster("nTotPoint").toString()), "0.0"));
        txtField08.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
        txtField09.setText((String) poTrans.getSource().getMaster("sDescript"));
        txtField10.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nTranAmtx").toString()), "###0.0"));
        txtField11.setText(CommonUtils.NumberFormat(Double.valueOf(loGCard.getMaster("nAvlPoint").toString()), "###0.0"));
        txtField12.setText((String) poTrans.getMaster("sSourceNo"));
        txtField13.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nPointsxx").toString()), "0.0"));
        txtField14.setText((String) poTrans.getMaster("sRemarksx"));
        
        if (loGCard.getMaster("cCardType").toString().equals("0")){
            rbtn00.setSelected(true);
        }else{
            rbtn01.setSelected(true);
        }
        
        setTransTat((String) poTrans.getMaster("cTranStat"));
        
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
        
        loClient = null;
        loGCard = null;
        pnEditMode = poTrans.getEditMode();
    }
    
    
    final ChangeListener<? super Boolean> txtField_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){ /*Lost Focus*/
            switch (lnIndex){
               case 0:
               case 1:
                break;
                  
                default:
                   ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
                   return;
            }
           
        } else {
        }
        pnIndex = lnIndex;
        txtField.selectAll(); 
    };
    
    private void loadGui(){
        mainController.cmdButton00.setText("HOME");
        mainController.cmdButton01.setText("SEARCH");
        mainController.cmdButton02.setText("VERIFY");
        mainController.cmdButton03.setText("VOID");
        mainController.cmdButton04.setText("CANCEL");
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
        
        mainController.setSiteMap(10);
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
                switch (pnIndex){
                    case 0:
                        if (txtField00.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            return;
                        }
                        
                        if (poTrans.searchWithCondition("sClientNm", txtField00.getText(), "a.sTransNox LIKE " + SQLUtil.toSQL(psBranchCd+ '%') + " AND a.cTranStat = '0'") ==true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField00);
                        }
                       break;
                    case 1:
                        if (txtField01.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            return;
                        }
                        
                        if (poTrans.searchWithCondition("sSourceNo", txtField01.getText(), "a.sTransNox LIKE " + SQLUtil.toSQL(psBranchCd+ '%') + " AND a.cTranStat = '0'") ==true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField01);
                        }
                       break;
                }
                break;
            case "cmdbutton02":
                if (poTrans.getMaster("sSourceNo").equals("")){
                    ShowMessageFX.Warning(null, pxeModuleName, "Please load Transaction first!");
                    return;
                }
                 
                if(ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to verify this transaction?")==true){
                    if(poTrans.closeTransaction((String) poTrans.getMaster("sTransNox"))== true){
                        ShowMessageFX.Information(null, pxeModuleName, "Successfully Verified!");
                        clearFields();
                    }else {
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                }
                break;
            case "cmdbutton03":
                if (poTrans.getMaster("sSourceNo").equals("")){
                    ShowMessageFX.Warning(null, pxeModuleName, "Please load Transaction first!");
                    return;
                }
                 
                if(ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to void this transaction?")==true){
                    if(poTrans.voidTransaction((String) poTrans.getMaster("sTransNox"))== true){
                        ShowMessageFX.Information(null, pxeModuleName, "Successfully Voided!");
                        clearFields();
                    }else {
                         ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                }
                break;
            case "cmdbutton04":
                if (poTrans.getMaster("sSourceNo").equals("")){
                    ShowMessageFX.Warning(null, pxeModuleName, "Please load Transaction first!");
                    return;
                }
                
                if(ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to cancel this transaction?")==true){
                    if(poTrans.cancelTransaction((String) poTrans.getMaster("sTransNox"))== true){
                        ShowMessageFX.Information(null, pxeModuleName, "Successfully Cancelled!");
                        clearFields();
                     }else {
                         ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                     }
                }
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
                return;
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
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}    
}
